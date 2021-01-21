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
                <div class="logout">        
                    <form action="/esd-group-3/LogoutServlet" method="GET">
                        <input type="submit" value="Logout" class="button logout"> 
                    </form>
                </div>

                <div class="center">
                    <a href="${dashboard}"><h2>SmartCare</h2></a>
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
        <h1>View Users</h1>
        <div class="center">
            <div class="card users-card">
                <div class="container">
                    <h2 style="text-align:center;margin-top: 10px;">View Users</h2>
                    <div class="container">
                        <table class='prices-table-header'>
        
                            <tr>
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
                                        <form action="PricesChangerServlet" method="POST">

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
                                            <input type="submit" name="select" value="Save" class="button">
                                            <input type="submit" name="select" value="Delete" class="button">
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
                                        <form action="PricesChangerServlet" method="POST">
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
                                            <input type="submit" name="select" value="Add" class="button"> 
                                        </td>
                                        </form>
                                    </tr>
                           
                         
                
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
