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
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Austin
 */
public class ClientInvoiceViewerServlet extends HttpServlet {  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");   
          
        request.getRequestDispatcher("dashboards/client_home.jsp").include(request, response);  
        
        HttpSession loginSession = request.getSession(false);
        int role = (Integer) loginSession.getAttribute("role");
        int userid = (Integer) loginSession.getAttribute("userID");

        String unpaid_str = request.getParameter("unpaid");
        boolean unpaid = false;
        if (unpaid_str == null) {}
        else if (unpaid_str.equals("on")){
            unpaid = true;
        }

        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation operationsCaller = new Operation();
            ArrayList<Operation> operationsArray = new ArrayList<Operation>();

            operationsArray = operationsCaller.retrieveAllClientOperations(dbcon, userid, unpaid);

            request.setAttribute("message", "Data Loaded Successfully");
            request.setAttribute("data", operationsArray); // Will be available as ${data}
            request.getRequestDispatcher("dashboards/client_home.jsp").forward(request,response);
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("dashboards/client_home.jsp").forward(request,response);
            response.sendRedirect("dashboards/client_home.jsp");
        }
            
    }
}
