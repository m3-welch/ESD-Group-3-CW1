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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/ViewPatients.jsp").include(request, response);
        
        //decare vars
        String filter = "all";
        
        request.setAttribute("checkednhs", "");
        request.setAttribute("checkedprivate", "");
        request.setAttribute("checkedcombined", "checked='true'");
        
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
            outputList += "<tr><td>" + clients.get(i).getClientId() + "</td><td>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</td><td>" + (clients.get(i).getIsNhs() ? "NHS" : "Private") + "</td><td>" + clients.get(i).getEmail() + "</td><td>" + clients.get(i).getAddress() + "</td></tr>";
        }
        
        outputList += "</table>";
        request.setAttribute("patientlist", outputList);
        request.getRequestDispatcher("pages/ViewPatients.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        request.getRequestDispatcher("pages/ViewPatients.jsp").include(request, response);
        
        
        String delete = "";
        
        //request.getRequestDispatcher("admin_home.jsp").include(request, response);
        
        try{
            delete = request.getParameter("delete_patient");            
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        if ("delete".equals(delete)) {
            try {
                DBConnection dbcon = new DBConnection("smartcaretest", "", "");
                Client deleteClient = new Client();
                deleteClient.retrieveClientByIdDrop(dbcon, Integer.parseInt(request.getParameter("clientId")));
                deleteClient.dropUserById(dbcon, deleteClient.getClientId());
            } catch (SQLException ex) {
                Logger.getLogger(ViewPatientsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //decare vars
        String filter = request.getParameter("filter");
        
        if ("NHS".equals(filter)) {
            request.setAttribute("checkednhs", "checked='true'");
            request.setAttribute("checkedprivate", "");
            request.setAttribute("checkedcombined", "");
        } else if ("private".equals(filter)) {
            request.setAttribute("checkednhs", "");
            request.setAttribute("checkedprivate", "checked='true'");
            request.setAttribute("checkedcombined", "");
        } else if ("all".equals(filter)) {
            request.setAttribute("checkednhs", "");
            request.setAttribute("checkedprivate", "");
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
            outputList += "<tr><form action='ViewPatientsServlet' method='POST'><td><input type='text' value='" + clients.get(i).getClientId() + "' name='clientId' readonly>" + "</td><td>" +
                    clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</td><td>" +
                    (clients.get(i).getIsNhs().equals("true") ? "NHS" : "Private") + "</td><td>" +
                    clients.get(i).getEmail() + "</td><td>" +
                    clients.get(i).getAddress() + "</td><td>" +
                    clients.get(i).getDob() + "</td><td>" +
                    "<input type='submit' name='delete_patient' value='delete' class='button'/></td></form></tr>";
        }
        
        outputList += "</table>";
        request.setAttribute("patientlist", outputList);
        request.getRequestDispatcher("pages/ViewPatients.jsp").forward(request,response);
    }
    
}
