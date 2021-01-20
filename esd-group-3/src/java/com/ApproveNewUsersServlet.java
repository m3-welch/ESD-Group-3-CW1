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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;
import models.Employee;

/**
 *
 * @author Harrison B
 */
public class ApproveNewUsersServlet {
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
            clientList += "<tr><td>" + clients.get(i).getId() + 
                    "</td><td>" + clients.get(i).getFirstname() + " " + 
                    clients.get(i).getLastname() + "</td><td>" + 
                    clients.get(i).getRole() + "</td><td>" + 
                    clients.get(i).getEmail() + "</td><td>" + 
                    clients.get(i).getAddress() + "</td><td>" +
                    clients.get(i).getDob() +"</td></tr>";
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
            employeeList += "<tr><td>" + employees.get(i).getId() + 
                    "</td><td>" + employees.get(i).getFirstname() + " " + 
                    employees.get(i).getLastname() + "</td><td>" + 
                    employees.get(i).getRole() + "</td><td>" + 
                    employees.get(i).getEmail() + "</td><td>" + 
                    employees.get(i).getAddress() + "</td><td>" +
                    employees.get(i).getDob() +"</td></tr>";
        }
        
        employeeList += "</table>";
        request.setAttribute("employeeList", employeeList);
        
        request.getRequestDispatcher("pages/ApproveNewUsers.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        request.getRequestDispatcher("pages/ViewEmployees.jsp").include(request, response);
        
        //decare vars
        String filter = request.getParameter("filter");
        
        if ("doctor".equals(filter)) {
            request.setAttribute("checkeddoctor", "checked='true'");
            request.setAttribute("checkednurse", "");
            request.setAttribute("checkedcombined", "");
        } else if ("nurse".equals(filter)) {
            request.setAttribute("checkeddoctor", "");
            request.setAttribute("checkednurse", "checked='true'");
            request.setAttribute("checkedcombined", "");
        } else if ("all".equals(filter)) {
            request.setAttribute("checkeddoctor", "");
            request.setAttribute("checkednurse", "");
            request.setAttribute("checkedcombined", "checked='true'");
        }
        
        Employee emp = new Employee();
        List<Employee> employees = null;
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            employees = emp.filteredRetrieveAllEmployees(dbcon, filter);
        } catch (SQLException ex) {
            Logger.getLogger(PatientSignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String outputList = "<table class='patients-table'>";
        
        for (int i = 0; i < employees.size(); i++) {
            outputList += "<tr><td>" + employees.get(i).getEmployeeId() + 
                    "</td><td>" + employees.get(i).getFirstname() + " " + 
                    employees.get(i).getLastname() + "</td><td>" + 
                    employees.get(i).getRole() + "</td><td>" + 
                    employees.get(i).getEmail() + "</td><td>" + 
                    employees.get(i).getAddress() + "</td><td>" +
                    employees.get(i).getDob() + "</td></tr>";
        }
        
        outputList += "</table>";
        request.setAttribute("employeelist", outputList);
        request.getRequestDispatcher("pages/ViewEmployees.jsp").forward(request,response);
    }
}
