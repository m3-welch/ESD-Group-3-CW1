<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page import="models.Operation"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - Patient Home</title>
    </head>
    <body>
    <div class="top-banner">
        <div class="logout">        
            <form action="/esd-group-3/LogoutServlet" method="GET">
                <input type="submit" value="Logout" class="button logout"> 
            </form>
        </div>
      
        <div class="center">
            <h2>SmartCare</h2>
        </div>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Patient Dashboard</h1>
        
        <form action="/esd-group-3/NewAppointmentServlet" method="GET">
            <input type="submit" value="New Appointment" class="link-button"/>
        </form>
        <hr>
        <form action="/esd-group-3/ViewReferralsServlet" method="GET">
            <input type="submit" value="View Referrals" class="link-button"/>
        </form>
        <hr>
        <form action="/esd-group-3/ViewPrescriptionsServlet" method="GET">
            <input type="submit" value="View Prescriptions" class="link-button"/>
        </form>
        <hr>
        <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
            <input type="submit" value="View Appointments and Pay Invoices" class="link-button"/>
        </form>
    </div>
    </body>
</html>
