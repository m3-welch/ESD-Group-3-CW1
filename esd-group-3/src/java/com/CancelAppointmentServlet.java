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
        
        System.out.println("HIT CANCEL APPOINTMENT SERVELT !!");
        
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        int userid = (Integer)loginSession.getAttribute("userID");

        System.out.println("USER ID !! " + userid);
        
        int appointmentid = Integer.parseInt(request.getParameter("appointment"));
        
        System.out.println("APPOINTMENT ID !! " + appointmentid);

               
        DBConnection dbcon = null;
        
        int clientid = 0;
        
        Events event = new Events();

        try {
            dbcon = new DBConnection("smartcaretest", "", "");
//            System.out.println("IN TRY BEFORE CANCEL BOOKING");
//            event.cancelBooking(dbcon, clientid, appointmentid);
//            System.out.println("IN TRY AFTER CANCEL BOOKING");\        int 

            clientid = new Client().retrieveClientByUserId(dbcon, userid).getClientId();


        } catch (SQLException ex) {
            Logger.getLogger(CancelAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        int clientid = new Client().retrieveClientByUserId(dbcon, userid).getClientId();
        System.out.println("CLIENT ID !! " + clientid);

//        Events event = new Events();
        
        
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            System.out.println("IN TRY BEFORE CANCEL BOOKING");
            event.cancelBooking(dbcon, clientid, appointmentid);
            System.out.println("IN TRY AFTER CANCEL BOOKING");

        } catch (SQLException ex) {
            Logger.getLogger(CancelAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // get the user id
        // cancel appointment for that user id
        // given the appointment to cancel
        
        
        // add similar to events
//        public void dropUser(DBConnection dbcon, String username) {
//        String query = "";
//           
//        // Get the user's id depending on the username
//        query = "SELECT id FROM Users WHERE username = '" + username + "'";
//        int userid = 0;
//        try (Statement stmt = dbcon.conn.createStatement()) {
//            ResultSet resultSet = stmt.executeQuery(query);
//            while (resultSet.next()) {
//                userid = resultSet.getInt("id");
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//
//        // Delete the user out of the employees or clients table
//        query = "DELETE FROM Clients WHERE userid=" + userid;
//        try (Statement stmt = dbcon.conn.createStatement()) {
//            int resultSet = stmt.executeUpdate(query);
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//        
        
        
//        DBConnection dbcon;
//        try {
//            dbcon = new DBConnection("smartcaretest", "", "");
//            Events events = new Events();
//            events.getOperationsFromDB(dbcon, userid);
//            events.orderEvents();
//            
//            LocalDate startDate = LocalDate.parse(request.getParameter("start"));
//            LocalDate endDate = LocalDate.parse(request.getParameter("end"));
//            
//            Operation[] ops = events.getEventsBetweenDates(startDate, endDate);
//            
//            String outputList = "<table>";
//            for (int i = 0; i < ops.length; i++) {
//                outputList += "<tr><td>" + ops[i].getOperationId() + "</td><td>" 
//                        + ops[i].getDate() + "</td><td>" +  
//                        ops[i].getClientFullNameFromId(dbcon) + "</td><td>" + 
//                        capitalizeWord(ops[i].getRoleFromId(dbcon)) + " " + 
//                        ops[i].getEmpLastNameFromId(dbcon) + "</td><td>" + 
//                        ops[i].getStartTime() + "</td><td>" + 
//                        ops[i].getEndTime() + "</td></tr>";
//            }
//            outputList += "</table>";
//            
//            request.setAttribute("message", "Data Loaded Successfully");       
//            request.setAttribute("eventList", outputList);
//            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
//            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
//            
//            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
//            
//        } catch (SQLException ex) {
//            // send error
//            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
//            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
//            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
//        }
        
    }
    


}
