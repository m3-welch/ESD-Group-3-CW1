/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import models.Events;

/**
 *
 * @author conranpearce
 */
@WebServlet(name = "CancelAppointmentServlet", urlPatterns = {"/CancelAppointmentServlet"})
public class CancelAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        
        // get user id from global
        
        HttpSession loginSession = request.getSession();
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);

        // Get Employee id from employees table using user id
        int user_id = Integer.parseInt((loginSession.getAttribute("userID").toString()));
        DBConnection dbcon = null;        
        
        System.out.println("USER_ID !!! " + user_id);
        
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(CancelAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        Events event = new Events();
        //decare vars
        event.getOperationsFromDB(dbcon,user_id);

        String currentdate = (LocalDate.now()).toString();
        
        ArrayList<String> operationsList = new ArrayList<>();
        
        System.out.println("ARRAY!!! "+ event.getEventsBetweenDates(LocalDate.now(), LocalDate.now().plusYears(1))); 
        System.out.println("ARRAY!!! TO STRING "+ Arrays.toString(event.getEventsBetweenDates(LocalDate.now(), LocalDate.now().plusYears(1)))); 
        
        String test = "YES";
        
        if (test == "YES") {
//        if (client.getUsername().equals(username) && client.getIsNHS().equals(type)) {
            request.setAttribute("message", "Appointment successfully cancelled!");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - Appointment was not cancelled");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }
}
