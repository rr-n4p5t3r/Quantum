/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rr.quantum;

import java.io.*;
import java.nio.file.Files;

/**
 * Clase que proporciona métodos para servir archivos estáticos y ejecutar archivos PHP.
 * 
 * Esta clase permite la manipulación de archivos estáticos (como HTML, CSS, JS) y la ejecución
 * de archivos PHP para su procesamiento dinámico.
 * 
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class SoporteArchivos {
    /**
    * Ruta raíz del directorio web en sistemas Windows.
    * 
    * Este atributo define la ruta raíz del directorio web que contendrá los archivos HTML, PHP y otros recursos
    * servidos por el servidor en sistemas Windows. Se utiliza para construir las rutas de los archivos solicitados por los clientes.
    * La ruta en sistemas Windows es "D:\\www".
    */
    private static final String WEB_ROOT_WINDOWS = "D:\\www";
    /**
     * Ruta raíz del directorio web en sistemas Linux.
     * 
     * Este atributo define la ruta raíz del directorio web que contendrá los archivos HTML, PHP y otros recursos
     * servidos por el servidor en sistemas Linux. Se utiliza para construir las rutas de los archivos solicitados por los clientes.
     * La ruta en sistemas Linux es "/tmp/www".
     */
    private static final String WEB_ROOT_LINUX = "/tmp/www";

    /**
     * Ruta al ejecutable de Python en sistemas Windows.
     * 
     * Este atributo almacena la ruta al ejecutable de Python utilizado para ejecutar aplicaciones Flask
     * en sistemas Windows. La ruta típica es "C:\\Users\\n4p5t3r\\AppData\\Local\\Programs\\Python\\Python312\\python.exe".
     */
    private static final String PYTHON_WINDOWS = "C:\\Users\\n4p5t3r\\AppData\\Local\\Programs\\Python\\Python312\\python.exe";

    /**
     * Ruta al ejecutable de Python en sistemas Linux.
     * 
     * Este atributo almacena la ruta al ejecutable de Python utilizado para ejecutar aplicaciones Flask
     * en sistemas Linux. La ruta típica es "/usr/bin/python3".
     */
    private static final String PYTHON_LINUX = "/usr/bin/python3";

    /**
     * Ruta al ejecutable de PHP en sistemas Windows.
     * 
     * Este atributo almacena la ruta al ejecutable de PHP utilizado para ejecutar archivos PHP
     * en sistemas Windows. La ruta típica es "D:\\dev\\SDK\\php\\php.exe".
     */
    private static final String PHP_WINDOWS = "D:\\dev\\SDK\\php\\php.exe";

    /**
     * Ruta al ejecutable de PHP en sistemas Linux.
     * 
     * Este atributo almacena la ruta al ejecutable de PHP utilizado para ejecutar archivos PHP
     * en sistemas Linux. La ruta típica es "/usr/bin/php".
     */
    private static final String PHP_LINUX = "/usr/bin/php";

    /**
     * Sirve un archivo estático al cliente a través del flujo de salida especificado.
     * 
     * @param out El flujo de salida para escribir la respuesta HTTP.
     * @param uri La URI del archivo solicitado.
     * @throws IOException Si ocurre un error de entrada/salida al leer o escribir datos.
     */
    public static void serveFile(OutputStream out, String uri) throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String filePath;

        if (osName.contains("win")) {
            // Windows
            filePath = WEB_ROOT_WINDOWS + uri;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux/Unix
            filePath = WEB_ROOT_LINUX + uri;
        } else {
            // Otros sistemas operativos
            throw new UnsupportedOperationException("Sistema operativo no soportado");
        }
        
        System.out.println("Ruta del archivo: " + filePath); // Mensaje de depuración

        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            // Determinar el tipo MIME del archivo
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                // Si no se puede determinar el tipo MIME, establecerlo como "text/plain"
                contentType = "text/plain";
            }

            // Construir los encabezados HTTP
            String responseHeaders = """
                                     HTTP/1.1 200 OK\r
                                     Content-Type: """ + contentType + "\r\n\r\n";
            out.write(responseHeaders.getBytes());
        } else {
            Quantum.sendError(out, 404, "Not Found");
        }
    }

    /**
     * Ejecuta un archivo PHP y envía su salida al cliente a través del flujo de salida especificado.
     * 
     * @param out El flujo de salida para escribir la respuesta HTTP.
     * @param uri La URI del archivo PHP solicitado.
     * @throws IOException Si ocurre un error de entrada/salida al leer o escribir datos.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void servePhpFile(OutputStream out, String uri) throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String filePath;
        String phpExecutable;
        String command;

        if (osName.contains("win")) {
            // Windows
            filePath = WEB_ROOT_WINDOWS + uri;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux/Unix
            filePath = WEB_ROOT_LINUX + uri;
        } else {
            // Otros sistemas operativos
            throw new UnsupportedOperationException("Sistema operativo no soportado");
        }

        if (osName.contains("win")) {
            // Windows
            phpExecutable = PHP_WINDOWS;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux/Unix
            phpExecutable = PHP_LINUX; 
        } else {
            // Otros sistemas operativos
            throw new UnsupportedOperationException("Sistema operativo no soportado");
        }
        
         // Construir el comando para ejecutar el archivo PHP
        command = phpExecutable + " " + filePath;

        try {
            // Ejecutar el comando y capturar la salida
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Leer la salida generada por PHP y enviarla al cliente
            String line;
            while ((line = reader.readLine()) != null) {
                out.write(line.getBytes());
                out.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Ejecuta una aplicación Flask y envía su salida al cliente a través del flujo de salida especificado.
    * 
    * @param out El flujo de salida para escribir la respuesta HTTP.
    * @param uri La URI de la aplicación Flask solicitada.
    * @param flaskAppPath La ruta a la aplicación Flask.
    * @throws IOException Si ocurre un error de entrada/salida al leer o escribir datos.
    */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void serveFlaskApp(OutputStream out, String uri, String flaskAppPath) throws IOException {
       String osName = System.getProperty("os.name").toLowerCase();
        String pythonExecutable;
        String command;

        if (osName.contains("win")) {
            // Windows
            pythonExecutable = PYTHON_WINDOWS + uri;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            // Linux/Unix
            pythonExecutable = PYTHON_LINUX + uri;
        } else {
            // Otros sistemas operativos
            throw new UnsupportedOperationException("Sistema operativo no soportado");
        }
        
        // Construir el comando para ejecutar la aplicación Flask
        command = pythonExecutable + " " + flaskAppPath;

        try {
            // Ejecutar el comando y capturar la salida
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Leer la salida generada por la aplicación Flask y enviarla al cliente
            String line;
            while ((line = reader.readLine()) != null) {
                out.write(line.getBytes());
                out.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
