package com;
 
import dbcon.DBConnection;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;
 
public class InvoiceDownloadServlet extends HttpServlet {
 
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession loginSession = request.getSession(false);
        request.setAttribute("dashboard", "/esd-group-3/dashboards/" + loginSession.getAttribute("user_role") + "_home.jsp");
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
            Logger.getLogger(InvoiceDownloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}