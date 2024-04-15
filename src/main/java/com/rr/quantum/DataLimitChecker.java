/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rr.quantum;

import java.io.*;
import java.net.Socket;

/**
 * Clase que determina la cantidad de datos que se pueden leer o escribir en un archivo o socket a la vez.
 * 
 * Esta clase proporciona métodos para verificar y establecer límites en la cantidad de datos que pueden
 * ser leídos o escritos en un archivo o socket en una operación de lectura o escritura.
 * 
 * Los límites pueden ser útiles para evitar la sobrecarga del sistema o la saturación de la memoria al
 * manipular grandes volúmenes de datos.
 * 
 * Se pueden establecer límites tanto para la lectura como para la escritura de datos, permitiendo un control
 * más preciso sobre la cantidad de datos que se manejan en una sola operación.
 * 
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class DataLimitChecker {

    /**
     * Constructor por defecto de la clase SoporteArchivos.
     * 
     * Este constructor no realiza ninguna operación específica. Se utiliza para crear una
     * instancia de la clase DataLimitChecker.
     */
    public DataLimitChecker() {
        // No es necesario realizar ninguna operación aquí
    }
    
    /**
     * Determina la cantidad máxima de datos que se pueden leer desde un InputStream.
     *
     * @param inputStream El InputStream del que se leerán los datos.
     * @return La cantidad máxima de datos que se pueden leer, en bytes.
     * @throws IOException Si ocurre un error al leer los datos del InputStream.
     */
    public static int getMaxReadLimit(InputStream inputStream) throws IOException {
        // Tamaño del búfer de lectura
        int bufferSize = 8192; // Puedes ajustar este valor según tus necesidades

        // Leer datos del InputStream para determinar la cantidad máxima de datos disponibles
        byte[] buffer = new byte[bufferSize];
        int totalBytesRead = 0;
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            totalBytesRead += bytesRead;
        }

        return totalBytesRead;
    }

    /**
     * Determina la cantidad máxima de datos que se pueden escribir en un OutputStream.
     *
     * @param outputStream El OutputStream en el que se escribirán los datos.
     * @return La cantidad máxima de datos que se pueden escribir, en bytes.
     * @throws IOException Si ocurre un error al escribir los datos en el OutputStream.
     */
    public static int getMaxWriteLimit(OutputStream outputStream) throws IOException {
        // Tamaño del búfer de escritura
        int bufferSize = 8192; // Puedes ajustar este valor según tus necesidades

        // Escribir datos en el OutputStream para determinar la cantidad máxima de datos que se pueden escribir
        byte[] buffer = new byte[bufferSize];
        int totalBytesWritten = 0;
        while (totalBytesWritten < bufferSize) {
            outputStream.write(buffer);
            totalBytesWritten += bufferSize;
        }

        return totalBytesWritten;
    }

    /**
     * Determina la cantidad máxima de datos que se pueden leer desde un Socket.
     *
     * @param socket El Socket del que se leerán los datos.
     * @return La cantidad máxima de datos que se pueden leer, en bytes.
     * @throws IOException Si ocurre un error al leer los datos del Socket.
     */
    public static int getMaxReadLimit(Socket socket) throws IOException {
        return getMaxReadLimit(socket.getInputStream());
    }

    /**
     * Determina la cantidad máxima de datos que se pueden escribir en un Socket.
     *
     * @param socket El Socket en el que se escribirán los datos.
     * @return La cantidad máxima de datos que se pueden escribir, en bytes.
     * @throws IOException Si ocurre un error al escribir los datos en el Socket.
     */
    public static int getMaxWriteLimit(Socket socket) throws IOException {
        return getMaxWriteLimit(socket.getOutputStream());
    }
    
    /**
     * Verifica si el tamaño de lectura está dentro del límite especificado.
     *
     * @param readLimit  El límite de tamaño de lectura permitido.
     * @param fileSize   El tamaño del archivo a verificar.
     * @return true si el tamaño del archivo está dentro del límite de lectura, false de lo contrario.
     */
    public static boolean checkReadLimit(long readLimit, long fileSize) {
        return fileSize <= readLimit;
    }
}
