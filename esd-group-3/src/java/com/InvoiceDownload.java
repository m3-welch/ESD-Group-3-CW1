package com;
 
import dbcon.DBConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Client;
 
public class InvoiceDownload extends HttpServlet {
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            Client client = new Client();
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            
            String user_in = request.getParameter("uname");
            
            
            client.retrieveClientByUsername(dbcon, user_in);
            
            ArrayList<String> invoice = client.getInvoice(dbcon, client.getClientId());
            
            String invoiceString = String.join("\n", invoice);
            
            ByteArrayInputStream inputStream = new ByteArrayInputStream(invoiceString.getBytes("UTF-8"));
            
            response.setHeader("Content-Disposition","attachment; filename=invoice.txt");
            try {
                int c;
                while ((c = inputStream.read()) != -1) {
                    response.getWriter().write(c);
                }
            } finally {
                if (inputStream != null) 
                    inputStream.close();
                    response.getWriter().close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InvoiceDownload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}