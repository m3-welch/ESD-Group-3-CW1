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
import javax.servlet.http.HttpSession;
import models.Bookings;

/**
 *
 * @author conran
 */
public class CancelAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        request.getRequestDispatcher("cancelAppointment.jsp").include(request, response);
        
        //decare vars
        int bookingid = Integer.parseInt(request.getParameter("bookingid"));
        
        // hide the booking id value in the values put into the drop down box
        // display time and date with doctor name for specific user 
        // copy morgans explanation
        
        Bookings booking = new Bookings();
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            booking.cancel(dbcon, bookingid);
        } catch (SQLException ex) {
            Logger.getLogger(NewUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpSession loginSession = request.getSession();

        // check to see if the booking is in the database with booking id, if it is not in it then successful
//        if (booking.)
        if (booking.getUsername().equals(username) && client.getType().equals(type)) {
            request.setAttribute("message", "Cancelled appointment successfully!");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - Appointment not cancelled");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }
}
