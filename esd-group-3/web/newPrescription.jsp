<%-- 
    Document   : newPrescription
    Created on : 14-Jan-2021, 15:21:19
    Author     : conranpearce
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css">
        <title>Smartcare - New Prescription</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">Create New Prescription</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
              <div class="container">
                <form action="NewPrescriptionServlet" method="POST">
                  <label for="clientid"><b>Client ID</b></label>
                  <input type="text" placeholder="Enter Client ID" name="clientid" required>
                  <label for="drug_name"><b>Drug Name</b></label>
                  <input type="text" placeholder="Enter Drug Name" name="drug_name" requried>
                  <label for="dosage"><b>Dosage</b></label>
                  <input type="text" placeholder="Enter a Dosage" name="dosage" required>
                  <label for="is_repeat"><b>Is the Dosage Repeated?</b></label>
                  <input type="checkbox" name="is_repeat">
                  <label for="date_start"><b>Start Date</b></label>
                  <input type="date" value=${currentdate} name="date_start" required> 
                  <label for="date_end"><b>End Date</b></label>
                  <input type="date" value=${currentdate} name="date_end" required>   
                  <input type="hidden" value=${employeeid} name="employeeid">   
                  <input type="submit" value="Create" class="button"> 
                </form>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>
