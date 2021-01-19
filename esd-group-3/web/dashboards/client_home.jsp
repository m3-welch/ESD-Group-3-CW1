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
        <link rel="stylesheet" href="styles/home.css">
        <title>SmartCare - Client Home</title>
        <%
            HttpSession loginSession = request.getSession();
            String doctornurseoptions = loginSession.getAttribute("doctornurseoptions").toString();
            String todaydate = loginSession.getAttribute("todaydate").toString();
            String maxdate = loginSession.getAttribute("maxdate").toString();
            String nowtime = loginSession.getAttribute("nowtime").toString();
            String tenmins = loginSession.getAttribute("tenmins").toString();
            String userid = loginSession.getAttribute("userid").toString();
        %>
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
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Book an appointment</h2>
                    <div class="container">
                        <form action="NewAppointmentServlet" method="POST">
                            <label for="doctor-nurse"><b>Staff</b></label>
                            <select name="doctor-nurse">
                                ${doctornurseoptions}
                            </select>
                            <label for="type"><b>Type</b></label>
                            <select name="type" required>
                                <option value="surgery">Surgery</option>
                                <option value="other">Other</option>
                            </select>
                            <label for="date"><b>Date (Mon - Fri)</b></label>
                            <input type="date" value="${todaydate}" max="${maxdate}" name="date" required/>
                            <label for="starttime"><b>Start Time (09:00 - 17:00)</b></label>
                            <input type="time" min="09:00" max="17:00" value="${nowtime}" name="starttime" required/>
                            <label for="endtime"><b>End Time (09:00 - 17:00)</b></label>
                            <input type="time" min="09:00" max="17:00" value="${tenmins}" name="endtime" required/>
                            <label for="reason"><b>Reason</b></label>
                            <input type="text" name="reason" required/>
                            <input type="hidden" name="clientid" value="${userid}"/>
                            <input type="submit" value="Create" class="button"/> 
                        </form>
                    </div>
                </div>
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">List employee appointments</h2>
                    <div class="container">
                        <form action="ViewEmployeeAppointments" method="POST">
                            <input ${checkeddoctor} type="radio" value="doctor" id="doctor" name="filter" class="patientlist-filter"/>
                            <label for="doctor">Doctors</label>
                            <input ${checkednurse} type="radio" value="nurse" id="nurse" name="filter" class="patientlist-filter"/>
                            <label for="nurse">Nurses</label>
                            <input ${checkedcombined} type="radio" value="all" id="combined" name="filter" class="patientlist-filter"/>
                            <label for="combined">Combined</label>
                            <label for="start">Start date</label>
                            <input type="date" value="${todaydate}" max="${maxdate}" name="start" required/>
                            <label for="end">End date</label>             
                            <input type="date" value="${todaydate}" max="${maxdate}" name="end" required/>
                            <input type="submit" value="Display" class="update-button"/>
                        </form>
                        <table class='patients-table-header'>
                            <tr>
                                <th>Date</th>
                                <th>Start time</th>
                                <th>End time</th>
                                <th>Employee Name</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${appointmentlist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="left">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View appointment details</h2>
                    <div class="container">
                        <form action="DisplayEventsServlet" method="POST">
                            <label for="start">Start date:</label>
                            <input type="date" id="start" name="start"
                                    value="<%= LocalDate.now().toString() %>"
                                    min="<%= LocalDate.now().minusYears(1).toString() %>"
                                    max="<%= LocalDate.now().plusYears(1).toString() %>"
                                    >
                            <label for="start">End date:</label>
                            <input type="date" id="end" name="end"
                                   value="<%= LocalDate.now().plusMonths(1).toString() %>"
                                    min="<%= LocalDate.now().minusYears(1).toString() %>"
                                    max="<%= LocalDate.now().plusYears(1).toString() %>"
                                    >
                            <input type="submit" value="Update" class="button">
                        </form>
                        <form action="DisplayEventsServlet" method="POST">
                            <input type="hidden" name="start" value="<%= LocalDate.now().toString() %>">
                            <input type="hidden" name="end" value="<%= LocalDate.now().plusYears(1).toString() %>">
                            <input type="submit" value="List upcoming appointments" class="button">
                        </form>
                        <table class='patients-table-header'>
                            <tr>
                                <th>Appointment Number</th>
                                <th>Date</th>
                                <th>Patient name</th>
                                <th>Employee Name</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${eventList}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="left">
			<div class="card" style="width: 50rem;">
				<div class="container">
					<h2 style="text-align:center;margin-top: 10px;">Invoice Viewer</h2>
					<div class="container">
						<form action="ClientInvoiceViewerServlet" method="GET">
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
				<div class="container">
					<h2 style="text-align:center;margin-top: 10px;">Pay Invoices</h2>
					<div class="container">
						<form action="PayInvoiceServlet" method="POST">
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