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
import models.Operation;
import dbcon.DBConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import models.Client;
import models.Employee;
import java.text.NumberFormat; 
import java.util.Locale;
/**
 *
 * @author Austin
 */
public class InvoiceViewerServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");
        
        // get session variables
        HttpSession loginSession = request.getSession(false);
        int userid = (Integer) loginSession.getAttribute("userID");
        int role = (Integer) loginSession.getAttribute("role");
        
        // dr/nurse - to ViewAppointments.jsp
        if (role == 1 || role == 2) {
            request.getRequestDispatcher("pages/ViewAppointments.jsp").include(request, response); 
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        }
        // client - to PayInvoices.jsp
        else if (role == 3) {
            request.getRequestDispatcher("pages/PayInvoices.jsp").include(request, response);
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        }
        // admin - to ViewTurnover.jsp
        else if (role == 4) {
            request.getRequestDispatcher("pages/ViewTurnover.jsp").include(request, response);  
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        }

        // get variables
        String filter_str = request.getParameter("filter");  
        int filter = 0;
        if (filter_str == null) {}
        else if (filter_str.equals("all")) {filter = 0;}
        else if (filter_str.equals("nhs")) {filter = 1;}
        else if (filter_str.equals("private")) {filter = 2;}
        
        String unpaid_str = request.getParameter("unpaid");
        boolean unpaid = false;
        if (unpaid_str == null) {}
        else if (unpaid_str.equals("on")){
            unpaid = true;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start_str = request.getParameter("start");
        String end_str = request.getParameter("end");
        LocalDate startDate = LocalDate.now().minusYears(1);
        LocalDate endDate = LocalDate.now().plusYears(1);
        if (start_str != null && end_str != null) {
            startDate = LocalDate.parse(start_str, formatter);
            endDate = LocalDate.parse(end_str, formatter);
        }
        
        // allows dr/nurse to view their appointments only
        boolean myAppt = false;
        if ((role == 1) || (role == 2)){
            if("on".equals(request.getParameter("myAppointments"))){
                myAppt = true;
            } 
        }
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            
            // get clientid if user is client
            int clientid = 0;
            int empid = 0;
            if (role == 3) {
                Client client = new Client();
                client.retrieveClientByUserId(dbcon, userid);
                clientid = client.getClientId();
            }
            else if (myAppt) {
                Employee emp = new Employee();
                emp.retrieveEmployeeByUserId(dbcon, userid);
                empid = emp.getEmployeeId();
            }
            
            // get operations that fit the variables
            Operation operationsCaller = new Operation();
            ArrayList<Operation> operationsArray = new ArrayList<Operation>();
            operationsArray = operationsCaller.retrieveAllOperationsWhere(dbcon, filter, unpaid, startDate, endDate, clientid, empid);
            
            // calculate/get other variables to display
            float turnover = 0;
            for(Operation i:operationsArray){
                turnover = turnover + i.getCharge();
            }
            
            // put data into table format
            NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.UK);
            boolean emptyTable = true;
            String outputList = "<table>";
            for(Operation i:operationsArray) {
                emptyTable = false;
                outputList += "<tr><td>" + i.getOperationId() + "</td><td>" 
                        + i.getDate() + "</td><td>" +  
                        i.getClientFullNameFromId(dbcon) + "</td><td>" + 
                        capitalizeWord(i.getRoleFromId(dbcon)) + " " + 
                        i.getEmpLastNameFromId(dbcon) + "</td><td>" + 
                        i.getStartTime() + "</td><td>" + 
                        i.getEndTime() + "</td><td>" +
                        i.getDescription() + "</td><td>" +
                        (i.getIsSurgery() ? "Surgery" : "Consultation") + "</td><td>" +
                        numFormat.format(i.getCharge()) + "</td><td>" + 
                        i.getIsPaid()  + "</td><td>" + 
                        i.getIsNhs() + "</td></tr>";
            }
            outputList += "</table>";
            if (emptyTable) {
                request.setAttribute("message", "Schedule Is Empty!");       
            } 
            else {
                request.setAttribute("message", "Data Loaded Successfully");
                request.setAttribute("tableData", outputList); // Will be available as ${tableData}
            }

            
            // send data to correct page
            // dr/nurse - to ViewAppointments.jsp
            if (role == 1 || role == 2) {
                request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
                response.sendRedirect("pages/ViewAppointments.jsp");
            }
            // client - to PayInvoices.jsp
            else if (role == 3) {
                request.getRequestDispatcher("pages/PayInvoices.jsp").forward(request,response);
                response.sendRedirect("pages/PayInvoices.jsp");
            }
            // admin - to ViewTurnover
            else if (role == 4) {
                request.setAttribute("turnover", turnover);
                request.getRequestDispatcher("pages/ViewTurnover.jsp").forward(request,response);
                response.sendRedirect("pages/ViewTurnover.jsp");
            }
        }
        catch(SQLException e){
            // send error
            if (role == 1 || role == 2) {
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
                response.sendRedirect("pages/ViewAppointments.jsp");
            }
            else if (role == 3) {
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                request.getRequestDispatcher("pages/PayInvoices.jsp").forward(request,response);
                response.sendRedirect("pages/PayInvoices.jsp");
            }
            else if (role == 4) {
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                request.getRequestDispatcher("pages/ViewTurnover.jsp").forward(request,response);
                response.sendRedirect("pages/ViewTurnover.jsp");
            }
            
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
