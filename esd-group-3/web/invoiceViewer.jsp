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
            <form action="LogoutServlet" method="GET">
                <input type="submit" value="logout" class="button logout"> 
            </form>
            </div>

            <div class="center">
              <a href="${dashboard}"><h2>SmartCare</h2></a>
            </div>

        </div>
        <div> <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p> </div>
        <div class="center">
            <div class="card users-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Displaying All Operations</h2>
                    <div class="container">
                        <div class="content">    
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

                                    <input type="submit" value="Update" class="button"> 
                                </form>
                            </div>

                            <div>
                                <table width="500" align="center" class="prices-table-header"> 
                                   <tr> 
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
                                  <br>
                            Turnover for this period: ${turnover}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
