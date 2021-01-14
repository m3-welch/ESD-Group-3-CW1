<%-- 
    Document   : home
    Created on : 02-Dec-2020, 14:23:38
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/home.css">
        <title>SmartCare - Admin Home</title>
    </head>
    <body>
    <div class="top-banner">
    
      <div class="dropdown">
        <button class="menu button">Menu</button>
        <div class="menu-content">
          <form action="InvoiceViewerServlet" method="GET">
            <input type="submit" value="Admin Page" class="button logout"> 
          </form>
          <a href="#">Page 2</a>
          <a href="#">Page 3</a>
        </div>
      </div>      
      
      <div class="logout">        
        <form action="LogoutServlet" method="GET">
            <input type="submit" value="logout" class="button logout"> 
        </form>
      </div>
      
      <div class="center">
        <h2>SmartCare</h2>
      </div>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Admin Dashboard</h1>
        <div class="left">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Create New Patient</h2>
              <div class="container">
                <form action="NewUserServlet" method="POST">
                  <label for="uname"><b>Username</b></label>
                  <input type="text" placeholder="Enter Username" name="uname" required>
                  <label for="psw"><b>Password</b></label>
                  <input type="password" placeholder="Enter Password" name="psw" required>
                  <label for="firstname"><b>Firstname</b></label>
                  <input type="text" placeholder="Enter firstname" name="firstname" required>
                  <label for="lastname"><b>Lastname</b></label>
                  <input type="text" placeholder="Enter lastname" name="lastaname" required>
                  <label for="email"><b>Email</b></label>
                  <input type="text" placeholder="Enter email address" name="email" required>
                  <label for="address"><b>Address</b></label>
                  <input type="text" placeholder="Enter address" name="address" required>
                  <label for="type"><b>Type</b></label>
                  <select name="type">
                      <option value="NHS">NHS</option>
                      <option value="private">Private</option
                  </select>
                  <input type="submit" value="Create" class="button"> 
                </form>
              </div>
            </div>
          </div>
        </div>
    <div class="left">
      <div class="card">
        <div class="container">
          <h2 style="text-align:center;margin-top: 10px;">Create New Employee</h2>
          <div class="container">
            <form action="NewEmployeeServlet" method="POST">
              <label for="uname"><b>Username</b></label>
              <input type="text" placeholder="Enter Username" name="uname" required>
              <label for="psw"><b>Password</b></label>
              <input type="password" placeholder="Enter Password" name="psw" required>
              <label for="firstname"><b>Firstname</b></label>
              <input type="text" placeholder="Enter firstname" name="firstname" required>
              <label for="lastname"><b>Lastname</b></label>
              <input type="text" placeholder="Enter lastname" name="lastaname" required>
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
              <input type="submit" value="Create" class="button"> 
            </form>
          </div>
        </div>
      </div>
    </div>
        <div class="left">
            <div class="card patients-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View Patients</h2>
                    <div class="container">
                        <form class="patientlist-filteroptions" action="ViewPatientsServlet" method="POST">
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
                            </tr>
                        </table>
                        <div class="list">
                            ${patientlist}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  </body>
</html>
