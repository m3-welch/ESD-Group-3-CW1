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
        <title>SmartCare - View Referrals</title>
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
                <a href="${dashboard}"><h2>SmartCare</h2></a>
            </div>
        </div>
        <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>View Referrals</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View referral details</h2>
                    <div class="container">
                        <table class='patients-table-header'>
                            <tr>
                                <th>Patient Name</th>
                                <th>Staff Name</th>
                                <th>Referral Name</th>
                                <th>Referral Address</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${referrallist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
