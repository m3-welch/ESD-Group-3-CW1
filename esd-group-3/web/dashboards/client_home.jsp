<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

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
          <form action="InvoiceViewerServlet" method="GET">
            <input type="submit" value="Admin Page" class="button logout"> 
          </form>
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
          </div>
        </div>
    </div>
  </body>
</html>