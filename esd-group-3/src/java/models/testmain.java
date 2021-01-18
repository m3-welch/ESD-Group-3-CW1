/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author morgan
 */
public class testmain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Operation op = new Operation();
        DBConnection dbcon = new DBConnection("smartcaretest", "", "");
        op.create(dbcon, 1, 3, LocalDate.now(), LocalTime.now(), LocalTime.now().plusMinutes(20), (float)0.00, true, true, "sghlgishgklhs");
    }
    
}
