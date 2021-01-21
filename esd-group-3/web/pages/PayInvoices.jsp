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
      <div class="logout">        
        <form action="LogoutServlet" method="GET">
            <input type="submit" value="Logout" class="button logout"> 
        </form>
      </div>
      
      <div class="center">
          <a href="${dashboard}"><h2>SmartCare</h2></a>
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
                </div>  </table>  
            </div>
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Pay Invoices</h2>
              <div class="container">
                <form action="/esd-group-3/PayInvoiceServlet" method="POST">
                  <label for="operation_id"><b>Select Invoice to Pay</b></label>
                  <select name="Invoice ID" id="Invoice ID" required>
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
