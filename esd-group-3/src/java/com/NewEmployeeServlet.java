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
import models.Price;

/**
 *
 * @author morgan
 */
public class NewEmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        request.getRequestDispatcher("newPatient.jsp").include(request, response);
        
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
        
        HttpSession loginSession = request.getSession();

        if (employee.getUsername().equals(username) && employee.getRole().equals(type)) {
            request.setAttribute("message", "New Employee successfully created!");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - New Employee not created");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }
}
