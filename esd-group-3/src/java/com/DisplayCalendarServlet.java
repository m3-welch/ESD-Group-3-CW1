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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import models.Operation;
import models.User;

/**
 *
 * @author Harrison B
 */
public class DisplayCalendarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        int userid = (Integer)loginSession.getAttribute("userID");
        
        
        DBConnection dbcon;
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            User user = new User();
            user.retrieveByUserId(dbcon, userid);
            user.events.getOperationsFromDB(dbcon, user.getId());
            
            LocalDate startDate = LocalDate.parse(request.getParameter("start"));
            LocalDate endDate = startDate.plusWeeks(1);
            
            Operation[] ops = user.events.getEventsBetweenDates(startDate, endDate);
            
            request.setAttribute("message", "Data Loaded Successfully");
            request.setAttribute("data", ops);
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplayCalendarServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Fetches calendar info and displays it";
    }

}
