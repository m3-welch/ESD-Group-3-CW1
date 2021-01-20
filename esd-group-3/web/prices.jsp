<%-- 
    Document   : changePrices
    Created on : 10-Jan-2021, 11:06:44
    Author     : Sam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Price"%> 
<%@page import="java.util.ArrayList"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="/esd-group-3/styles/home.css">
        <title>SmartCare - Change Price</title>
    </head>
    <body>
        <div class="top-banner">

            <div class="dropdown">
                <button class="menu button">Menu</button>
                <div class="menu-content">
                    <form action="/esd-group-3/InvoiceViewerServlet" method="GET">
                        <input type="submit" value="Admin Page" class="button logout"> 
                    </form>
                    <a href="#">Page 2</a>
                    <a href="#">Page 3</a>
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
            <h1>Displaying All Prices</h1> 
            <table border ="1" width="750" align="center"> 
                <tr bgcolor="00FF7F">
                    <th><b>Appointment Type</b></th>
                    <th><b>Employee Type</b></th>
                    <th><b>Price per Hour</b></th>
                    <th></th> <!-- Empty as contains buttons -->
                </tr> 
                <%-- Fetching the attributes of the request object 
                     which was previously set by the servlet --%>  
                <%
                try {
                    ArrayList<Price> pricesArray = (ArrayList<Price>)request.getAttribute("data"); 
                    for(Price i:pricesArray){%> 
                        <%-- Arranging data in tabular form --%> 
                        <tr>
                            <form action="PricesChanger" method="POST">

                            <input type="hidden" name="idValue" value="<%=i.getID()%>" readonly>
                            
                            <td>
                                <input type="text" name="apptType" value="<%=i.getAppointmentType()%>">
                            </td>
                            <td>
                                <input type="text" name="empType" value="<%=i.getEmployeeType()%>">
                            </td>                         
                            <td>
                                <input type="text" name="priceValue" value="<%=i.getPricePerSlot()%>">
                            </td>
                            <td>                            
                                <input type="submit" name="select" value="Save">
                                <input type="submit" name="select" value="Delete">
                            </td>
                            </form>
                        </tr>
                        
                    <%
                        
                    }
                }
                catch(NullPointerException e){
                // send error
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                }%>
                        <tr>
                            <form action="PricesChanger" method="POST">
                            <td>
                                <input type="text" name="newApptType" placeholder="Appointment Type" required>
                            </td>
                            <td>
                                <input type="text" name="newEmpType" placeholder="Employee Type" required>
                            </td>                         
                            <td>
                                <input type="text" name="newPriceValue" placeholder="Price per Hour" required>
                            </td>
                            <td>                            
                                <input type="submit" name="select" value="Add"> 
                            </td>
                            </form>
                        </tr>
                           
                         
                
          </table>
        </div>
    </body>
</html>
