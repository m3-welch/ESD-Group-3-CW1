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
        <h1>View Patients</h1>
        <div class="center">
            <div class="card patients-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View Patients</h2>
                    <div class="container">
                        <form class="patientlist-filteroptions" action="/esd-group-3/ViewPatientsServlet" method="POST">
                            <input ${checkednhs} type="radio" value="NHS" id="nhs" name="filter" class="patientlist-filter"/>
                            <label for="nhs">NHS</label>
                            <input ${checkedprivate} type="radio" value="private" id="private" name="filter" class="patientlist-filter"/>
                            <label for="private">Private</label>
                            <input ${checkedcombined} type="radio" value="all" id="combined" name="filter" class="patientlist-filter"/>
                            <label for="combined">Combined</label>                                
                            <input type="submit" value="Update" class="update-button"/>
                        </form>
                        <table class='patients-table-header'>
                            <tr>
                                <th>Patient ID</th>
                                <th>Patient Name</th>
                                <th>Patient Type</th>
                                <th>Patient Email</th>
                                <th>Patient Address</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${patientlist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
