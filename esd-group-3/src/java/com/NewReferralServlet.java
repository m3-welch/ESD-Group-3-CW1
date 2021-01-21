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
import models.Referrals;

/**
 *
 * @author Harrison B
 */
public class NewReferralServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
            
            for (int i = 0; i < clients.size(); i++) {
                clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
            
            request.setAttribute("clientoptions", clientoptions);
            request.setAttribute("userid", loginSession.getAttribute("userID"));
            request.getRequestDispatcher("pages/NewReferral.jsp").forward(request,response);
        }
        catch (SQLException ex) {
            Logger.getLogger(NewEmployeeAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        request.setAttribute("userid", loginSession.getAttribute("userID"));
        response.setContentType("text/html");

        request.getRequestDispatcher("pages/NewReferral.jsp").include(request, response);

        //decare vars
        int employee_userid = Integer.parseInt(request.getParameter("employee_userid"));
        int clientid = Integer.parseInt(request.getParameter("clientoptions"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        Referrals ref = new Referrals();
        int count = 0;
        int employeeid = 0;
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            employeeid = new Employee().retrieveEmployeeByUserId(dbcon, employee_userid).getEmployeeId();
            ref.create(dbcon, employeeid, clientid, name, address);
            
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
            
            for (int i = 0; i < clients.size(); i++) {
                clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
            request.setAttribute("clientoptions", clientoptions);
        } catch (SQLException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (ref.getClientId() == clientid && ref.getEmployeeId() == employeeid) {
            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "New Referral added to client");
            request.getRequestDispatcher("pages/NewReferral.jsp").forward(request,response);
        } else {
            request.setAttribute("messagecolour", "#FF3232");
            request.setAttribute("message", "Error! - Referral failed to add");
            request.getRequestDispatcher("pages/NewReferral.jsp").forward(request,response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Adds referral to db through calls to class in models package";
    }

}
