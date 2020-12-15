/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author morgan
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String role;
    
    public User setId(int id) {
        this.id = id;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public void retrieveByUsername(DBConnection dbcon, String uname) {
        String query = "SELECT * FROM Users WHERE username = '" + uname + "'";

        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setId(Integer.parseInt(resultSet.getString("id")));
                this.setUsername(resultSet.getString("username"));
                this.setPassword(resultSet.getString("password"));
                this.setFirstname(resultSet.getString("firstname"));
                this.setLastname(resultSet.getString("lastname"));
                this.setEmail(resultSet.getString("email"));
                this.setAddress(resultSet.getString("address"));
                this.setRole(resultSet.getString("role"));

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void retrieveByUserId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Users WHERE id = '" + id + "'";

        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setId(Integer.parseInt(resultSet.getString("id")));
                this.setUsername(resultSet.getString("username"));
                this.setPassword(resultSet.getString("password"));
                this.setFirstname(resultSet.getString("firstname"));
                this.setLastname(resultSet.getString("lastname"));
                this.setEmail(resultSet.getString("email"));
                this.setAddress(resultSet.getString("address"));
                this.setRole(resultSet.getString("role"));

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void changePrice(DBConnection dbcon, String appointmentType, 
            String employeeType, String price) {
        String query = "UPDATE PRICES SET priceperslot = " + price +
                " WHERE appointmenttype = '" + appointmentType + 
                "' AND employeetype = '" + employeeType + "'"; //could be issue ;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Price changed");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void addPrice(DBConnection dbcon, String appointmentType,
            String employeeType, String price) {
        String query = "INSERT INTO PRICES (appointmenttype, employeetype,"
                + " priceperslot) VALUES ('" + appointmentType + "', '" +
                employeeType + "', " + price + ")";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Price added");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void removePrice(DBConnection dbcon, String appointmentType, 
            String employeeType, String price) {
        String query = "DELETE FROM PRICES WHERE priceperslot = " + price +
                " AND appointmenttype = '" + appointmentType + 
                "' AND employeetype = '" + employeeType + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Price removed");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
