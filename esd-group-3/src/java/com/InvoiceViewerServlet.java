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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Austin
 */
public class InvoiceViewerServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");   
          
        request.getRequestDispatcher("admin.jsp").include(request, response);  
        
        String filter = request.getParameter("filter");  
        String start_date = request.getParameter("start");
        String end_date = request.getParameter("end");
        
        if (filter == null) {
            filter = "all";
        }
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation operationsCaller = new Operation();
            int noOfRows = operationsCaller.countAllOperations(dbcon);
            ArrayList<Operation> operationsArray = new ArrayList<Operation>();
            
            boolean all = false;
            boolean is_nhs = false;
            
            if (filter.equals("all")) {all = true;}
            else if (filter.equals("nhs")) {is_nhs = true; }
            else if (filter.equals("private")) {is_nhs = false; }
            
            operationsArray = operationsCaller.retrieveAllOperationsWhere(dbcon, all, is_nhs, start_date, end_date);
            
            
            request.setAttribute("data", operationsArray); // Will be available as ${data}
            request.getRequestDispatcher("admin.jsp").forward(request,response);
            response.sendRedirect("admin.jsp");
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("admin.jsp").forward(request,response);
            response.sendRedirect("admin.jsp");
        }    
            
    }  
  
}  
