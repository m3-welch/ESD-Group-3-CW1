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
import models.Employee;
import models.User;
import models.Client;

/**
 *
 * @author Sam
 */
public class ViewUsersServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        //response.setContentType("text/html");
        
        request.getRequestDispatcher("pages/ViewUsers.jsp").include(request, response);
        
        User user = new Client();
        List<User> users = null;        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            users = user.retrieveAll(dbcon);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        String outputList = "<table class='users-table'>";
        
        for (int i = 0; i < users.size(); i++) {
            String selectStatement = "";
            if("doctor".equals(users.get(i).getRole())){
                selectStatement = "<option selected='selected' value='doctor'>doctor</doctor>" +
                        "<option value='nurse'>nurse</option>" +
                        "<option value='admin'>admin</option>" +
                        "<option value='client'>client</option>";
            } else if("nurse".equals(users.get(i).getRole())) {
                selectStatement = "<option value='doctor'>doctor</doctor>" +
                        "<option selected='selected' value='nurse'>nurse</option>" +
                        "<option value='admin'>admin</option>" +
                        "<option value='client'>client</option>";
            }else if("admin".equals(users.get(i).getRole())) {
                selectStatement = "<option value='doctor'>doctor</doctor>" +
                        "<option value='nurse'>nurse</option>" +
                        "<option selected='selected' value='admin'>admin</option>" +
                        "<option value='client'>client</option>";
            }else if("client".equals(users.get(i).getRole())) {
                selectStatement = "<option value='doctor'>doctor</doctor>" +
                        "<option value='nurse'>nurse</option>" +
                        "<option value='admin'>admin</option>" +
                        "<option selected='selected' value='client'>client</option>";
            }
            outputList += "<tr><form action='ViewUsersServlet' method='POST'><td>" + users.get(i).getId() + "</td><td>" +
                    "<input type='text' name='username' value='" + users.get(i).getUsername() + "' readonly/></td><td>" +
                    users.get(i).getFirstname() + " " + users.get(i).getLastname() + "</td><td>" +
                    users.get(i).getEmail() + "</td><td>" +
                    users.get(i).getAddress() + "</td><td>" +
                    "<select name='role'>" + selectStatement + "</select></td><td>" +
                    "<input type='submit' name='save' value='save' class='button'/></td></form></tr>";
        }
        
        outputList += "</table>";
        request.setAttribute("userlist", outputList);
        request.getRequestDispatcher("pages/ViewUsers.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        //response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        
        request.getRequestDispatcher("pages/ViewUsers.jsp").include(request, response);
        
        //decare vars
        String save = "";
        String uname = "";
        String role = "";
        String dbRole = "";
        
        
        //request.getRequestDispatcher("admin_home.jsp").include(request, response);
        
        try{
            save = request.getParameter("save");
            uname = request.getParameter("username");
            role = request.getParameter("role");     
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        if ("save".equals(save)) {
            try {
                DBConnection dbcon = new DBConnection("smartcaretest", "", "");
                User updateUser = new User();// create a client object to update the old one              
                User dbUser = new User();
                
                dbUser.retrieveByUsername(dbcon, uname);
                updateUser.retrieveByUsername(dbcon, uname);
                updateUser.setRole(role);
                dbRole = dbUser.getRole();
                if (("doctor".equals(role))||("nurse".equals(role))||("admin".equals(role))) {
                    if("doctor".equals(dbRole)||("nurse".equals(dbRole))||("admin".equals(dbRole))){//if update user and table user are of the same client or employee then just change role
                        dbUser.editUser(dbcon, "Users", dbUser.getUsername(), "Role", updateUser.getRole());//just change type in users
                    } else {
                        //remove dbUser from employee db
                        dbUser.dropUser(dbcon, dbUser.getUsername());
                        //add updateUser to client
                        Employee updateEmployee = new Employee();
                        updateEmployee.create(dbcon, updateUser.getUsername(), updateUser.getPassword(), updateUser.getFirstname(), updateUser.getLastname(), updateUser.getEmail(), updateUser.getAddress(), updateUser.getRole(), "", updateUser.getDob());
                    }
                        
                } else if("client".equals(role)){
                   if(!("client".equals(dbRole))){
                        //remove dbUser from client db
                        dbUser.dropUser(dbcon, dbUser.getUsername());
                        //add updateUser to employee db
                        Client updateClient = new Client();
                        updateClient.create(dbcon, updateUser.getUsername(), updateUser.getPassword(), updateUser.getFirstname(), updateUser.getLastname(), updateUser.getEmail(), updateUser.getAddress(), updateUser.getRole(), "", updateUser.getDob());
                    } 
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ViewPatientsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        User user = new Client();
        List<User> users = null;        
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            users = user.retrieveAll(dbcon);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        String outputList = "<table class='users-table'>";
        
        for (int i = 0; i < users.size(); i++) {
            String selectStatement = "";
            if(null != users.get(i).getRole())switch (users.get(i).getRole()) {
                case "doctor":
                    selectStatement = "<option selected='selected' value='doctor'>doctor</doctor>" +
                            "<option value='nurse'>nurse</option>" +
                            "<option value='admin'>admin</option>" +
                            "<option value='client'>client</option>";
                    break;
                case "nurse":
                    selectStatement = "<option value='doctor'>doctor</doctor>" +
                            "<option selected='selected' value='nurse'>nurse</option>" +
                            "<option value='admin'>admin</option>" +
                            "<option value='client'>client</option>";
                    break;
                case "admin":
                    selectStatement = "<option value='doctor'>doctor</doctor>" +
                            "<option value='nurse'>nurse</option>" +
                            "<option selected='selected' value='admin'>admin</option>" +
                            "<option value='client'>client</option>";
                    break;
                case "client":
                    selectStatement = "<option value='doctor'>doctor</doctor>" +
                            "<option value='nurse'>nurse</option>" +
                            "<option value='admin'>admin</option>" +
                            "<option selected='selected' value='client'>client</option>";
                    break;
                default:
                    break;
            }
            outputList += "<tr><form action='ViewUsersServlet' method='POST'><td>" + users.get(i).getId() + "</td><td>" +
                    "<input type='text' name='username' value='" + users.get(i).getUsername() + "' readonly/></td><td>" +
                    users.get(i).getFirstname() + " " + users.get(i).getLastname() + "</td><td>" +
                    users.get(i).getEmail() + "</td><td>" +
                    users.get(i).getAddress() + "</td><td>" +
                    "<select name='role'>" + selectStatement + "</select></td><td>" +
                    "<input type='submit' name='save' value='save' class='button'/></td></form></tr>";
        }
        
        outputList += "</table>";
        request.setAttribute("userlist", outputList);
        request.getRequestDispatcher("pages/ViewUsers.jsp").forward(request,response);
    }
}
