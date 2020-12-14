/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Referrals;

/**
 *
 * @author Harrison B
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection dbcon;
        try {
            dbcon = new DBConnection("smartcaretest", "", "");
            // TODO: REMOVE
            Referrals ref = new Referrals();
            
            System.out.println("Trying to create");
            ref.create(dbcon, 1, "BRI, Eye Clinic", "Bristol centre, idk figure it out");
            System.out.println("It worked?");
            System.out.println(Arrays.toString(ref.getName()));
            System.out.println(Arrays.toString(ref.getAddress()));
           
            ref.retreiveAll(dbcon, 1);
            System.out.println("Retrieve done");
            System.out.println(Arrays.toString(ref.getName()));
            System.out.println(Arrays.toString(ref.getAddress()));
        } catch (SQLException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
