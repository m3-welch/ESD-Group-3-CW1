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
        <title>SmartCare Home</title>
    </head>
    <body>
    <div class="top-banner">
    
      <div class="dropdown">
        <button class="menu button">Menu</button>
        <div class="menu-content">
          <form action="InvoiceViewerServlet" method="GET">
            <input type="submit" value="Admin Page" class="button logout"> 
          </form>
          <form action="IssueInvoiceServlet" method="POST">
            <input type="submit" value="Add Invoice" class="button logout"> 
          </form>
          <a href="#">Page 3</a>
        </div>
      </div>      
      
      <div class="logout">        
        <form action="LogoutServlet" method="GET">
            <input type="submit" value="logout" class="button logout"> 
        </form>
      </div>
      
      <div class="center">
        <a href="${dashboard}"><h2>SmartCare</h2></a>
      </div>
    </div>
    <div class="content">
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>

    </div>
  </body>
</html>
