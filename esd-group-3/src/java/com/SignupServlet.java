/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Client;
import models.Price;

/**
 *
 * @author morgan
 */
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        request.getRequestDispatcher("signup.jsp").include(request, response);
        
        //decare vars
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        Client client = new Client();
        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            client.create(dbcon, username, password, firstname, lastname, email, address, "client", type);
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (client.getUsername().equals(username) && client.getType().equals(type)) {
            request.setAttribute("message", "Successful Signup - Login to continue");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("message", "Error - Failed Signup. Please try again.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            response.sendRedirect("signup.jsp");
        }
    }
}
