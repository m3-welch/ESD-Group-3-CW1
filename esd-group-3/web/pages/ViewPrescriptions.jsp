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
        <title>SmartCare - View Prescriptions</title>
    </head>
    <body>
        <<div class="top-banner">
            <div class="logout">        
                <form action="/esd-group-3/LogoutServlet" method="GET">
                    <input type="submit" value="Logout" class="button logout"> 
                </form>
            </div>

            <div class="center">
                <a href="${dashboard}"><h2>SmartCare</h2></a>
            </div>
        </div>  

        </div>
        <div class="content">
        <p style="color:${messagecolour};font-size:12px;text-align:center" id="note">${message}</p>
        <h1>View Appointment</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View prescription details</h2>
                    <div class="container">
                        <table class='events-table-header'>
                            <tr>
                                <th>Prescription ID</th>
                                <th>Patient name</th>
                                <th>Employee Name</th>
                                <th>Drug Name</th>
                                <th>Dosage</th>
                                <th>Regularity</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${prescriptionlist}
                        </div>
                    </div>
                </div>
            </div>
            <div class="left">
                <div class="card">
                    <div class="container">
                        <h2 style="text-align:center;margin-top: 10px;">Extend Prescription</h2>
                        <div class="container">
                            <form action="ViewPrescriptionsServlet" method="POST">
                                <label for="prescriptionid"><b>Prescription ID</b></label>
                                <select name="prescriptionid">
                                    ${prescriptionoptions}
                                </select>
                                <label for="newEndDate"><b>New End Date</b></label>
                                <input input="date" name="newEndDate" value="${onemonth}" min="${todaydate}" max="${oneyear}"/>
                                <input type="submit" value="Submit for Approval" class="button">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
