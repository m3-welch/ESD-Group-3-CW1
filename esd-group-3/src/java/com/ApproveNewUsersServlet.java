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
import java.util.ArrayList;
import java.util.Collections;
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
        ArrayList<Integer> ids = new ArrayList<>();
        
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
            ids.add(clients.get(i).getId());
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
            ids.add(employees.get(i).getId());
        }
        
        employeeList += "</table>";
        request.setAttribute("employeeList", employeeList);
        
        Collections.sort(ids);
        String approvaloptions = "";
           
        for (int i = 0; i < ids.size(); i++) {
            approvaloptions += "<option value='" + ids.get(i) + "'>" + ids.get(i) + "</option>";
        }
        request.setAttribute("approvaloptions", approvaloptions);
        
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
            
            Client client = new Client();
            Employee emp = new Employee();
            List<Employee> employees = null;
            List<Client> clients = null;
            
            // Retreive clients
            clients = client.retrieveSignups(dbcon);
            // Retreive employees
            employees = emp.retrieveSignups(dbcon);
            
            if (!approvalResponse.equals("deny")) {
                // Find client, and add if found remove
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getId() == approvalid) {
                        client.create(dbcon, 
                                clients.get(i).getUsername(),
                                clients.get(i).getPassword(),
                                clients.get(i).getFirstname(),
                                clients.get(i).getLastname(),
                                clients.get(i).getEmail(),
                                clients.get(i).getAddress(), 
                                clients.get(i).getRole(), 
                                clients.get(i).getClientType(), 
                                clients.get(i).getDob());
                    }
                }
                // Find employee, and add if found remove
                for (int i = 0; i < employees.size(); i++) {
                    if (employees.get(i).getId() == approvalid) {
                        emp.create(dbcon, 
                                employees.get(i).getUsername(),
                                employees.get(i).getPassword(),
                                employees.get(i).getFirstname(),
                                employees.get(i).getLastname(),
                                employees.get(i).getEmail(),
                                employees.get(i).getAddress(), 
                                employees.get(i).getRole(), 
                                employees.get(i).isFullTime().toString(), 
                                employees.get(i).getDob());
                    }
                }
                request.setAttribute("messagecolour", "#329232");
                request.setAttribute("message", "Created new user");
            }
            else {
                request.setAttribute("messagecolour", "#FF3232");
                request.setAttribute("message", "User removed from approval system");
            }
            // Remove from DB
            String query = "DELETE FROM SignupApproval WHERE id = " + approvalid;
            try (Statement stmt = dbcon.conn.createStatement()) {
                stmt.execute(query);
            } catch (SQLException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Retreive clients
            clients = client.retrieveSignups(dbcon);
            // Retreive employees
            employees = emp.retrieveSignups(dbcon);
            ArrayList<Integer> ids = new ArrayList<>();
            
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
                ids.add(clients.get(i).getId());
            }

            clientList += "</table>";
            request.setAttribute("clientList", clientList);

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
                ids.add(employees.get(i).getId());
            }

            employeeList += "</table>";
            request.setAttribute("employeeList", employeeList);
            
            Collections.sort(ids);
            String approvaloptions = "";

            for (int i = 0; i < ids.size(); i++) {
                approvaloptions += "<option value='" + ids.get(i) + "'>" + ids.get(i) + "</option>";
            }
            request.setAttribute("approvaloptions", approvaloptions);
            
            request.getRequestDispatcher("pages/ApproveNewUsers.jsp").forward(request,response);
            
        } catch (SQLException ex) {
            Logger.getLogger(ApproveNewUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
