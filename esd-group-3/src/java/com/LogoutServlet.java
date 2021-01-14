/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Austin H
 */
public class LogoutServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                        throws ServletException, IOException {  
        response.setContentType("text/html");  
            
        request.getRequestDispatcher("home.jsp").include(request, response);  
        
        // invalidate httpSession
        HttpSession loginSession = request.getSession();
        loginSession.invalidate();
          
        request.setAttribute("message", "Successful Logout"); // Will be available as ${message}
        request.getRequestDispatcher("login.jsp").forward(request,response);
        response.sendRedirect("login.jsp");
    }  
}  
