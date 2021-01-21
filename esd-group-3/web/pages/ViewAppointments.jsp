<%-- 
    Document   : ViewAppointments
    Created on : 19-Jan-2021, 16:39:08
    Author     : morgan
--%>

<%@page import="models.Operation"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - Doctor Home</title>
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
                    <input type="submit" value="Logout" class="button logout"> 
                </form>
            </div>

            <div class="center">
                <a href="${dashboard}"><h2>SmartCare</h2></a>
            </div>
        </div>
        <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>View Appointment</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View appointment details</h2>
                    <div class="container">
                        <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                            <label for='myAppointments'>View my appointments only:</label><input type='checkbox' id='myAppointments' name='myAppointments' />
                            <label for="start">Start date:</label>
                            <input type="date" id="start" name="start"
                                    value="${todaydate}"
                                    min="${minusyear}"
                                    max="${maxdate}"
                                    >
                            <label for="start">End date:</label>
                            <input type="date" id="end" name="end"
                                   value="${onemonth}"
                                    min="${minusyear}"
                                    max="${maxdate}"
                                    >
                            <input type="submit" value="Update" class="button">
                        </form>
                        <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                            <input type="hidden" id="start" name="start" value="${todaydate}">
                            <input type="hidden" id="end" name="end" value="${todaydate}">
                            <input type="submit" value="View today's schedule" class="button">
			                  </form>
			                  <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                            <input type="hidden" id="start" name="start" value="${todaydate}">
                            <input type="hidden" id="end" name="end" value="${maxdate}">
                            <input type="submit" value="View upcoming appointments" class="button">
                        </form>
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
                </div>
            </div>
            <div class="left">
                <div class="card">
                    <div class="container">
                        <h2 style="text-align:center;margin-top: 10px;">Cancel appointment</h2>
                        <div class="container">
                            <form action="CancelAppointmentServlet" method="POST">
                                <label for="appointment"><b>Appointment Number</b></label>
                                <input type="number" name="appointment" required/>
                                <input type="submit" value="Cancel" class="button">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <div class="center">
            <div class="card users-card">
                <div class="container">
                    <div class="container">
                        <h2 style="text-align:center;margin-top: 10px;">Update Invoice</h2>
                        <div class="container">
                            <form action="/esd-group-3/UpdateInvoiceServlet" method="POST">
                                <label for="operation_id"><b>Select Invoice ID to be Updated</b></label>
                                <select name="InvoiceID" id="InvoiceID" >
                                    <%
                                    try {
                                        ArrayList<Operation> operationsArray = (ArrayList<Operation>)request.getAttribute("data");
                                        for(Operation i:operationsArray){ 
                                            int i_id = i.getOperationId(); %>
                                            <option value=<%=i_id%>><%=i_id%></option>
                                    <%  }    
                                    } 
                                    catch(NullPointerException e){
                                        // send error
                                        request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                                    }   %>
                                <label for="starttime"><b>Start Time (09:00 - 17:00)</b></label>
                                <input type="time" min="09:00" max="17:00" value="" name="starttime"/>
                                <label for="endtime"><b>End Time (09:00 - 17:00)</b></label>
                                <input type="time" min="09:00" max="17:00" value="" name="endtime"/>
                                <label for="reason"><b>Description</b></label>
                                <input type="text" name="description" value=""/>  
                                <label for="paid"><b>Has Client Paid</b></label>
                                <input name="paid" id="paid" type="checkbox"/>
                                <input type="submit" value="Update" class="button"> 
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
