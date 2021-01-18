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
        <title>SmartCare - Client Home</title>
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
        <h1>Client Dashboard</h1>
        <div class="left">
          <div class="card">
            <div class="container">
              <h2 style="text-align:center;margin-top: 10px;">List upcoming appointments</h2>
              <div class="container">
                <form action="DisplayCalendarServlet" method="POST">
                  <input type="submit" value="Create" class="button"> 
                </form>
              </div>
            </div>
          </div>
        </div>
    </div>
  </body>
</html>
