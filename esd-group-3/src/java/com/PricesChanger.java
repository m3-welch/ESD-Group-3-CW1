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
import models.Price;

/**
 *
 * @author Sam
 */
public class PricesChanger extends HttpServlet {

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
        String delete = "";
        String save = ""; 
        String add = "";
        
        request.getRequestDispatcher("prices.jsp").include(request, response);  
        try{
            //check the editOrSave parameter is save
            delete = request.getParameter("delete");            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        try{
            //check the editOrSave parameter is save
            save = request.getParameter("save");            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        try{
            //check the editOrSave parameter is save
            save = request.getParameter("add");            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        try {
            
            
            if (delete == "Delete") {
                Price deletePrice = new Price(request.getParameter("apptType"), request.getParameter("empType"), Float.parseFloat(request.getParameter("priceValue"))); //populate with table attributes
                deletePrice.removePrice();
            }
            else if (save == "Save") {
                Price savePrice = new Price(request.getParameter("apptType"), request.getParameter("empType"), Float.parseFloat(request.getParameter("priceValue"))); //populate with table attributes
                savePrice.update(Integer.parseInt(request.getParameter("idValue")));
            }
            else if (add == "Add") {
                Price addPrice = new Price(request.getParameter("apptType"), request.getParameter("empType"), Float.parseFloat(request.getParameter("priceValue"))); //populate with table attributes
                addPrice.addPrice();
            }
            
            
            
            Price pricesCaller = new Price();
            ArrayList<Price> pricesArray = new ArrayList<Price>();
            
            request.setAttribute("readonly", "readonly");
            request.setAttribute("editOrSave", "edit");
            request.setAttribute("delete", "<input type='submit' name='delete' value='Delete' class='button'>");
            
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            pricesArray = pricesCaller.retrievePriceTable();
                        
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
