/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.Client;
import models.User;
import models.Employee;

/**
 *
 * @author morgan
 */
public class DBConnection {
    
    public Connection conn;    

    public DBConnection(String dbname, String dbusername, String dbpassword) throws SQLException {
        this.conn = DriverManager.getConnection(
            "jdbc:derby://localhost:1527/" + dbname
        );
        
        if (this.conn != null) {
            System.out.println("System has successfully connected to the database.");
        } else {
            System.out.println("Something went wrong while connecting to the database.");
            System.exit(0);
        }
    }
}
