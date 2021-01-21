/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
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
import models.User;

/**
 *
 * @author Harrison B
 */
public class ApproveNewUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/ApproveNewUsers.jsp").include(request, response);
        
        // Declare vars
        Client client = new Client();
        Employee emp = new Employee();
        List<Employee> employees = null;
        List<Client> clients = null;
        
        // Retreive clients
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            clients = client.retrieveSignups(dbcon);
        } catch (SQLException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String clientList = "<table class='patients-table'>";
        
        for (int i = 0; i < clients.size(); i++) {
            clientList += "<tr><td>" + clients.get(i).getId() + "</td><td>" +
                    clients.get(i).getUsername() + "</td><td>" +
                    clients.get(i).getFirstname() + " " + 
                    clients.get(i).getLastname() + "</td><td>" + 
                    clients.get(i).getEmail() + "</td><td>" + 
                    clients.get(i).getAddress() + "</td><td>" +
                    clients.get(i).getDob() + "</td><td>" +
                    clients.get(i).getIsNhs() +"</td></tr>";
        }
        
        clientList += "</table>";
        request.setAttribute("clientList", clientList);
        
        // Retreive employees
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            employees = emp.retrieveSignups(dbcon);
        } catch (SQLException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String employeeList = "<table class='patients-table'>";
        
        for (int i = 0; i < employees.size(); i++) {
            employeeList += "<tr><td>" + employees.get(i).getId() + "</td><td>" +
                    employees.get(i).getUsername() + "</td><td>" +
                    employees.get(i).getFirstname() + " " + 
                    employees.get(i).getLastname() + "</td><td>" + 
                    employees.get(i).getEmail() + "</td><td>" + 
                    employees.get(i).getAddress() + "</td><td>" +
                    employees.get(i).getRole() + "</td><td>" +
                    employees.get(i).getDob() + "</td><td>" +
                    employees.get(i).isFullTimeToString() + "</td></tr>";
        }
        
        employeeList += "</table>";
        request.setAttribute("employeeList", employeeList);
        
        request.getRequestDispatcher("pages/ApproveNewUsers.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        try {
            HttpSession loginSession = request.getSession(false);
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            
            int approvalid = Integer.parseInt(request.getParameter("approvalid").toString());
            String approvalResponse = request.getParameter("response").toString();
            
            if (approvalResponse.equals("deny")) {
                String query = "DELETE FROM SignupApproval WHERE id = " + approvalid;
                try (Statement stmt = dbcon.conn.createStatement()) {
                    stmt.execute(query);
                } catch (SQLException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                query = ""
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ApproveNewUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
