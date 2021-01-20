<%-- 
    Document   : NewAppointment
    Created on : 19-Jan-2021, 16:41:00
    Author     : morgan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - New Appointment As Employee</title>
    </head>
    <body>
        <div class="top-banner">
            <div class="dropdown">
                <button class="menu button">Menu</button>
                <div class="menu-content">
                    <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                        <input type="submit" value="Admin Page" class="button logout"> 
                    </form>
                    <a href="#">Page 2</a>
                    <a href="#">Page 3</a>
                </div>
            </div>      

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
        <h1>New Appointment</h1>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Book an appointment</h2>
              <div class="container">
                <form action="/esd-group-3/NewEmployeeAppointmentServlet" method="POST">
                  <label for="clientoptions"><b>Patient</b></label>
                  <select name="clientoptions">
                      ${clientoptions}
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
                  <input type="hidden" name="employeeid" value="${userid}"/>
                  <input type="submit" value="Create" class="button"/> 
                </form>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>
