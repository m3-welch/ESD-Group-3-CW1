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

/**
 *
 * @author Austi
 */
public class InvoiceViewerServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");   
          
        request.getRequestDispatcher("admin.jsp").include(request, response);  
          
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation counter = null;
            int noOfRows = counter.countAllOperations(dbcon);
            Operation[] operationsArray = new Operation[noOfRows];
            
            for (int i = 0; i <= noOfRows; i++) {
                operationsArray[i].retrieveByOperationId(dbcon, i);
            }
            
            request.setAttribute("data", "operationsArray"); // Will be available as ${data}
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