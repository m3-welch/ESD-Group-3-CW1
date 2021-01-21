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
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import models.Events;
import models.Operation;
import models.User;

/**
 *
 * @author Harrison B
 */
public class DisplayEventsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        request.setAttribute("todaydate", LocalDate.now().toString());
        request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
        request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
        request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
       
        
        String myAppt = "off";
        System.out.println("role = " + loginSession.getAttribute("user_role"));
        
        if (loginSession.getAttribute("user_role").equals("doctor")||loginSession.getAttribute("user_role").equals("nurse")){
            request.setAttribute("ma", "<label for='myAppointments'>View my appointments only:</label><input type='checkbox' id='myAppointments' name='myAppointments' /><br>");
        }
        request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        request.setAttribute("todaydate", LocalDate.now().toString());
        request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
        request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
        request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        
       String myAppt = "off";
        
        int userid = (Integer)loginSession.getAttribute("userID");
        
        // Create a list of ids to loop through the schedule
        List<Integer> ids = new ArrayList<>();

        DBConnection dbcon;

        if (loginSession.getAttribute("user_role").equals("doctor")||loginSession.getAttribute("user_role").equals("nurse")){
            request.setAttribute("ma", "<label for='myAppointments'>View my appointments only:</label><input type='checkbox' id='myAppointments' name='myAppointments' /><br>");
            
            if("on".equals(request.getParameter("myAppointments"))){
                myAppt = "on";
            } else {
                myAppt = "off";
            }
            System.out.println("myAppt = " + myAppt);
        }
        // If the user role is an admin then we need to display all appointments in the database
        if (loginSession.getAttribute("user_role").equals("admin")||!myAppt.equals("on")) {
            User users = new User();
            try {
                dbcon = new DBConnection("smartcaretest", "", "");
                // For each id in the database add that to the user id list
                for (int usersid: users.getAllUserids(dbcon)) {
                    ids.add(usersid);
                } 
            } catch (SQLException ex) {
                Logger.getLogger(DisplayEventsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        // If the user is not an admin then only show the appointments relevant to them
        } else {
            ids.add(userid);
        }
        
        List<Integer> appointmentList = new ArrayList<>();
        
        String outputList = "<table>";
        
        boolean emptyTable = true;
        
        // For each id in the database then return the apppointment
        for (int idloop: ids) {
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
            try {
                dbcon = new DBConnection("smartcaretest", "", "");
                Events events = new Events();
                events.getOperationsFromDB(dbcon, idloop);
                events.orderEvents();

                LocalDate startDate = LocalDate.parse(request.getParameter("start"));
                LocalDate endDate = LocalDate.parse(request.getParameter("end"));

                Operation[] ops = events.getEventsBetweenDates(startDate, endDate);
                NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
                
                for (int i = 0; i < ops.length; i++) {
                    // Display if the appointment if it has not already been displayed
                    if (appointmentList.contains(ops[i].getOperationId()) == false) {                        
                        appointmentList.add(ops[i].getOperationId());
                        
                        emptyTable = false;
                        
                        outputList += "<tr><td>" + ops[i].getOperationId() + "</td><td>" 
                            + ops[i].getDate() + "</td><td>" +  
                            ops[i].getClientFullNameFromId(dbcon) + "</td><td>" + 
                            capitalizeWord(ops[i].getRoleFromId(dbcon)) + " " + 
                            ops[i].getEmpLastNameFromId(dbcon) + "</td><td>" + 
                            ops[i].getStartTime() + "</td><td>" + 
                            ops[i].getEndTime() + "</td><td>" +
                            ops[i].getDescription() + "</td><td>" +
                            (ops[i].getIsSurgery() ? "Surgery" : "Consultation") + "</td><td>" +
                            formatter.format(ops[i].getCharge()) + "</td></tr>";
                    }
                }
            } catch (SQLException ex) {
                // send error
                request.setAttribute("messagecolour", "#FF3232"); 
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
            }
        }
        
        outputList += "</table>";
        
        if (emptyTable) {
            request.setAttribute("messagecolour", "#FF3232"); 
            request.setAttribute("message", "Schedule Is Empty!");       
        } else {
            request.setAttribute("messagecolour", "#329232"); 
            request.setAttribute("message", "Schedule Successfully Loaded!");       
        }
        request.setAttribute("eventList", outputList);
        request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
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
