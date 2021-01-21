/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
public class RespondToPendingPrescriptionExtensionsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession loginSession = request.getSession(false);
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
           
            
            List<PendingPrescriptionExtensions> ppes = new PendingPrescriptionExtensions().getAll(dbcon);

            Prescriptions pres = new Prescriptions();
                        
            String prescriptionlist = "<table>";

            for (int i = 0; i < ppes.size(); i++) {
                pres.retreivePrescriptionByPrescriptionId(dbcon, ppes.get(i).getPrescriptionId());
                prescriptionlist += "<tr><td>" + ppes.get(i).getId() +"</td>" +
                        "<td>" + ppes.get(i).getPrescriptionId() + "</td>" +
                        "<td>" + new Client().retrieveClientByClientId(dbcon, pres.getClient()).getFirstname() + " " + new Client().retrieveClientByClientId(dbcon, pres.getClient()).getLastname() + "</td>" +
                        "<td>" + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[0]).getFirstname() + " " + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[0]).getLastname() + "</td>" +
                        "<td>" + pres.getDrugName()[0] + "</td>" +
                        "<td>" + pres.getDosage()[0] + "</td>" +
                        "<td>" + (pres.getIsRepeat()[0] ? "Repeat" : "One-time") + "</td>" +
                        "<td>" + pres.getDateStart()[0] + "</td>" +
                        "<td>" + pres.getDateEnd()[0] + "</td>" +
                        "<td>" + ppes.get(i).getNewEndDate() + "</td>" +
                        "</tr>";
            }

            prescriptionlist += "</table>";
            
            request.setAttribute("prescriptionlist", prescriptionlist);
            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "Successfully displaying data");

            request.getRequestDispatcher("pages/ViewPendingPrescriptionExtensions.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(RespondToPendingPrescriptionExtensionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession loginSession = request.getSession(false);
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
           
            int approvalid = Integer.parseInt(request.getParameter("approvalid").toString());
            String approvalResponse = request.getParameter("response").toString();
            if (approvalResponse.equals("approve")) {
                new PendingPrescriptionExtensions().approve(dbcon, approvalid);
            } else {
                new PendingPrescriptionExtensions().deny(dbcon, approvalid);
            }
            
            List<PendingPrescriptionExtensions> ppes = new PendingPrescriptionExtensions().getAll(dbcon);

            Prescriptions pres = new Prescriptions();
                        
            String prescriptionlist = "<table>";

            for (int i = 0; i < ppes.size(); i++) {
                pres.retreivePrescriptionByPrescriptionId(dbcon, ppes.get(i).getPrescriptionId());
                prescriptionlist += "<tr><td>" + ppes.get(i).getId() +"</td>" +
                        "<td>" + ppes.get(i).getPrescriptionId() + "</td>" +
                        "<td>" + new Client().retrieveClientByClientId(dbcon, pres.getClient()).getFirstname() + " " + new Client().retrieveClientByClientId(dbcon, pres.getClient()).getLastname() + "</td>" +
                        "<td>" + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[0]).getFirstname() + " " + new Employee().retrieveEmployeeByEmployeeId(dbcon, pres.getEmployeeId()[0]).getLastname() + "</td>" +
                        "<td>" + pres.getDrugName()[0] + "</td>" +
                        "<td>" + pres.getDosage()[0] + "</td>" +
                        "<td>" + (pres.getIsRepeat()[0] ? "Repeat" : "One-time") + "</td>" +
                        "<td>" + pres.getDateStart()[0] + "</td>" +
                        "<td>" + pres.getDateEnd()[0] + "</td>" +
                        "<td>" + ppes.get(i).getNewEndDate() + "</td>" +
                        "</tr>";
            }

            prescriptionlist += "</table>";
            
            request.setAttribute("prescriptionlist", prescriptionlist);
            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "Response for extension submitted");
            request.getRequestDispatcher("pages/ViewPendingPrescriptionExtensions.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(RespondToPendingPrescriptionExtensionsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
