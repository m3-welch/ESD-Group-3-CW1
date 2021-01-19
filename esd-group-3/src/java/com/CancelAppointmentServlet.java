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
import dbcon.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import models.Client;
import models.Employee;
import models.Events;
import models.Operation;

/**
 *
 * @author conran
 */
public class CancelAppointmentServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        // declar vars
        int userid = (Integer)loginSession.getAttribute("userID");        
        int appointmentid = Integer.parseInt(request.getParameter("appointment"));
        
        int clientid = 0;
        DBConnection dbcon = null;
        Events event = new Events();
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            clientid = new Client().retrieveClientByUserId(dbcon, userid).getClientId();

        } catch (SQLException ex) {
            Logger.getLogger(CancelAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);   
        }
        
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            
            // If the appointment was successfully cancelled then 1 shall be returned
            if (event.cancelBooking(dbcon, clientid, appointmentid) == 1) {
                request.setAttribute("message", "Appointment successfully cancelled!");       
            // If the appointment could not be cancelled then 0 is returned
            } else {
                request.setAttribute("message", "Appointment was not able to be cancelled! Please input a valid appointment number.");       
            }
            
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));

        } catch (SQLException ex) {
            Logger.getLogger(CancelAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }        
    }
}
