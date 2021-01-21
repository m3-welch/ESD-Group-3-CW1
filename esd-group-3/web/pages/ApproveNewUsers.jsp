<%-- 
    Document   : ApproveNewUsers
    Created on : 20-Jan-2021, 22:36:33
    Author     : Harrison B
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - Approve New Users</title>
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
        <h1>Approve New Users</h1>
        <div class="center">
            <div class="card users-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Clients</h2>
                    <div class="container">
                        <table class='patients-table-header'>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Address</th>
                                <th>Date of Birth</th>
                                <th>Is NHS?</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${clientList}
                        </div>
                    </div>
                </div>
            </div>
            <div class="card users-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Employees</h2>
                    <div class="container">
                        <table class='patients-table-header'>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Address</th>
                                <th>Role</th>
                                <th>Date of Birth</th>
                                <th>Work pattern</th>
                            </tr>
                        </table>
                        <div class="list">
                            ${employeeList}
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="left">
            <div class="card users-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">Respond to Approval Request</h2>
                    <div class="container">
                        <form action="RespondToPendingUsersServlet" method="POST">
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
    </body>
</html>
