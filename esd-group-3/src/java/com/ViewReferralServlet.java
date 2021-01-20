/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Client;
import models.Events;
import models.Operation;
import models.Referrals;

/**
 *
 * @author conranpearce
 */
@WebServlet(name = "ViewReferralServlet", urlPatterns = {"/ViewReferralServlet"})
public class ViewReferralServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        HttpSession loginSession = request.getSession(false);
        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);
        
        int userid = (Integer)loginSession.getAttribute("userID");
                
        int clientid = 0;
        DBConnection dbcon = null;

        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            clientid = new Client().retrieveClientByUserId(dbcon, userid).getClientId();
        } catch (SQLException ex) {
            Logger.getLogger(ViewReferralServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<String> referralsList = new ArrayList<String>();

         try {
            dbcon = new DBConnection("smartcaretest", "", "");
            Referrals referral = new Referrals();
            referral.retreiveAll(dbcon, clientid);
            
            referralsList = referral.getReferralsFromClientId(dbcon, clientid);
            
            if (referralsList.size() > 0) {
             
                String outputList = "<table>";
                for (int i = 0; i < referralsList.size(); i++) {
                    String[] referralParts = referralsList.get(i).split("//");
                    outputList += "<tr><td>" + referralParts[0] + "</td><td>" 
                           + referralParts[1] + "</td><td>" +  
                           referralParts[2] + "</td><tr>";
                }
                outputList += "</table>";

                request.setAttribute("message", "Referrals Loaded Successfully");   
                request.setAttribute("referralList", outputList);
            } else {
                request.setAttribute("message", "Referalls Could Not Be Loaded. No Referrals To Load.");   
            }
            
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
                        
        } catch (SQLException ex) {
            Logger.getLogger(ViewReferralServlet.class.getName()).log(Level.SEVERE, null, ex);
            // send error
            request.setAttribute("message", "Error - SQL Exception"); // Will be available as ${message}
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }   
}
