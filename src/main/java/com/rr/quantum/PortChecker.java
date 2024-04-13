/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rr.quantum;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Clase para verificar la disponibilidad del puerto utilizado en el servidor.
 *
 * Esta clase proporciona un método estático para verificar si un puerto específico
 * está disponible y no está bloqueado por otro servicio en el sistema.
 *
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class PortChecker {
    /**
     * Constructor por defecto de la clase PortChecker.
     * 
     * Este constructor no realiza ninguna operación específica. Se utiliza para crear una
     * instancia de la clase PortChecker.
     */
    public PortChecker() {
        // No es necesario realizar ninguna operación aquí
    }
    
    /**
     * Verifica si el puerto especificado está disponible y no está bloqueado.
     *
     * @param port El número de puerto a verificar.
     * @return true si el puerto está disponible y no está bloqueado, false de lo contrario.
     */
    public static boolean isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Si la apertura del ServerSocket tiene éxito, el puerto está disponible y no está bloqueado
            return true;
        } catch (IOException e) {
            // Si la apertura del ServerSocket falla, el puerto está bloqueado o en uso
            return false;
        }
    }
}