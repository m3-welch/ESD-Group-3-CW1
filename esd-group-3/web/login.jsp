<%-- 
    Document   : login
    Created on : 02-Dec-2020, 14:08:37
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css">
        <title>SmartCare Login</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center">SmartCare Login</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
              <div class="container">
                <form action="LoginServlet" method="POST">
                  <label for="uname"><b>Username</b></label>
                  <input type="text" placeholder="Enter Username" name="uname" required>
                  <label for="psw"><b>Password</b></label>
                  <input type="password" placeholder="Enter Password" name="psw" required>
                  <input type="submit" value="login" class="button"> 
                </form>
                
                <a href="/esd-group-3/signup.jsp" class="button">Signup</a>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>
