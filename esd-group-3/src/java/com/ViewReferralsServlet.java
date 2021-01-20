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
import models.Events;
import models.Operation;
import models.Referrals;

/**
 *
 * @author morgan
 */
public class ViewReferralsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            HttpSession loginSession = request.getSession(false);
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            
            int id;
            
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Referrals ref = new Referrals();

            String id_type = "";
            
            if (loginSession.getAttribute("user_role").equals("client")) {
                id = new Client().retrieveClientByUserId(dbcon, Integer.parseInt((String)loginSession.getAttribute("userID").toString())).getClientId();
                id_type = "clientid";
            } else {
                id = new Employee().retrieveEmployeeByUserId(dbcon, Integer.parseInt(loginSession.getAttribute("userID").toString())).getEmployeeId();
                id_type = "employeeid";
            }
            
            List<Referrals> refs = ref.getAllFor(dbcon, id_type, id);
            
            String referrallist = "<table class='patients-table'>";
        
            for (int i = 0; i < refs.size(); i++) {
                referrallist += "<tr><td>" + new Client().retrieveClientByClientId(dbcon, refs.get(i).getClientId()).getFirstname() + " "
                        + new Client().retrieveClientByClientId(dbcon, refs.get(i).getClientId()).getLastname() + "</td><td>" 
                        + new Employee().retrieveEmployeeByEmployeeId(dbcon, refs.get(i).getEmployeeId()).getFirstname() + " "
                        + new Employee().retrieveEmployeeByEmployeeId(dbcon, refs.get(i).getEmployeeId()).getLastname()
                        + "</td><td>" + refs.get(i).getName() + "</td><td>" + refs.get(i).getAddress() + "</td></tr>";
            }

            referrallist += "</table>";
            request.setAttribute("referrallist", referrallist);
            
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");

            request.getRequestDispatcher("pages/ViewReferrals.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewReferralsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            response.setContentType("text/html");
            HttpSession loginSession = request.getSession(false);
            request.getRequestDispatcher("pages/ViewReferrals.jsp").include(request, response);
            
            int id;
            
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            
            if (loginSession.getAttribute("user_role").equals("client")) {
                id = new Client().retrieveClientByUserId(dbcon, Integer.parseInt((String)loginSession.getAttribute("userID"))).getClientId();
            } else {
                id = new Employee().retrieveEmployeeByUserId(dbcon, Integer.parseInt((String)loginSession.getAttribute("userID"))).getEmployeeId();
            }
            
            
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
            
            request.getRequestDispatcher("pages/ViewReferrals.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(ViewReferralsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
