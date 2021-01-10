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
        <title>Change Price</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div>
            <h1>Displaying All Operations</h1> 
            <table border ="1" width="500" align="center"> 
                <tr bgcolor="00FF7F">
                    <th><b>Appointment Type</b></th>
                    <th><b>Employee Type</b></th> 
                    <th><b>Price per Hour</b></th> 
                </tr> 
                <%-- Fetching the attributes of the request object 
                     which was previously set by the servlet --%>  
                <%
                try {
                    ArrayList<Price> pricesArray = (ArrayList<Price>)request.getAttribute("data"); 
                    for(Price i:pricesArray){%> 
                        <%-- Arranging data in tabular form --%> 
                        <tr> 
                            <td><%=i.getEmployeeType()%></td> 
                            <td><%=i.getAppointmentType()%></td> 
                            <td><%=i.getPricePerSlot()%></td>            
                        </tr> 
                <%}
                }
                catch(NullPointerException e){
                // send error
                request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
                }%> 
          </table>
        </div>
    </body>
</html>
