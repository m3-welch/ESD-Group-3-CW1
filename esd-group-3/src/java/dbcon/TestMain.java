/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcon;

import java.sql.SQLException;
import models.User;

/**
 *
 * @author morgan
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        DBConnection dbcon = new DBConnection("smartcare", "morgan", "password");
        User user = dbcon.getUserById(0);
        System.out.println(user);
    }
    
}
