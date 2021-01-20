<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css"/>
        <title>SmartCare - Create a New Employee</title>
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
            <h1>Create a new Employee</h1>
            <div class="center">
                <div class="card">
                    <div class="container">
                        <h2 style="text-align:center;margin-top: 10px;">Create New Employee</h2>
                        <div class="container">
                            <form action="/esd-group-3/NewEmployeeServlet" method="POST">
                                <label for="uname"><b>Username</b></label>
                                <input type="text" placeholder="Enter Username" name="uname" required>
                                <label for="psw"><b>Password</b></label>
                                <input type="password" placeholder="Enter Password" name="psw" required>
                                <label for="firstname"><b>Firstname</b></label>
                                <input type="text" placeholder="Enter firstname" name="firstname" required>
                                <label for="lastname"><b>Lastname</b></label>
                                <input type="text" placeholder="Enter lastname" name="lastname" required>
                                <label for="email"><b>Email</b></label>
                                <input type="text" placeholder="Enter email address" name="email" required>
                                <label for="address"><b>Address</b></label>
                                <input type="text" placeholder="Enter address" name="address" required>
                                <label for="type"><b>Type</b></label>
                                <select name="type">
                                    <option value="doctor">Doctor</option>
                                    <option value="nurse">Nurse</option>
                                </select>
                                <label for="isFullTime"><b>Working Hours</b></label>
                                <select name="isFullTime">
                                    <option value="TRUE">Full Time</option>
                                    <option value="FALSE">Part Time</option>
                                </select>
                                <label for="dob"><b>Date of birth</b></label>
                                <input type="date" name="dob" required>
                                <input type="submit" value="Create" class="button"> 
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
