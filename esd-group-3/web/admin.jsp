<%-- 
    Document   : admin
    Created on : 10-Dec-2020, 15:11:56
    Author     : Austin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Operation"%> 
<%@page import="java.util.ArrayList"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrator Page</title>
    </head>
    <body>
        <div>    
            <select name="Filters" id="filter">
                <option value="all">All Invoices</option>
                <option value="nhs">NHS Patient Invoices</option>
                <option value="private">Private Patient Invoices</option>
            </select>
            
            <label for="start">Start date:</label>
            <input type="date" id="start" name="start"
                   value="2018-07-22"
                   min="2018-01-01" max="2021-12-31">
            <label for="start">End date:</label>
            <input type="date" id="end" name="end"
                   value="2018-07-22"
                   min="2018-01-01" max="2021-12-31">
        </div>
        <h1>Displaying All Operations</h1> 
        <table border ="1" width="500" align="center"> 
           <tr bgcolor="00FF7F"> 
            <th><b>Operation ID</b></th> 
            <th><b>Employee ID</b></th> 
            <th><b>Client ID</b></th> 
            <th><b>Date</b></th> 
            <th><b>Start Time</b></th> 
            <th><b>End Time</b></th> 
            <th><b>Charge</b></th> 
            <th><b>Slot</b></th> 
            <th><b>Invoice Paid</b></th> 
            <th><b>NHS Patient</b></th> 
           </tr> 
          <%-- Fetching the attributes of the request object 
               which was previously set by the servlet --%>  
          <%
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
                  <td><%=i.getSlot()%></td> 
                  <td><%=i.getIsPaid()%></td> 
                  <td><%=i.getIsNhs()%></td> 
              </tr> 
              <%}%> 
          </table>  
        <hr/> 
    </body>
</html>
