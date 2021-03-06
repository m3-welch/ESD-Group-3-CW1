/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import models.Operation;

/**
 *
 * @author Harrison B
 */
public class NewAppointmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            
            LocalTime now = LocalTime.now();
            LocalTime tenmins = now.plusMinutes(10);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            String timeLabel = new String(now.format(dtf));
            
            String timeLabelTenMins = new String(tenmins.format(dtf));
            
            request.setAttribute("nowtime", timeLabel);
            request.setAttribute("tenmins", timeLabelTenMins);
            
            request.setAttribute("userid", loginSession.getAttribute("userID"));
            
            // get list of employees
            Employee employee = new Employee();
            List<Employee> employees = employee.retrieveAllEmployees(dbcon);
            
            String doctornurseoptions = "";
           
            for (int i = 0; i < employees.size(); i++) {
                    doctornurseoptions += "<option value='" + employees.get(i).getId() + "'>" + employees.get(i).getFirstname() + " " + employees.get(i).getLastname() + "</option>";
            }
        
            request.setAttribute("doctornurseoptions", doctornurseoptions);
            
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
            request.getRequestDispatcher("pages/NewAppointment.jsp").forward(request,response);
        } catch (SQLException ex) {
            Logger.getLogger(NewEmployeeAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        HttpSession loginSession = request.getSession();
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
        response.setContentType("text/html");

        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            Client client = new Client();
            List<Client> clients = client.getAll(dbcon, "all");
            String clientoptions = "";
            
            for (int i = 0; i < clients.size(); i++) {
                clientoptions += "<option value='" + clients.get(i).getId() + "'>" + clients.get(i).getFirstname() + " " + clients.get(i).getLastname() + "</option>";
            }
            
            LocalTime now = LocalTime.now();
            LocalTime tenmins = now.plusMinutes(10);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            String timeLabel = new String(now.format(dtf));
            
            String timeLabelTenMins = new String(tenmins.format(dtf));
            
            request.setAttribute("nowtime", timeLabel);
            request.setAttribute("tenmins", timeLabelTenMins);
            
            request.setAttribute("userid", loginSession.getAttribute("userID"));
            
            // get list of employees
            Employee employee = new Employee();
            List<Employee> employees = employee.retrieveAllEmployees(dbcon);
            
            String doctornurseoptions = "";
           
            for (int i = 0; i < employees.size(); i++) {
                    doctornurseoptions += "<option value='" + employees.get(i).getId() + "'>" + employees.get(i).getFirstname() + " " + employees.get(i).getLastname() + "</option>";
            }
        
            request.setAttribute("doctornurseoptions", doctornurseoptions);
            
            request.setAttribute("todaydate", LocalDate.now().toString());
            request.setAttribute("maxdate", LocalDate.now().plusYears(1).toString());
            request.setAttribute("onemonth", LocalDate.now().plusMonths(1).toString());
            request.setAttribute("minusyear", LocalDate.now().minusYears(1).toString());
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);

        //decare vars
        String client_userid = request.getParameter("clientid");
        String employee_userid = request.getParameter("doctor-nurse");
        String type = request.getParameter("type");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime starttime = LocalTime.parse(request.getParameter("starttime"));
        LocalTime endtime = LocalTime.parse(request.getParameter("endtime"));
        String reason = request.getParameter("reason");

        //create appointment
        Operation operation = new Operation();
        
        DBConnection dbcon;
        
        Boolean isSurgery = null;
        
        if ("surgery".equals(type)) {
            isSurgery = true;
        } else {
            isSurgery = false;
        }
        
        Employee employee = new Employee();
        
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            operation.create(dbcon, Integer.parseInt(employee_userid), Integer.parseInt(client_userid), date, starttime, endtime, (float)0.00, false, isSurgery, reason);
        } catch (SQLException ex) {
            Logger.getLogger(NewAppointmentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (operation.getIsSurgery() == isSurgery && operation.getDate() == date) {
            request.setAttribute("messagecolour", "#329232");
            request.setAttribute("message", "Appointment successfully booked");
            request.getRequestDispatcher("pages/NewAppointment.jsp").forward(request,response);
        } else {
            request.setAttribute("messagecolour", "#FF3232");
            request.setAttribute("message", "Error! - Appointment failed to book. Please try again.");
            request.getRequestDispatcher("pages/NewAppointment.jsp").forward(request,response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Adds operation to db through calls to class in models package";
    }

}
