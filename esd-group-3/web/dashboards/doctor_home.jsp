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
        <title>SmartCare - Doctor Home</title>
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
            <input type="submit" value="Logout" class="button logout"> 
        </form>
      </div>
      
      <div class="center">
        <h2>SmartCare</h2>
      </div>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <h1>Doctor Dashboard</h1>
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
                  <input type="text" placeholder="Enter lastname" name="lastname" required>
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
              <h2 style="text-align:center;margin-top: 10px;">Create New Prescription</h2>
              <div class="container">
                <form action="NewPrescriptionServlet" method="POST">
                  <label for="clientid"><b>Client ID</b></label>
                  <input type="text" placeholder="Enter Client ID" name="clientid" required>
                  <label for="drug_name"><b>Drug Name</b></label>
                  <input type="text" placeholder="Enter Drug Name" name="drug_name" requried>
                  <label for="dosage"><b>Dosage</b></label>
                  <input type="text" placeholder="Enter a Dosage" name="dosage" required>
                  <label for="is_repeat"><b>Is the prescription repeatable?</b></label>
                  <input type="checkbox" name="is_repeat">
                  <label for="date_start"><b>Start Date</b></label>
                  <input type="date" value=${currentdate} name="date_start" required> 
                  <label for="date_end"><b>End Date</b></label>
                  <input type="date" value=${currentdate} name="date_end" required>   
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
          <div class="card">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Add a referral to a patient</h2>
              <div class="container">
                <form action="NewReferralServlet" method="POST">
                  <label for="clientid"><b>Client ID</b></label>
                  <input type="number" name="clientid" required>
                  <br>
                  <label for="name"><b>Hospital/Ward/Surgery Name</b></label>
                  <input type="text" placeholder="Enter name of referral location" name="name" required>
                  <label for="address"><b>Address</b></label>
                  <input type="text" placeholder="Enter address" name="address" required>
                  <input type="submit" value="Add referral" class="button">
                </form>
              </div>
            </div>
          </div>
        </div>
    </div>
  </body>
</html>
