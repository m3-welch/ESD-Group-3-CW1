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
public class PayInvoiceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        
        response.setContentType("text/html");   
          
        request.getRequestDispatcher("pages/PayInvoices.jsp").include(request, response);  
        
        int op_id = Integer.parseInt(request.getParameter("Invoice ID"));
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation operationsCaller = new Operation();

            operationsCaller.payByOperationId(dbcon, op_id);

            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "Payment Successful");
            request.getRequestDispatcher("pages/PayInvoices.jsp").forward(request,response);
        }
        catch(SQLException e){
            // send error
            request.setAttribute("messagecolour", "#FF3232");
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("pages/PayInvoices.jsp").forward(request,response);
            response.sendRedirect("pages/PayInvoices.jsp");
        }
    }
}
