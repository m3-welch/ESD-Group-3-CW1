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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;
import models.Employee;
import models.PendingPrescriptionExtensions;
import models.Prescriptions;

/**
 *
 * @author morgan
 */
public class ViewPrescriptionsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            
            HttpSession loginSession = request.getSession(false);
            int userid = Integer.parseInt(loginSession.getAttribute("userID").toString());
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client().retrieveClientByUserId(dbcon, userid);
            int clientid = client.getClientId();
            
            Prescriptions pres = new Prescriptions();
            pres.retreivePrescriptions(dbcon, clientid);
            
            String prescriptionlist = "<table>";
            
            if (pres.getEmployeeId().length > 0) {
                for (int i = 0; i < pres.getId().length; i++) {
                    prescriptionlist += "<tr><td>" + pres.getId()[i] +"</td>" +
                        "<td>" + client.getFirstname() + " " + client.getLastname() + "</td>" +
                        "<td>" + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[i]).getFirstname() + " " + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[i]).getLastname() + "</td>" +
                        "<td>" + pres.getDrugName()[i] + "</td>" +
                        "<td>" + pres.getDosage()[i] + "</td>" +
                        "<td>" + (pres.getIsRepeat()[i] ? "Repeat" : "One-time") + "</td>" +
                        "<td>" + pres.getDateStart()[i] + "</td>" +
                        "<td>" + pres.getDateEnd()[i] + "</td>" +                      
                        "</tr>";
                }
            }
            
            prescriptionlist += "</table>";
            
            request.setAttribute("prescriptionlist", prescriptionlist);
            
            pres.retreiveRepeatPrescriptions(dbcon, clientid);
            
            String prescriptionoptions = "<table>";
            
            if (pres.getEmployeeId().length > 0) {
                for (int i = 0; i < pres.getId().length; i++) {
                    prescriptionoptions += "<option value='" + pres.getId()[i] + "'>" + pres.getDrugName()[i] + "</option>";
                }
            }
            
            prescriptionoptions += "</table>";
            
            request.setAttribute("prescriptionoptions", prescriptionoptions);
            
            request.setAttribute("message", "Displaying data successfully");
            request.getRequestDispatcher("pages/ViewPrescriptions.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewPrescriptionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            
            HttpSession loginSession = request.getSession(false);
            int userid = Integer.parseInt(loginSession.getAttribute("userID").toString());
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client().retrieveClientByUserId(dbcon, userid);
            int clientid = client.getClientId();
            
            Prescriptions pres = new Prescriptions();
            pres.retreivePrescriptions(dbcon, clientid);
            
            String prescriptionlist = "<table>";
            
            for (int i = 0; i < pres.getId().length; i++) {
                prescriptionlist += "<tr><td>" + pres.getId()[i] +"</td>" +
                    "<td>" + client.getFirstname() + " " + client.getLastname() + "</td>" +
                    "<td>" + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[i]).getFirstname() + " " + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[i]).getLastname() + "</td>" +
                    "<td>" + pres.getDrugName()[i] + "</td>" +
                    "<td>" + pres.getDosage()[i] + "</td>" +
                    "<td>" + (pres.getIsRepeat()[i] ? "Repeat" : "One-time") + "</td>" +
                    "<td>" + pres.getDateStart()[i] + "</td>" +
                    "<td>" + pres.getDateEnd()[i] + "</td>" +                      
                    "</tr>";
            }
            
            prescriptionlist += "</table>";
            
            pres.retreiveRepeatPrescriptions(dbcon, clientid);
            
            request.setAttribute("prescriptionlist", prescriptionlist);
            
            String prescriptionoptions = "<table>";
            
            if (pres.getEmployeeId().length > 0) {
                for (int i = 0; i < pres.getId().length; i++) {
                    prescriptionoptions += "<option value='" + pres.getId()[i] + "'>" + pres.getDrugName()[i] + "</option>";
                }
            }
            
            prescriptionoptions += "</table>";
            
            request.setAttribute("prescriptionoptions", prescriptionoptions);
            
            int prescriptionid = Integer.parseInt(request.getParameter("prescriptionid"));
            LocalDate newEndDate = LocalDate.parse(request.getParameter("newEndDate"));
            
            Prescriptions prescription = new Prescriptions();
            prescription.retreivePrescriptionByPrescriptionId(dbcon, prescriptionid);
            
            if (!prescription.getIsRepeat()[0] || prescription.getDateEnd()[0].isAfter(newEndDate)) {
                request.setAttribute("message", "Request for extension failed. Please select a repeat prescription and set the new end date after the current end date.");
                request.getRequestDispatcher("pages/ViewPrescriptions.jsp").forward(request,response);
            } else {
                PendingPrescriptionExtensions ppe = new PendingPrescriptionExtensions();
                ppe.requestRepeatPrescriptionExtension(dbcon, prescriptionid, newEndDate);

                request.setAttribute("message", "Request for extension submitted");

                request.getRequestDispatcher("pages/ViewPrescriptions.jsp").forward(request,response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewPrescriptionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
