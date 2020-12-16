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
import java.util.Calendar;

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
        
        //turnover
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Operation operationsCaller = new Operation();
            ArrayList<Operation> operationsArray = new ArrayList<Operation>();
            
            // get today and clear time of day
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            
            String current_date = cal.toString();
            
            // get start of this week in milliseconds
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            
            String week_date = cal.toString();
            
            // start of the month
            cal.set(Calendar.DAY_OF_MONTH, 1);
            
            String month_date = cal.toString();
            
            operationsArray = operationsCaller.retrieveAllOperationsWhere(dbcon, true, false, current_date, current_date);
            
            float daily_t = 0;
            float daily_p = 0;
            float daily_n = 0;
            for(Operation i:operationsArray){
                daily_t = daily_t + i.getCharge();
                if (i.getIsNhs()) {
                    daily_n = daily_n + i.getCharge();
                }
                else {
                    daily_p = daily_p + i.getCharge();
                }
            }
            request.setAttribute("daily_t", daily_t);
            request.setAttribute("daily_p", daily_p);
            request.setAttribute("daily_n", daily_n);
            
            operationsArray = operationsCaller.retrieveAllOperationsWhere(dbcon, true, false, week_date, current_date);
            
            float weekly_t = 0;
            float weekly_p = 0;
            float weekly_n = 0;
            for(Operation i:operationsArray){
                weekly_t = weekly_t + i.getCharge();
                if (i.getIsNhs()) {
                    weekly_n = weekly_n + i.getCharge();
                }
                else {
                    weekly_p = weekly_p + i.getCharge();
                }
            }
            request.setAttribute("weekly_t", weekly_t);
            request.setAttribute("weekly_p", weekly_p);
            request.setAttribute("weekly_n", weekly_n);
            
            operationsArray = operationsCaller.retrieveAllOperationsWhere(dbcon, true, false, month_date, current_date);
            
            float monthly_t = 0;
            float monthly_p = 0;
            float monthly_n = 0;
            for(Operation i:operationsArray){
                monthly_t = monthly_t + i.getCharge();
                if (i.getIsNhs()) {
                    monthly_n = monthly_n + i.getCharge();
                }
                else {
                    monthly_p = monthly_p + i.getCharge();
                }
            }
            request.setAttribute("monthly_t", monthly_t);
            request.setAttribute("monthly_p", monthly_p);
            request.setAttribute("monthly_n", monthly_n);
            
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
