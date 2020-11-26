/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import models.User;

/**
 *
 * @author morgan
 */
public class DBConnection {
    
    Connection conn;
    public DBConnection(String dbname, String dbusername, String dbpassword) throws SQLException {
        this.conn = DriverManager.getConnection(
            "jdbc:derby://localhost:1527/" + dbname,
            dbusername,
            dbpassword
        );
        
        if (this.conn != null) {
            System.out.println("System has successfully connected to the database.");
        } else {
            System.out.println("Something went wrong while connecting to the database.");
            System.exit(0);
        }
    }
    
    public User getUser(int id) {
        String query = "SELECT * FROM Users WHERE id = " + id;

        User user = new User();

        try (Statement stmt = this.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                int userid = Integer.parseInt(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                user.setId(userid)
                    .setUsername(username)
                    .setPassword(password)
                    .setFirstname(firstname)
                    .setLastname(lastname)
                    .setEmail(email)
                    .setAddress(address);
            }

            
        } catch (SQLException e) {
            System.out.println(e);
        }

        return user;
    }
    
}
