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
    private Events events;
    
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
    
    public void setEvents(DBConnection dbcon) {
        Events ev = new Events();
        ev.getOperationsFromDB(dbcon, this.id);
        this.events = ev;
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
        String query = "SELECT * FROM Users WHERE id = " + id;

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
    
    public void editUser(DBConnection dbcon, String setDatabase, String username, String toChange, String updatedValue) {            
        String query = "";
        
        if ((toChange != "userid") || (toChange != "id")) {
        
            if (setDatabase == "Clients" || setDatabase == "Employees") {
                query = "SELECT id FROM Users WHERE username = '" + username + "'";

                int userid = 0;
                try (Statement stmt = dbcon.conn.createStatement()) {
                    ResultSet resultSet = stmt.executeQuery(query);
                    while (resultSet.next()) {
                        userid = resultSet.getInt("id");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }

                query = "UPDATE " + setDatabase + " SET " + toChange + "='" + updatedValue +"' WHERE userid =" + userid;

                try (Statement stmt = dbcon.conn.createStatement()) {
                    int resultSet = stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                }

            } else if (setDatabase == "Users") {
                                
                query = "UPDATE Users SET " + toChange + "='"+ updatedValue + "' WHERE username ='" + username + "'";

                try (Statement stmt = dbcon.conn.createStatement()) {
                    int resultSet = stmt.executeUpdate(query);
                } catch (SQLException e) {
                    System.out.println(e);
                }
                
            }
        }
    }
    
    public void dropUser(DBConnection dbcon, String username) {
        String query = "";
           
        // Get the user's id depending on the username
        query = "SELECT id FROM Users WHERE username = '" + username + "'";
        int userid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Delete the user out of the employees or clients table
        query = "DELETE FROM Clients WHERE userid=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "DELETE FROM Employees WHERE userid=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Delete from users table
        query = "DELETE FROM Users WHERE id=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void dropUserById(DBConnection dbcon, int userid) {
        String query = "";
        query = "DELETE FROM Users WHERE id=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            System.out.println("-3");
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Delete the user out of the employees or clients table
        query = "DELETE FROM Clients WHERE userid=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            System.out.println("-1");
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "DELETE FROM Employees WHERE userid=" + userid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            System.out.println("-2");
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Delete from users table
        
    }
}
