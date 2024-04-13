/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.rr.quantum;

import java.io.*;
import java.net.*;
import java.util.Arrays;

/**
 * Clase principal que inicia un servidor web y uno SSL.
 * 
 * Esta clase inicia dos servidores: uno en el puerto 1980 sin SSL y otro en el puerto 2016 con SSL.
 * El servidor sin SSL se utiliza para manejar solicitudes HTTP normales, mientras que el servidor con SSL
 * se utiliza para manejar solicitudes HTTPS seguras.
 * 
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class Quantum {
    /**
    * Puerto para el servidor sin SSL.
    * 
    * Este atributo define el puerto en el que se iniciará el servidor sin SSL para manejar solicitudes HTTP normales.
    * Por defecto, se establece en 1980.
    */
    private static final int PORT = 1980;

   /**
    * Puerto para el servidor con SSL.
    * 
    * Este atributo define el puerto en el que se iniciará el servidor con SSL para manejar solicitudes HTTPS seguras.
    * Por defecto, se establece en 2016.
    */
    private static final int SSLPORT = 2016;

    /**
     * Método principal para iniciar los servidores web.
     * 
     * @param args Los argumentos de la línea de comandos (no se utilizan).
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        // Verificar si el puerto está disponible
        if (PortChecker.isPortAvailable(PORT)) {
            LogQuantum.logAccess("El puerto " + PORT + " esta disponible y no esta bloqueado.");
            // Intentar iniciar el servidor web sin SSL en el puerto 1980
            try {
                ServerSocket serverSocketWithoutSSL = SSLServerConfig.createSSLServerSocket(PORT, false);
                LogQuantum.logAccess("Servidor iniciado en el puerto " + PORT + " sin SSL");

                while (true) {
                    Socket clientSocket = serverSocketWithoutSSL.accept();
                    handleRequest(clientSocket);
                }
            } catch (IOException e) {
                LogQuantum.logError(e.getMessage());
            }
        } else {
            LogQuantum.logError("El puerto " + PORT + " está bloqueado o en uso por otro servicio.");
        }

        // Iniciar el servidor SSL en el puerto 2016
        try {
            ServerSocket serverSocketWithSSL = SSLServerConfig.createSSLServerSocket(SSLPORT, true);
            LogQuantum.logAccess("Servidor iniciado en el puerto " + SSLPORT + " con SSL");

            while (true) {
                Socket clientSocket = serverSocketWithSSL.accept();
                handleRequest(clientSocket);
            }
        } catch (IOException e) {
            LogQuantum.logError(e.getMessage());
        }
    }

    /**
     * Maneja una solicitud HTTP recibida desde un cliente.
     * 
     * @param clientSocket El socket del cliente que realiza la solicitud.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine != null) {
                String[] requestParts = requestLine.split("\\s+");
                String method = requestParts[0];
                String uri = requestParts[1];
                String httpVersion = requestParts[2];

                LogQuantum.logAccess(method + " " + uri + " " + httpVersion);

                if ("GET".equals(method)) {
                    SoporteArchivos.serveFile(out, uri);
                    SoporteArchivos.servePhpFile(out, uri);
                } else {
                    sendError(out, 501, "Not Implemented");
                }
            }
        } catch (IOException e) {
            LogQuantum.logError(e.getMessage());
        }
    }

    /**
     * Envía un error HTTP al cliente.
     * 
     * @param out El flujo de salida para enviar la respuesta HTTP.
     * @param statusCode El código de estado HTTP del error.
     * @param statusMessage El mensaje asociado con el código de estado HTTP.
     * @throws IOException Si ocurre un error de entrada/salida al escribir los datos.
     */
    static void sendError(OutputStream out, int statusCode, String statusMessage) throws IOException {
        String response = "HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n\r\n";
        out.write(response.getBytes());
        LogQuantum.logError(Arrays.toString(response.getBytes()));
    }
}
