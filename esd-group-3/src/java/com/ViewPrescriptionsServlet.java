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
            
            request.setAttribute("prescriptionlist", prescriptionlist);
            
            int appointmentid = Integer.parseInt(request.getParameter("prescriptionid"));
            LocalDate newEndDate = LocalDate.parse(request.getParameter("newEndDate"));
            PendingPrescriptionExtensions ppe = new PendingPrescriptionExtensions();
            ppe.requestRepeatPrescriptionExtension(dbcon, appointmentid, newEndDate);
            
            request.setAttribute("message", "Request for extension submitted");

            request.getRequestDispatcher("pages/ViewPrescriptions.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewPrescriptionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
