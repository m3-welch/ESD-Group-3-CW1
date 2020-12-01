/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;  
import dbcon.DBConnection;
import models.User;

/**
 *
 * @author Austin H
 */
public class LoginServlet extends HttpServlet {  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
                           throws ServletException, IOException {  
        response.setContentType("text/html");  
        PrintWriter out=response.getWriter();  
          
        request.getRequestDispatcher("link.html").include(request, response);  
          
        // declare vars
        String user_in = request.getParameter("username");  
        String password_in = request.getParameter("password");  
        String actual_password = "";
        String user_role = "";
        int user_type = 0;
        
        // get password from db
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            User user_to_login = dbcon.getUserByUsername(user_in);
            actual_password = user_to_login.getPassword();
            user_role = user_to_login.getRole();
        }
        catch(SQLException e){
            // TODO: some error
            // sc.setAttribute("error", e); 
            
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
                    // no role assigned
                    user_type = 0;
                    out.print("ERROR - No Role Assigned");
                    break;
            }
        }
        else {
            // password mismatch
            out.print("ERROR - Username or Password Incorrect"); 
            user_type = 0;
        }
        
        // results of login attempt
        if (user_type != 0) {
            // sucessful login response
            out.print("You are successfully logged in!");  
            out.print("<br>Welcome, " + user_in);  
            
            // cookie creation
            Cookie ck_username = new Cookie("username", user_in);
            Cookie ck_role = new Cookie("role", Integer.toString(user_type));
            // set timeout of cookies (1200s = 20m)
            ck_username.setMaxAge(1200);
            ck_role.setMaxAge(1200);
            response.addCookie(ck_username);  
            response.addCookie(ck_role);  
        }
        else {
            // bad login response
            request.getRequestDispatcher("login.html").include(request, response);
        }
        
        // TODO: Send user to correct page based on their role/login
        
        out.close();
        
    }  
  
}  
