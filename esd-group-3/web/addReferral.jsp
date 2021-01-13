<%-- 
    Document   : addReferral
    Created on : 13-Jan-2021, 12:14:31
    Author     : Harrison B
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/home.css">
        <title>Smartcare - Add Referral</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center">Smartcare - Add Referral</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
              <div class="container">
                <form action="NewReferralServlet" method="POST">
                  <label for="clientid"><b>Client ID</b></label>
                  <input type="number" placeholder="Enter client ID" name="clientid" required>
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
    </body>
</html>
