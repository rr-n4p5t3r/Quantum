/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rr.quantum;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.*;

/**
 * Clase para configurar un servidor SSL.
 *
 * Esta clase proporciona métodos para crear un servidor SSL que escuche conexiones
 * en el puerto especificado. Permite la configuración de certificados SSL para
 * establecer conexiones seguras.
 *
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class SSLServerConfig {
    /**
    * Ruta del archivo de almacén de claves utilizado para configurar el servidor SSL.
    * Este archivo contiene los certificados necesarios para establecer conexiones seguras.
    */
    private static final String KEYSTORE_PATH = "ruta/al/almacen-de-claves.jks";

    /**
     * Contraseña para acceder al archivo de almacén de claves.
     * Se utiliza para desbloquear el almacén de claves y acceder a los certificados necesarios.
     */
    private static final String KEYSTORE_PASSWORD = "contraseña-del-almacen-de-claves";

    /**
     * Crea un servidor SSL en el puerto especificado.
     *
     * @param port El número de puerto en el que se va a escuchar.
     * @param useSSL Indica si se debe usar SSL para el servidor.
     * @return Un objeto SSLServerSocket configurado.
     * @throws IOException Si ocurre un error durante la configuración del servidor SSL.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static ServerSocket createSSLServerSocket(int port, boolean useSSL) throws IOException {
        if (useSSL) {
            // Código para SSL
            try {
                // Crear y configurar el contexto SSL
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, null, null);

                // Crear el servidor SSL
                SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
                return sslServerSocketFactory.createServerSocket(port);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                throw new IOException("Error creating SSL server socket", e);
            }
        } else {
            // Crear el servidor sin SSL
            return new ServerSocket(port);
        }
    }
}
