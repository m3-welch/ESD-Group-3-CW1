<%-- 
    Document   : cancelAppointment.jsp
    Created on : 17-Jan-2021, 18:14:55
    Author     : conranpearce
--%>

<%-- 
    Document   : signup
    Created on : 10-Dec-2020, 14:59:12
    Author     : morgan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css">
        <title>Smartcare - Cancel Appointment</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center">Smartcare - Cancel Appointment</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
              <div class="container">
                <form action="CancelAppointmentServlet" method="POST">
                  <label for="type"><b>Select Appointment to Cancel:</b></label>
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
    </body>
</html>
