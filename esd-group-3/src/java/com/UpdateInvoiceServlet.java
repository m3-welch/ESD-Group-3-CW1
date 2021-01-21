/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Operation;

/**
 *
 * @author Austi
 */
public class UpdateInvoiceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        
        response.setContentType("text/html");   
          
        request.getRequestDispatcher("pages/ViewAppointments.jsp").include(request, response);  
        
        int op_id = Integer.parseInt(request.getParameter("InvoiceID"));
        
        String start_str = request.getParameter("starttime"); 
        String end_str = request.getParameter("endtime");

        String description = request.getParameter("description");
        
        String paid_str;
        paid_str = request.getParameter("paid");
        boolean paid = false;
        if (paid_str == null) {}
        else if (paid_str.equals("on")){
            paid = true;
        }
        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation operation = new Operation();

            operation.retrieveByOperationId(dbcon, op_id);
            if (!start_str.equals("")) {
                operation.setStartTime(LocalTime.parse(start_str));
            }
            if (!end_str.equals("")) {
                operation.setEndTime(LocalTime.parse(end_str));
            }
            if (description != null) {
                operation.setDescription(description);
            }
            operation.setIsPaid(paid);
            operation.setCharge(operation.calculateOperationCost(dbcon, operation));
            
            operation.updateThisOperation(dbcon);

            request.setAttribute("message", "Update Successful");
            request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("pages/ViewAppointments.jsp").forward(request,response);
            response.sendRedirect("pages/ViewAppointments.jsp");
        }
    }
}
