/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Price;

/**
 *
 * @author Sam
 */
public class ChangePrices extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
            Price pricesCaller = new Price();
            ArrayList<Price> pricesArray = new ArrayList<Price>();
            
            boolean all = false;
            boolean is_nhs = false;
            
            if (filter.equals("all")) {all = true;}
            else if (filter.equals("nhs")) {is_nhs = true; }
            else if (filter.equals("private")) {is_nhs = false; }
            
            pricesArray = pricesCaller.retrievePriceTable(dbcon, all, is_nhs, start_date, end_date);
            
            float turnover = 0;
            for(Price i:pricesArray){
                turnover = turnover + i.getCharge();
            }
            
            request.setAttribute("data", pricesArray); // Will be available as ${data}
            request.setAttribute("turnover", turnover);
            request.getRequestDispatcher("admin.jsp").forward(request,response);
            // response.sendRedirect("admin.jsp");
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("admin.jsp").forward(request,response);
            response.sendRedirect("admin.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
