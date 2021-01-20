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
import java.time.LocalDate;
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
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");
        
        request.getRequestDispatcher("newPatient.jsp").include(request, response);
        
        //decare vars
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        LocalDate dob = LocalDate.parse(request.getParameter("dob"));
        Client client = new Client();
        
        
        GoogleMaps maps = new GoogleMaps();
        
        String formatted_address = maps.lookupAddress(address);
        
        if (formatted_address != null) {
            address = formatted_address;
        }
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            client.create(dbcon, username, password, firstname, lastname, email, address, "client", type, dob);
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (client.getUsername().equals(username) && client.getIsNhs().equals(type)) {
            request.setAttribute("message", "Successful Signup - Login to continue");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("message", "Error - Failed Signup. Please try again.");
            request.getRequestDispatcher("newPatient.jsp").forward(request, response);
            response.sendRedirect("newPatient.jsp");
        }
    }
}