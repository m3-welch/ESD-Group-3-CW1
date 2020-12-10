<%-- 
    Document   : admin
    Created on : 10-Dec-2020, 15:11:56
    Author     : Austi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${message}</p>
        <div class="invoiceViewer">        
        <form action="InvoiceViewerServlet" method="POST">
            <input type="submit" value="invoiceViewer" class="button invoiceViewer"> 
        </form>
        </div>
        <p style="color:#FF3232;font-size:12px;text-align:center" id="note">${data}</p>
    </body>
</html>
