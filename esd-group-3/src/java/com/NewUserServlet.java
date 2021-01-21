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
public class NewUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        response.setContentType("text/html");
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        request.getRequestDispatcher("pages/NewUser.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
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
        LocalDate dob = LocalDate.parse(request.getParameter("dob"));
        
        GoogleMaps maps = new GoogleMaps();
        
        String formatted_address = maps.lookupAddress(address);
        
        if (formatted_address != null) {
            address = formatted_address;
        }   
        
        Client client = new Client();
        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            client.create(dbcon, username, password, firstname, lastname, email, address, "client", type, dob);
        } catch (SQLException ex) {
            Logger.getLogger(NewUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (client.getUsername().equals(username) && client.getFirstname().equals(firstname)) {
            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "New Patient successfully created!");
            request.getRequestDispatcher("pages/NewUser.jsp").forward(request,response);
        } else {
            request.setAttribute("messagecolour", "#FF3232");
            request.setAttribute("message", "Error! - New Patient not created");
            request.getRequestDispatcher("pages/NewUser.jsp").forward(request,response);
        }
    }
}
