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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;
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
        try {
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
            
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
            
            for (int i = 0; i < clients.size(); i++) {
                clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
            
            response.setContentType("text/html");
            request.setAttribute("clientoptions", clientoptions);
            
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        request.getRequestDispatcher("pages/CreatePrescriptions.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        try {
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
            
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
            
            for (int i = 0; i < clients.size(); i++) {
                clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
            
            response.setContentType("text/html");
            request.setAttribute("clientoptions", clientoptions);
            
        } catch (SQLException ex) {
            Logger.getLogger(CreatePrescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

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

        int clientid = Integer.parseInt(request.getParameter("clientoptions"));
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
        if (prescription.getDateStart()[0].isEqual(date_start) && prescription.getDateEnd()[0].isEqual(date_end)) {            
            request.setAttribute("message", "New Prescription successfully created!");
            request.getRequestDispatcher("pages/CreatePrescriptions.jsp").forward(request,response);
        } else {
            request.setAttribute("message", "Error! - New Prescription not created");
            request.getRequestDispatcher("pages/CreatePrescriptions.jsp").forward(request,response);
        }
    }
} 