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
import javax.servlet.annotation.WebServlet;
import models.User;
import models.Bookings;

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
        int user_type = 0;
        DBConnection dbcon = null;
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
            HttpSession loginSession = request.getSession();
            loginSession.setAttribute("name",user_in);
            loginSession.setAttribute("role",user_type);
            loginSession.setAttribute("dashboard", "dashboards/" + user_role + "_home.jsp");
            loginSession.setMaxInactiveInterval(20*60);
            
            Bookings booking = new Bookings();
                    
            String currentdate = (LocalDate.now()).toString(); // Setting the current date to be able to be avialable as ${currentdate}
            int employeeid = booking.getIdFromDatabase(dbcon, user_in); // Get employee id from the databased, based on the currently logged in user
            
            System.out.println("---- " + (String)loginSession.getAttribute("dashboard") + " ----");
            
            // sucessful login response
            request.setAttribute("message", "Successful Login - Welcome " + user_in); // Will be available as ${message}
            request.setAttribute("currentdate", currentdate);
            request.setAttribute("employeeid", employeeid); // Will be available as ${employeeid}
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
        else {
            // bad login response
            request.setAttribute("message", "Error - Invalid Password"); // Will be available as ${message}
            request.getRequestDispatcher("login.jsp").forward(request,response);
            response.sendRedirect("login.jsp");
        }
        
    }  
  
}  
