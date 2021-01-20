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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Prescriptions;
import models.Employee;

/**
 *
 * @author conranpearce
 */
public class CreatePrescriptionServlet extends HttpServlet {
    
     // Remove brackets from array.toString() return, to be able to compare strings
    public String removeBrackets(String message) {
        message = message.replace("[","");
        message = message.replace("]","");

        return message;
    } 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        response.setContentType("text/html");
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        request.getRequestDispatcher("pages/CreatePrescriptions.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/CreatePrescriptions.jsp").include(request, response);

        // Get Employee id from employees table using user id
        int user_id = Integer.parseInt((loginSession.getAttribute("userID").toString()));
        DBConnection dbcon = null;
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Employee employee = new Employee();
        //decare vars
        int employee_id = employee.retrieveEmployeeIdByUserId(dbcon,user_id);        

        int clientid = Integer.parseInt(request.getParameter("clientid"));
        String drug_name = request.getParameter("drug_name");
        String dosage = request.getParameter("dosage");
        String is_repeat = "FALSE";
        LocalDate date_start = LocalDate.parse(request.getParameter("date_start"));
        LocalDate date_end = LocalDate.parse(request.getParameter("date_end"));

        // Set boolean for if the repeat value is on or off (checkbox)
        try {
            if (request.getParameter("is_repeat").equals("on")) {
                is_repeat = "TRUE";
            }
        } catch(Exception e) {
            is_repeat = "FALSE";
        }

        Prescriptions prescription = new Prescriptions();

        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            prescription.create(dbcon, clientid, employee_id, drug_name, dosage, Boolean.valueOf(is_repeat), date_start, date_end);
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Format the start and end date so that it can be compared against a string
        String startDateFormatted = removeBrackets(Arrays.toString(prescription.getDateStart()));
        String endDateFormatted = removeBrackets(Arrays.toString(prescription.getDateEnd()));

        // If the prescription set has the same dates, drug name and dosage then the prescription has successfully been added to the database
        if ((startDateFormatted.equals(date_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) &&  (endDateFormatted.equals(date_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) && (removeBrackets(Arrays.toString(prescription.getDosage())).equals(dosage)) && (removeBrackets(Arrays.toString(prescription.getDrugName())).equals(drug_name))){            
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