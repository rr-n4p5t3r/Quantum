package com.rr.quantum;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que proporciona funcionalidad para el registro de errores y accesos en archivos de log.
 * Los archivos de log se almacenan en ubicaciones predefinidas dependiendo del sistema operativo.
 * Se crean dos tipos de log: uno para errores y otro para accesos.
 * 
 * @author Ricardo Rosero
 * @version 1.0
 */
@SuppressWarnings("CallToPrintStackTrace")
public class LogQuantum {

    /**
    * Ruta base para los archivos de log en entornos Windows.
    * Se utiliza para almacenar archivos de log de errores y accesos en sistemas operativos Windows.
    */
    private static final String LOG_WINDOWS = "D:\\www\\log\\";

    /**
    * Ruta base para los archivos de log en entornos Linux.
    * Se utiliza para almacenar archivos de log de errores y accesos en sistemas operativos Linux.
    */
    private static final String LOG_LINUX = "/tmp/www/log/";

    /**
    * Logger para el registro de errores.
    * Se utiliza para registrar mensajes de error en un archivo de log específico.
    */
    private static final Logger ERROR = Logger.getLogger("error.log");

    /**
    * Logger para el registro de accesos.
    * Se utiliza para registrar mensajes de acceso en un archivo de log específico.
    */
    private static final Logger ACCESS = Logger.getLogger("access.log");

    /**
    * Configuración estática para inicializar los manejadores de log basados en el sistema operativo.
    * Se determina la ruta de los logs basada en el sistema operativo y se configuran los manejadores de log
    * para registrar mensajes de error y acceso en archivos de log específicos.
    */
    static {
        // Obtener el sistema operativo
        String osName = System.getProperty("os.name").toLowerCase();
        String logDir;

        // Determinar la ruta de los logs basado en el sistema operativo
        if (osName.contains("windows")) {
            logDir = LOG_WINDOWS;
        } else {
            logDir = LOG_LINUX;
        }

        try {
            // Crear el directorio si no existe
            File logDirectory = new File(logDir);
            if (!logDirectory.exists()) {
                logDirectory.mkdirs();
            }

            // Configuración del handler para el logger de errores
            Handler errorHandler = new FileHandler(logDir + "log_quantum_error.log", true); // El segundo argumento es 'true' para habilitar el anexo
            errorHandler.setLevel(Level.SEVERE);
            ERROR.addHandler(errorHandler);

            // Configuración del handler para el logger de accesos
            Handler accessHandler = new FileHandler(logDir + "log_quantum_acceso.log", true); // El segundo argumento es 'true' para habilitar el anexo
            accessHandler.setLevel(Level.INFO);
            ACCESS.addHandler(accessHandler);
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra un mensaje de error en el archivo de log de errores.
     * 
     * @param message El mensaje de error a registrar.
     */
    public static void logError(String message) {
        ERROR.severe(message);
    }

    /**
     * Registra un mensaje de acceso en el archivo de log de accesos.
     * 
     * @param message El mensaje de acceso a registrar.
     */
    public static void logAccess(String message) {
        ACCESS.info(message);
    }
}