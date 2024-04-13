package com.rr.quantum;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Clase que implementa un servlet para manejar solicitudes GET y POST.
 * 
 * Este servlet proporciona métodos para manejar solicitudes GET y POST,
 * mostrando un mensaje simple en el cuerpo de la respuesta HTML.
 * 
 * @author Ricardo Rosero, n4p5t3r, rrosero2000@gmail.com - RR Soluciones IT SAS
 * @version 1.0
 */
public class CompatibilidadServlet extends HttpServlet {
    
    /**
     * Constructor por defecto.
     * 
     * Crea una nueva instancia de CompatibilidadServlet.
     */
    public CompatibilidadServlet() {
        // No es necesario realizar ninguna operación aquí
    }
    
    /**
     * Método para manejar las solicitudes GET.
     * 
     * Este método se invoca cuando se recibe una solicitud GET y es responsable de
     * configurar el tipo de contenido de la respuesta y escribir el contenido HTML
     * de la respuesta.
     * 
     * @param request Objeto HttpServletRequest que representa la solicitud HTTP.
     * @param response Objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida al escribir la respuesta.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configurar el tipo de contenido de la respuesta
        response.setContentType("text/html");
        
        // Obtener el flujo de salida para escribir la respuesta
        PrintWriter out = response.getWriter();
        
        // Escribir el contenido HTML de la respuesta
        out.println("<html>");
        out.println("<head><title>MiServlet</title></head>");
        out.println("<body>");
        out.println("<h1>Hola desde MiServlet - Método GET</h1>");
        out.println("</body></html>");
    }
    
    /**
     * Método para manejar las solicitudes POST.
     * 
     * Este método se invoca cuando se recibe una solicitud POST y es responsable de
     * configurar el tipo de contenido de la respuesta, leer los datos del formulario
     * y escribir el contenido HTML de la respuesta.
     * 
     * @param request Objeto HttpServletRequest que representa la solicitud HTTP.
     * @param response Objeto HttpServletResponse que representa la respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida al leer o escribir la respuesta.
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configurar el tipo de contenido de la respuesta
        response.setContentType("text/html");
        
        // Obtener el flujo de entrada para leer los datos del formulario
        BufferedReader reader = request.getReader();
        
        // Leer los datos del formulario
        String line;
        StringBuilder requestBody = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        
        // Escribir el contenido HTML de la respuesta
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>MiServlet</title></head>");
        out.println("<body>");
        out.println("<h1>Hola desde MiServlet - Método POST</h1>");
        out.println("<p>Datos del formulario:</p>");
        out.println("<p>" + requestBody.toString() + "</p>");
        out.println("</body></html>");
    }
}
