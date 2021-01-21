<%-- 
    Document   : newjsp
    Created on : 20-Jan-2021, 18:03:41
    Author     : conranpearce
--%>

<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SmartCare - Create a new Prescription</title>
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
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
        <p style="color:${messagecolour};font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Create Prescription</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Create New Prescription</h2>
                    <div class="container">
                        <form action="CreatePrescriptionServlet" method="POST">
                            <label for="clientoptions"><b>Patient</b></label>
                            <select name="clientoptions">
                                ${clientoptions}
                            </select>
                            <label for="drug_name"><b>Drug Name</b></label>
                            <input type="text" placeholder="Enter Drug Name" name="drug_name" requried>
                            <label for="dosage"><b>Dosage</b></label>
                            <input type="text" placeholder="Enter a Dosage" name="dosage" required>
                            <label for="is_repeat"><b>Is the prescription repeatable?</b></label>
                            <input type="checkbox" name="is_repeat">
                            <label for="date_start"><b>Start Date</b></label>
                            <input type="date" id="date_start" name="date_start"
                                              value="<%= LocalDate.now().toString() %>"
                                              min="<%= LocalDate.now().minusYears(1).toString() %>"
                                              max="<%= LocalDate.now().plusYears(1).toString() %>"
                                              >                  
                            <label for="date_end"><b>End Date</b></label>
                            <input type="date" id="date_end" name="date_end"
                                   value="<%= LocalDate.now().plusYears(1).toString() %>"
                                              min="<%= LocalDate.now().minusYears(1).toString() %>"
                                              max="<%= LocalDate.now().plusYears(1).toString() %>"
                                              >                   
                            <input type="submit" value="Create" class="button"> 
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
