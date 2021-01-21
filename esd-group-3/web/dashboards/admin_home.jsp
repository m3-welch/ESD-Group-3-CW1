<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - Admin Home</title>
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
        <h1>Admin Dashboard</h1>
        <form action="/esd-group-3/NewUserServlet" method="GET">
            <input type="submit" value="Create a New User" class="link-button"/>
        </form>
        <form action="/esd-group-3/NewEmployeeServlet" method="GET">
            <input type="submit" value="Create a New Employee" class="link-button"/>
        </form>
        <form action="/esd-group-3/ViewUsersServlet" method="GET">
            <input type="submit" value="View Users" class="link-button"/>
        </form>
        <form action="/esd-group-3/ViewEmployeesServlet" method="GET">
            <input type="submit" value="View Employees" class="link-button"/>
        </form>
        <form action="/esd-group-3/ViewPatientsServlet" method="GET">
            <input type="submit" value="View Patients" class="link-button"/>
        </form>
        <form action="/esd-group-3/PricesViewer" method="GET">
            <input type="submit" value="View Price Allocation" class="link-button"/>
        </form>
        <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
            <input type="submit" value="View Turnover" class="link-button"> 
        </form>
        <form action="/esd-group-3/DisplayEventsServlet" method="GET">
            <input type="submit" value="View Schedule" class="link-button"> 
        </form>
    </div>
    </body>
</html>
