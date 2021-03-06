<%-- 
    Document   : NewReferral
    Created on : 19-Jan-2021, 16:37:30
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
        <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>New Referral</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Add a referral to a patient</h2>
                    <div class="container">
                        <form action="/esd-group-3/NewReferralServlet" method="POST">
                            <label for="clientoptions"><b>Patient</b></label>
                            <select name="clientoptions">
                                ${clientoptions}
                            </select>
                            <label for="name"><b>Hospital/Ward/Surgery Name</b></label>
                            <input type="text" placeholder="Enter name of referral location" name="name" required>
                            <label for="address"><b>Address</b></label>
                            <input type="text" placeholder="Enter address" name="address" required>
                            <input type="hidden" name="employee_userid" value="${userid}"/>
                            <input type="submit" value="Add referral" class="button">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
