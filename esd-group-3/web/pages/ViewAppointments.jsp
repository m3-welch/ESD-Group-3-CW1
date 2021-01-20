<%-- 
    Document   : ViewAppointments
    Created on : 19-Jan-2021, 16:39:08
    Author     : morgan
--%>

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
                        <form action="/esd-group-3/DisplayEventsServlet" method="POST">
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
                            </tr>
                        </table>
                        <div class="events-list">
                            ${eventList}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
