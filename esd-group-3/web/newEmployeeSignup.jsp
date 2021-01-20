<%-- 
    Document   : newSignupEmployee
    Created on : 20-Jan-2021, 21:09:07
    Author     : Harrison B
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css">
        <title>Smartcare - New Employee</title>
    </head>
    <body>
        <div class="center">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center">Smartcare - New Employee</h2>
              <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
              <div class="container">
                <form action="EmployeeSignupServlet" method="POST">
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
                      <option value="fulltime">Full time</option>
                      <option value="parttime">Part time</option>
                  </select>
                  <label for="type"><b>Role</b></label>
                  <select name="role">
                      <option value="fulltime">doctor</option>
                      <option value="parttime">nurse</option>
                      <option value="parttime">admin</option>
                  </select>
                  <label for="dob"><b>Date of Birth</b></label>
                  <input type="date" name="dob" required>
                  <input type="submit" value="signup" class="button"> 
                </form>
              </div>
            </div>
          </div>
        </div>
    </body>
</html>
