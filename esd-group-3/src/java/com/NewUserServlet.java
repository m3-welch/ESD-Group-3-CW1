/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import api.GoogleMaps;
import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;

/**
 *
 * @author morgan
 */
public class NewUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/NewUser.jsp").include(request, response);
        
        //decare vars
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        
        GoogleMaps maps = new GoogleMaps();
        
        String formatted_address = maps.lookupAddress(address);
        
        if (formatted_address != null) {
            address = formatted_address;
        }   
        
        Client client = new Client();
        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            client.create(dbcon, username, password, firstname, lastname, email, address, "client", type);
        } catch (SQLException ex) {
            Logger.getLogger(NewUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpSession loginSession = request.getSession();

        if (client.getUsername().equals(username) && client.getFirstname().equals(firstname)) {
            request.setAttribute("message", "New Patient successfully created!");
            request.getRequestDispatcher("pages/NewUser.jsp").forward(request,response);
        } else {
            request.setAttribute("message", "Error! - New Patient not created");
            request.getRequestDispatcher("pages/NewUser.jsp").forward(request,response);
        }
    }
}
