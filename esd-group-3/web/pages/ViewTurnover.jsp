<%-- 
    Document   : invoiceViewer
    Created on : 10-Dec-2020, 15:11:56
    Author     : Austin
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Operation"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="java.time.LocalDate"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/home.css">
        <title>SmartCare: Turnover Viewer</title>
    </head>
    <body>
        <div class="top-banner">
                <div class="logout">        
                    <form action="/esd-group-3/LogoutServlet" method="GET">
                        <input type="submit" value="Logout" class="button logout"> 
                    </form>
                </div>

                <div class="center">
                    <a href="${dashboard}"><h2>SmartCare</h2></a>
                </div>
            </div>   

            <div class="logout">        
            <form action="/esd-group-3/LogoutServlet" method="GET">
                <input type="submit" value="logout" class="button logout"> 
            </form>
            </div>

            <div class="center">
              <a href="${dashboard}"><h2>SmartCare</h2></a>
            </div>

        </div>
        <div class="content">    
            <div> <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p> </div>

            <h3>Displaying All Operations</h3> 
            
            <div>   
                <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                    <select name="filter" id="filter">
                        <option value="all">All Invoices</option>
                        <option value="nhs">NHS Patient Invoices</option>
                        <option value="private">Private Patient Invoices</option>
                    </select>

                    <label for="start">Start date:</label>
                    <input type="date" id="startDate" name="start"
                            value="${todaydate}"
                            min="${minusyear}"
                            max="${maxdate}" >
                    <label for="start">End date:</label>
                    <input type="date" id="endDate" name="end"
                           value="${onemonth}"
                            min="${minusyear}"
                            max="${maxdate}" >

                    <input type="submit" value="Update" class="button" style="color:#000000"> 
                </form>
            </div>

            <div>
                <table class='events-table-header'>
                    <tr>
                        <th>Appointment ID</th>
                        <th>Date</th>
                        <th>Patient name</th>
                        <th>Employee Name</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Description</th>
                        <th>Type of Appointment</th>
                        <th>Charge</th>
                        <th>Has Patient Paid</th>
                        <th>Is Patient NHS</th>
                    </tr>
                </table>
                <div class="events-list">
                    ${tableData}
                </div>
            </div>
            <hr/> 
            Turnover for this period: ${turnover}
        </div>
    </body>
</html>
