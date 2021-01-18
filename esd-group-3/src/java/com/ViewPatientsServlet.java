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

/**
 *
 * @author morgan
 */
public class ViewPatientsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        HttpSession loginSession = request.getSession();
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        //decare vars
        String filter = request.getParameter("filter");
        
        if ("NHS".equals(filter)) {
            request.setAttribute("checkednhs", "checked='true'");
        } else if ("private".equals(filter)) {
            request.setAttribute("checkedprivate", "checked='true'");
        } else if ("all".equals(filter)) {
            request.setAttribute("checkedcombined", "checked='true'");
        }
        
        Client client = new Client();
        List<Client> clients = null;        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            clients = client.getAll(dbcon, filter);
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String outputList = "<table class='patients-table'>";
        
        for (int i = 0; i < clients.size(); i++) {
            outputList += "<tr><td>" + clients.get(i).getClientId() + "</td><td>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</td><td>" + (clients.get(i).getIsNhs().equals("true") ? "NHS" : "Private") + "</td></td>";
        }
        
        outputList += "</table>";
        request.setAttribute("patientlist", outputList);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
        response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
    }
    
}
