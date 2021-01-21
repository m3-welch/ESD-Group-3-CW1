/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import dbcon.DBConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import models.Client;
import models.Employee;
import models.User;

/**
 *
 * @author Austin H
 */
public class LoginServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");
          
        request.getRequestDispatcher("login.jsp").include(request, response);
          
        // declare vars
        String user_in = request.getParameter("uname");  
        String password_in = request.getParameter("psw");  
        String actual_password = "";
        String user_role = "";
        int user_id = 0;
        int user_type = 0;
        DBConnection dbcon = null;
        HttpSession loginSession = request.getSession();
        User user_to_login = null;
        // get password from db
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            user_to_login = new User();
            user_to_login.retrieveByUsername(dbcon, user_in);
            if (user_to_login.getUsername() == null) {
                // if username mismatch, send error
                request.setAttribute("message", "Error - Invalid Username"); // Will be available as ${message}
                request.getRequestDispatcher("login.jsp").forward(request,response);
                response.sendRedirect("login.jsp");
            }
            actual_password = user_to_login.getPassword();
            user_role = user_to_login.getRole();
            
            // get list of employees
            Employee employee = new Employee();
            List<Employee> employees = employee.retrieveAllEmployees(dbcon);
            
            String doctornurseoptions = "";
           
            for (int i = 0; i < employees.size(); i++) {
                    doctornurseoptions += "<option value='" + employees.get(i).getId() + "'>" + employees.get(i).getFirstname() + " " + employees.get(i).getLastname() + "</option>";
            }
        
            request.setAttribute("doctornurseoptions", doctornurseoptions);
            
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
           
            for (int i = 0; i < clients.size(); i++) {
                    clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
        
            request.setAttribute("clientoptions", clientoptions);
            request.setAttribute("doctornurseoptions", doctornurseoptions);
            request.setAttribute("checkednhs", "");
            request.setAttribute("checkedprivate", "");
            request.setAttribute("checkedcombined", "checked='true'");
            
            String outputList = "<table class='patients-table'>";
        
            for (int i = 0; i < clients.size(); i++) {
                outputList += "<tr><td>" + clients.get(i).getClientId() + "</td><td>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</td><td>" + (clients.get(i).getIsNhs().equals("true") ? "NHS" : "Private") + "</td></tr>";
            }
            
            outputList += "</table>";
            
            request.setAttribute("patientslist", outputList);

            
            LocalDate today = LocalDate.now();
            request.setAttribute("todaydate", today.toString());
            
            LocalDate oneYear = today.plusYears(1);
            request.setAttribute("maxdate", oneYear.toString());
            
            LocalDate oneMonth = today.plusMonths(1);
            request.setAttribute("onemonth", oneMonth.toString());
            
            LocalDate minusYear = today.minusYears(1);
            request.setAttribute("minusyear", minusYear.toString());
            
            LocalTime now = LocalTime.now();
            LocalTime tenmins = now.plusMinutes(10);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            String timeLabel = new String(now.format(dtf));
            
            String timeLabelTenMins = new String(tenmins.format(dtf));
            
            request.setAttribute("nowtime", timeLabel);
            request.setAttribute("tenmins", timeLabelTenMins);
            user_id = user_to_login.getId();
        }
        catch(SQLException e){
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher("login.jsp").forward(request,response);
            response.sendRedirect("login.jsp");
        }
                
        if (actual_password.equals(password_in)) {
            // password match
            // set login role
            // 0 = bad login, 1 = doctor, 2 = nurse, 3 = client, 4 = admin
            switch(user_role) {
                case "doctor":
                    user_type = 1;
                    break;
                case "nurse":
                    user_type = 2;
                    break;
                case "client":
                    user_type = 3;
                    break;
                case "admin":
                    user_type = 4;
                    break;
                default:
                    // no role assigned, user has no type, send error
                    user_type = 0;
                    request.setAttribute("message", "Error - No Role assigned to User"); // Will be available as ${message}
                    request.getRequestDispatcher("login.jsp").forward(request,response);
                    response.sendRedirect("login.jsp");
                    break;
            }
        }
        
        // results of login attempt
        if (user_type != 0) {
            // httpSession creation, store: name - role - timeout(20*60=20 mins)
            loginSession.setAttribute("name",user_in);
            loginSession.setAttribute("role",user_type);
            loginSession.setAttribute("userID",user_id);
            loginSession.setAttribute("user_role", user_role);
            loginSession.setAttribute("dashboard", "dashboards/" + user_role + "_home.jsp");
            request.setAttribute("dashboard", "/esd-group-3/dashboards/" + user_role + "_home.jsp");
            loginSession.setMaxInactiveInterval(20*60);
            
            // sucessful login response
            request.setAttribute("message", "Successful Login - Welcome " + user_in); // Will be available as ${message}
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
        }
        else {
            // bad login response
            request.setAttribute("message", "Error - Invalid Password"); // Will be available as ${message}
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        
    }  
  
}  