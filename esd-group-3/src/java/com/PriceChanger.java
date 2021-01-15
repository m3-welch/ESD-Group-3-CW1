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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Price;

/**
 *
 * @author Sam
 */
@WebServlet(name = "PriceChanger", urlPatterns = {"/PriceChanger"})
public class PriceChanger extends HttpServlet {

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
        String save = "";
        request.getRequestDispatcher("prices.jsp").include(request, response);  
           
        try{
            //check the editOrSave parameter is save
            save = request.getParameter("save");            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        try {
            request.setAttribute("readonly", "");
            request.setAttribute("editOrSave", "save");
            request.setAttribute("delete", "");
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            
            System.out.println(save);
            if ("save".equals(save)) {
                System.out.println("+++++++++++++++++++++++++++++ reeeepkkk");
                System.out.println(request.getParameter("apptType"));
                System.out.println(request.getParameter("empType"));
                System.out.println(request.getParameter("priceValue"));
                                          
                Price newPrice = new Price(request.getParameter("apptType"), request.getParameter("empType"), Float.parseFloat(request.getParameter("priceValue"))); //populate with table attributes
                //only id is consistent
                newPrice.setPrice(dbcon, request.getParameter("idValue"));
            }
            
            Price pricesCaller = new Price();
            ArrayList<Price> pricesArray = new ArrayList<Price>();

            pricesArray = pricesCaller.retrievePriceTable(dbcon);

            request.setAttribute("data", pricesArray); // Will be available as ${data}
            request.getRequestDispatcher("prices.jsp").forward(request,response);
            
            
            
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("prices.jsp").forward(request,response);
            response.sendRedirect("prices.jsp");
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
