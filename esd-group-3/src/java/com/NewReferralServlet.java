/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import dbcon.DBConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Referrals;

/**
 *
 * @author Harrison B
 */
public class NewReferralServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
                            throws ServletException, IOException {
        response.setContentType("text/html");
        
        HttpSession loginSession = request.getSession();

        request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).include(request, response);

        //decare vars
        int clientid = Integer.parseInt(request.getParameter("clientid"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        Referrals ref = new Referrals();
        int count = 0;

        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            ref.retreiveAll(dbcon, clientid);
            count = ref.getNameArr().length;
            ref.create(dbcon, clientid, name, address);
        } catch (SQLException ex) {
            Logger.getLogger(SignupServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (ref.getNameArr().length > count) {
            request.setAttribute("message", "New Referral added to client");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        } else {
            request.setAttribute("message", "Error! - Referral failed to add");
            request.getRequestDispatcher((String)loginSession.getAttribute("dashboard")).forward(request,response);
            response.sendRedirect((String)loginSession.getAttribute("dashboard"));
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Adds referral to db through calls to class in models package";
    }

}
