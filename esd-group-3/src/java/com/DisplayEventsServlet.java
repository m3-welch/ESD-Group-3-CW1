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
import models.Events;
import models.Operation;

/**
 *
 * @author Harrison B
 */
public class DisplayEventsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        
        request.setAttribute("todaydate", LocalDate.now().toString());
        request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
        request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
        request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        int userid = (Integer)loginSession.getAttribute("userID");
        
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        DBConnection dbcon;
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            Events events = new Events();
            events.getOperationsFromDB(dbcon, userid);
            events.orderEvents();
            
            LocalDate startDate = LocalDate.parse(request.getParameter("start"));
            LocalDate endDate = LocalDate.parse(request.getParameter("end"));
            
            Operation[] ops = events.getEventsBetweenDates(startDate, endDate);
            
            String outputList = "<table>";
            for (int i = 0; i < ops.length; i++) {
                outputList += "<tr><td>" + ops[i].getDate() + "</td><td>" + 
                        ops[i].getClientFullNameFromId(dbcon) + "</td><td>" + 
                        capitalizeWord(ops[i].getRoleFromId(dbcon)) + " " + 
                        ops[i].getEmpLastNameFromId(dbcon) + "</td><td>" + 
                        ops[i].getStartTime() + "</td><td>" + 
                        ops[i].getEndTime() + "</td><td>" +
                        ops[i].getDescription() + "</td></tr>";
            }
            outputList += "</table>";
                        
            request.setAttribute("message", "Data Loaded Successfully");       
            request.setAttribute("eventList", outputList);
            request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
            
        } catch (SQLException ex) {
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
        }
        
    }
    
    private static String capitalizeWord(String str){  
        String words[]=str.split("\\s");  
        String capitalizeWord="";  
        for(String w:words){  
            String first=w.substring(0,1);  
            String afterfirst=w.substring(1);  
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";  
        }  
        return capitalizeWord.trim();  
    }  

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Fetches calendar info";
    }

}
