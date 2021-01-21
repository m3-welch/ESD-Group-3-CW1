<%-- 
    Document   : PayInvoices
    Created on : 20-Jan-2021, 17:24:58
    Author     : Austi
--%>

<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page import="models.Operation"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/home.css">
        <title>SmartCare - Client Home</title>
    </head>
    <body>
    <div class="top-banner">
    
      <div class="dropdown">
        <button class="menu button">Menu</button>
        <div class="menu-content">
          <a href="#">Page 1</a>
          <a href="#">Page 2</a>
          <a href="#">Page 3</a>
        </div>
      </div>      
      
      <div class="logout">        
        <form action="LogoutServlet" method="GET">
            <input type="submit" value="Logout" class="button logout"> 
        </form>
      </div>
      
      <div class="center">
        <h2>SmartCare</h2>
      </div>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Client Dashboard</h1>
        <div class="left">
          <div class="card" style="width: 80rem;">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Invoice Viewer</h2>
              <div class="container">
                <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                  <label for="unpaid"><b>Unpaid Invoices Only</b></label>
                  <input name="unpaid" id="unpaid" type="checkbox">
                  <input type="submit" value="Load Invoices" class="button"> 
                </form>
              </div>
            </div>
            <div>
                <table border ="1" width="500" align="center"> 
                   <tr bgcolor="6c9ee0"> 
                        <th><b>Operation ID</b></th> 
                        <th><b>Patient Name</b></th> 
                        <th><b>Employee Name</b></th> 
                        <th><b>Date</b></th> 
                        <th><b>Start Time</b></th> 
                        <th><b>End Time</b></th> 
                        <td><b>Description</b</td> 
                        <td><b>Type</b</td> 
                        <td><b>Charge</b</td>  
                        <th><b>Invoice Paid</b></th> 
                        <th><b>NHS Patient</b></th> 
                   </tr> 
                    <%-- Fetching the attributes of the request object 
                       which was previously set by the servlet --%>  
                    <%
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
                    try {
                        ArrayList<Operation> operationsArray = (ArrayList<Operation>)request.getAttribute("data"); 
                        for(Operation i:operationsArray){%> 
                    <%-- Arranging data in tabular form --%> 
                        <tr> 
                            <td><%=i.getOperationId()%></td> 
                            <td>${names[j][1]}</td>
                            <td>${names[j][2]}</td> 
                            <td><%=i.getDate()%></td> 
                            <td><%=i.getStartTime()%></td> 
                            <td><%=i.getEndTime()%></td> 
                            <td><%=i.getDescription()%></td> 
                            <td><%=(i.getIsSurgery() ? "Surgery" : "Consultation")%></td> 
                            <td><%=(formatter.format(i.getCharge()))%></td>  
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
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Pay Invoices</h2>
              <div class="container">
                <form action="/esd-group-3/PayInvoiceServlet" method="POST">
                  <label for="operation_id"><b>Select Invoice to Pay</b></label>
                  <select name="Invoice ID" id="Invoice ID">
                        <%
                            try {
                                ArrayList<Operation> operationsArray = (ArrayList<Operation>)request.getAttribute("data");
                                for(Operation i:operationsArray){ 
                                    int i_id = i.getOperationId();
                                    boolean i_is_paid = i.getIsPaid();
                                    if(!i_is_paid) { %>
                                        <option value=<%=i_id%>><%=i_id%></option>
                        <%          }
                                } 
                            } 
                            catch(NullPointerException e){
                            // send error
                            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                            }   %>
                  <input type="submit" value="Pay" class="button"> 
                </form>
              </div>
            </div>
          </div>
        </div>
    </div>
  </body>
</html>
