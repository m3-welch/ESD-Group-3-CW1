<%-- 
    Document   : ViewPatients
    Created on : 19-Jan-2021, 16:35:22
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
        </div>
        <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>View Patients</h1>
        <div class="center">
            <div class="card patients-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View Patients</h2>
                    <div class="container">
                        <table class='users-table-header'>
                            <tr>
                                <th>Patient ID</th>
                                <th>Patient Name</th>
                                <th>Patient Type</th>
                                <th>Patient Email</th>
                                <th>Patient Address</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${userlist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
