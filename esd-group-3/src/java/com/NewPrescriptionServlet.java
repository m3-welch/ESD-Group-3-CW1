/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Prescriptions;

/**
 *
 * @author conranpearce
 */
public class NewPrescriptionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        request.getRequestDispatcher("newPrescription.jsp").include(request, response);
        
        //decare vars
        int clientid = Integer.parseInt(request.getParameter("clientid"));
//        int employeeid = 1;
        int employeeid = Integer.parseInt(request.getParameter("employeeid"));
        String drug_name = request.getParameter("drug_name");
        String dosage = request.getParameter("dosage");
        String is_repeat = "FALSE";
        LocalDate date_start = LocalDate.parse(request.getParameter("date_start"));
        LocalDate date_end = LocalDate.parse(request.getParameter("date_end"));
        
        try {
            if (request.getParameter("is_repeat").equals("on")) {
                is_repeat = "TRUE";
            }
        } catch(Exception e) {
            is_repeat = "FALSE";
        }
        
        Prescriptions prescription = new Prescriptions();
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            prescription.create(dbcon, clientid, employeeid, drug_name, dosage, Boolean.valueOf(is_repeat), date_start, date_end);
        } catch (SQLException ex) {
            Logger.getLogger(NewPrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpSession loginSession = request.getSession();
        
        System.out.println("prescription.getDrugName()(): " + prescription.getDrugName());
        System.out.println("prescription.getDrugName() to string(): " + Arrays.toString(prescription.getDrugName()));
        System.out.println("drug_name: " + drug_name);
        System.out.println("prescription.getDrugName().equals): " + Arrays.toString(prescription.getDrugName()).equals(drug_name));
        
        System.out.println("prescription.getDrugName().contains): " + Arrays.toString(prescription.getDrugName()).contains(drug_name));
        
        System.out.println("GET CLIENT: " + prescription.getClient());


        if ((prescription.getClient() == clientid)) {
//        if ((prescription.getClient() == clientid) && prescription.getDrugName().equals(drug_name)) {
//        if (prescription.getDrugName().equals(drug_name) && prescription.getDosage().equals(dosage)) {
            request.setAttribute("message", "New Prescription successfully created!");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - New Prescription not created");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }
}
