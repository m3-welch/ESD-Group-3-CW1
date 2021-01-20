/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Employee;

/**
 *
 * @author morgan
 */
public class NewEmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        response.setContentType("text/html");
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        request.getRequestDispatcher("pages/NewEmployee.jsp").forward(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/NewEmployee.jsp").include(request, response);
        
        //decare vars
        String username = request.getParameter("uname");
        String password = request.getParameter("psw");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String type = request.getParameter("type");
        String isFullTime = request.getParameter("isFullTime");
        
        Employee employee = new Employee();
        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            employee.create(dbcon, username, password, firstname, lastname, email, address, type, isFullTime);
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (employee.getUsername().equals(username) && employee.getRole().equals(type)) {
            request.setAttribute("message", "New Employee successfully created!");
            request.getRequestDispatcher("pages/NewEmployee.jsp").forward(request,response);
        } else {
            request.setAttribute("message", "Error! - New Employee not created");
            request.getRequestDispatcher("pages/NewEmployee.jsp").forward(request,response);
        }
    }
}
