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
import javax.servlet.http.HttpSession;
import models.Operation;

/**
 *
 * @author Austi
 */
public class IssueInvoiceServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");   
        
        // change this jsp to wherever servlet is being called from 
        request.getRequestDispatcher("addInvoice.jsp").include(request, response);  
          
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation new_op = new Operation();
            HttpSession loginSession = request.getSession();
            String employee_name = (String) loginSession.getAttribute("name");
            
            //  dbcon, get from employee_name above, html select, select, select, select, work out charge, select slot, init paid as false?
            // new_op.create(dbcon, employee_userid, client_userid, date, starttime, endtime, 0, 0, false);
            
            
            // change jsp file and messages to where they should be sent
            request.setAttribute("data", "Operation Added"); // Will be available as ${data}
            request.getRequestDispatcher("addInvoice.jsp").forward(request,response);
            response.sendRedirect("addInvoice.jsp");
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("addInvoice.jsp").forward(request,response);
            response.sendRedirect("addInvoice.jsp");
        }    
            
    }  
  
}  
