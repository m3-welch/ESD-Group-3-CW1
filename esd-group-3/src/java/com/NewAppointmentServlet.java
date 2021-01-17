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
        String clientid = request.getParameter("clientid");
        String employeeId = request.getParameter("doctor-nurse");
        String type = request.getParameter("type");
        String date = request.getParameter("date");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");

        //create appointment
        Operation operation = new Operation();
        
        DBConnection dbcon;
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            operation.create(dbcon, employeeId, clientid, date, starttime, endtime, (float)0.00, 0, false);
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (ref.getNameArr().length > count) {
            request.setAttribute("message", "New Referral added to client");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - Referral failed to add");
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
        return "Adds referral to db through calls to class in models package";
    }

}
