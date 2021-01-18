<%-- 
    Document   : invoiceViewer
    Created on : 10-Dec-2020, 15:11:56
    Author     : Austin
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Operation"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/home.css">
        <title>SmartCare: Invoice Viewer</title>
    </head>
    <body>
        <div class="top-banner">

            <div class="dropdown">
                <button class="menu button">Menu</button>
                <div class="menu-content">
                    <a href="#">Administrator Home</a>
                    <form action="IssueInvoiceServlet" method="POST">
                        <input type="submit" value="Add Invoice" class="button logout"> 
                    </form>
                    <a href="#">Page 3</a>
                </div>
            </div>

            <div class="logout">        
            <form action="LogoutServlet" method="GET">
                <input type="submit" value="logout" class="button logout"> 
            </form>
            </div>

            <div class="center">
              <h2>SmartCare: Invoice Viewer</h2>
            </div>

        </div>
        <div class="content">    
            <div> <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p> </div>

            <h3>Displaying All Operations</h3> 
            
            <%  Date date = new Date();
                String currentDate= new SimpleDateFormat("yyyy-MM-dd").format(date);%>
            
            <div>   
                <form action="InvoiceViewerServlet" method="GET">
                    <select name="filter" id="filter">
                        <option value="all">All Invoices</option>
                        <option value="nhs">NHS Patient Invoices</option>
                        <option value="private">Private Patient Invoices</option>
                    </select>

                    <label for="start">Start date:</label>
                    <input type="date" id="start" name="start"
                           value=<%= currentDate %>
                           min="2000-01-01" max="2050-12-31">
                    <label for="end">End date:</label>
                    <input type="date" id="end" name="end"
                           value=<%= currentDate %>
                           min="2000-01-01" max="2050-12-31">

                    <input type="submit" value="Update" class="button" style="color:#FFFFFF"> 
                </form>
            </div>

            <div>
                <table border ="1" width="500" align="center"> 
                   <tr bgcolor="6c9ee0"> 
                        <th><b>Operation ID</b></th> 
                        <th><b>Employee ID</b></th> 
                        <th><b>Client ID</b></th> 
                        <th><b>Date</b></th> 
                        <th><b>Start Time</b></th> 
                        <th><b>End Time</b></th> 
                        <th><b>Charge</b></th> 
                        <th><b>Invoice Paid</b></th> 
                        <th><b>NHS Patient</b></th> 
                   </tr> 
                  <%-- Fetching the attributes of the request object 
                       which was previously set by the servlet --%>  
                  <%
                    try {
                        ArrayList<Operation> operationsArray = (ArrayList<Operation>)request.getAttribute("data"); 
                        for(Operation i:operationsArray){%> 
                  <%-- Arranging data in tabular form --%> 
                        <tr> 
                            <td><%=i.getOperationId()%></td> 
                            <td><%=i.getEmployeeId()%></td> 
                            <td><%=i.getClientId()%></td> 
                            <td><%=i.getDate()%></td> 
                            <td><%=i.getStartTime()%></td> 
                            <td><%=i.getEndTime()%></td> 
                            <td><%=i.getCharge()%></td> 
                            <td><%=i.getIsPaid()%></td> 
                            <td><%=i.getIsNhs()%></td> 
                        </tr> 
                      <%}
                    }
                    catch(NullPointerException e){
                    // send error
                    request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                    }
                      %> 
                  </table>  
            </div>
            <hr/> 
            Turnover for this period: ${turnover}
        </div>
    </body>
</html>
