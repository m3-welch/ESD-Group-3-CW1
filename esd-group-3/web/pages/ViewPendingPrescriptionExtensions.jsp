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
        <title>SmartCare - View Pending Prescription Extensions</title>
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
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>View Pending Prescription Extensions</h1>
        <div class="center">
            <div class="card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View prending prescription extensions</h2>
                    <div class="container">
                        <table class='events-table-header'>
                            <tr>
                                <th>Approval ID</th>
                                <th>Prescription ID</th>
                                <th>Patient name</th>
                                <th>Employee Name</th>
                                <th>Drug Name</th>
                                <th>Dosage</th>
                                <th>Regularity</th>
                                <th>Start Date</th>
                                <th>End Date</th>
                                <th>New End Date</th>
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
                        <h2 style="text-align:center;margin-top: 10px;">Respond to Approval Request</h2>
                        <div class="container">
                            <form action="RespondToPendingPrescriptionExtensionsServlet" method="POST">
                                <label for="approvalid"><b>Approval ID</b></label>
                                <input type="number" name="approvalid" required/>
                                <label for="response"><b>Response</b></label>
                                <select name="response">
                                    <option value="approve">Approve</option>
                                    <option value="deny">Deny</option>
                                </select>
                                <input type="submit" value="Submit Response" class="button">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
