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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Events;
import models.Operation;

/**
 *
 * @author Harrison B
 */
public class ViewEmployeeSchedule extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {

        HttpSession loginSession = request.getSession();
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");

        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);

        //decare vars
        String filter = request.getParameter("filter");

        if ("doctor".equals(filter)) {
            request.setAttribute("checkeddoctor", "checked='true'");
        } else if ("nurse".equals(filter)) {
            request.setAttribute("checkednurse", "checked='true'");
        } else if ("all".equals(filter)) {
            request.setAttribute("checkedcombined", "checked='true'");
        }

        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Events events = new Events();
            events.getFilteredOperationsFromDB(dbcon, filter);
            events.orderEvents();
            LocalDate startDate = LocalDate.parse(request.getParameter("start"));
            LocalDate endDate = LocalDate.parse(request.getParameter("end"));

            Operation[] ops = events.getEventsBetweenDates(startDate, endDate);
            String outputList = "<table>";
            for (int i = 0; i < ops.length; i++) {
                outputList += "<tr><td>" + ops[i].getDate() + "</td><td>" +
                        ops[i].getStartTime() + "</td><td>" +
                        ops[i].getEndTime() + "</td><td>" + 
                        capitalizeWord(ops[i].getRoleFromId(dbcon)) + " " + 
                        ops[i].getEmpLastNameFromId(dbcon) + "</td><td>" + 
                        ops[i].getClientId() + "</td></tr>";
            }
            outputList += "</table>";
            
            request.setAttribute("message", "Data Loaded Successfully");
            request.setAttribute("appointmentlist", outputList);
            request.getRequestDispatcher("pages/NewAppointment.jsp").forward(request,response);

        } catch (SQLException ex) {
            request.setAttribute("message", "Error! - Appointments failed load. Please try again.");
            request.getRequestDispatcher("pages/NewAppointment.jsp").forward(request,response);
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
}
