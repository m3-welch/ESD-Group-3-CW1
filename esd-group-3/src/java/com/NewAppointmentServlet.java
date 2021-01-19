/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Employee;
import models.Operation;
import models.Referrals;

/**
 *
 * @author Harrison B
 */
public class NewAppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession loginSession = request.getSession();
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);

        //decare vars
        String client_userid = request.getParameter("clientid");
        String employee_userid = request.getParameter("doctor-nurse");
        String type = request.getParameter("type");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime starttime = LocalTime.parse(request.getParameter("starttime"));
        LocalTime endtime = LocalTime.parse(request.getParameter("endtime"));
        String reason = request.getParameter("reason");

        //create appointment
        Operation operation = new Operation();
        
        DBConnection dbcon;
        
        Boolean isSurgery = null;
        
        if ("surgery".equals(type)) {
            isSurgery = true;
        } else {
            isSurgery = false;
        }
        
        Employee employee = new Employee();
        
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            operation.create(dbcon, Integer.parseInt(employee_userid), Integer.parseInt(client_userid), date, starttime, endtime, (float)0.00, false, isSurgery, reason);
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (operation.getIsSurgery() == isSurgery && operation.getDate() == date) {
            request.setAttribute("message", "Appointment successfully booked");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - Appointment failed to book. Please try again.");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Adds operation to db through calls to class in models package";
    }

}
