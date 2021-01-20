<%-- 
    Document   : ViewEmployees
    Created on : 20-Jan-2021, 11:53:07
    Author     : Harrison B
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
        <h1>View Employees</h1>
        <div class="center">
            <div class="card patients-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View Employees</h2>
                    <div class="container">
                        <form class="patientlist-filteroptions" action="/esd-group-3/ViewEmployeesServlet" method="POST">
                            <input ${checkeddoctor} type="radio" value="doctor" id="doctor" name="filter" class="patientlist-filter"/>
                            <label for="doctor">Doctor</label>
                            <input ${checkednurse} type="radio" value="nurse" id="nurse" name="filter" class="patientlist-filter"/>
                            <label for="nurse">Nurse</label>
                            <input ${checkedcombined} type="radio" value="all" id="combined" name="filter" class="patientlist-filter"/>
                            <label for="combined">Combined</label>                                
                            <input type="submit" value="Update" class="update-button"/>
                        </form>
                        <table class='patients-table-header'>
                            <tr>
                                <th>Employee ID</th>
                                <th>Employee Name</th>
                                <th>Employee Role</th>
                                <th>Employee Email</th>
                                <th>Employee Address</th>
                                <th>Employee Date of Birth</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${employeelist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
