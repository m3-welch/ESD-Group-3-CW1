<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
    <div class="center">
        <h2>SmartCare</h2>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Patient Dashboard</h1>
        <form action="/esd-group-3/NewAppointmentServlet" method="GET">
            <input type="submit" value="New Appointment" class="link-button"/>
        </form>
        <form action="/esd-group-3/DisplayEventsServlet" method="GET">
            <input type="submit" value="View Appointments" class="link-button"/>
        </form>
        <form action="/esd-group-3/ViewReferralsServlet" method="GET">
            <input type="submit" value="View Referrals" class="link-button"/>
        </form>
    </div>
    </body>
</html>
