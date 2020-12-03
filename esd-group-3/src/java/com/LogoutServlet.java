/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 *
 * @author Austin H
 */
public class LogoutServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                        throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
          
        request.getRequestDispatcher("home.jsp").include(request, response);  
          
        // overwrite current cookies, with expired cookies
        Cookie ck_username=new Cookie("username","");  
        ck_username.setMaxAge(0);  
        response.addCookie(ck_username);  
        Cookie ck_role=new Cookie("role","");  
        ck_role.setMaxAge(0);  
        response.addCookie(ck_role);  
          
        out.print("You have been logged out");  
        
        response.sendRedirect("login.jsp");
    }  
}  
