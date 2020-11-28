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
    
    public User getUserById(int id) {
        String query = "SELECT * FROM Users WHERE id = " + id;

        User user = new User();

        try (Statement stmt = this.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int userid = Integer.parseInt(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                user.setId(userid);
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);
                user.setAddress(address);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        
        user = this.getUserRole(user);

        return user;
    }
    
    public User getUserRole(User user) {
        String query = "SELECT role FROM Roles WHERE id = (SELECT roleid FROM UserRoles WHERE userid = " + Integer.toString(user.getId()) + ")";
        
        try (Statement stmt = this.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                user.setRole(role);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return user;
    }
    
    public Client getClientByUserId(int id) {
        String query = "SELECT * FROM Clients WHERE userid = " + id;
        
        Client client = new Client();
        
        try (Statement stmt = this.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int clientid = resultSet.getInt("id");
                String type = resultSet.getString("type");
                client.setClientId(clientid);
                client.setType(type);
                client.setId(id);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = this.getUserById(id);
        
        client.setUsername(user.getUsername());
        client.setPassword(user.getPassword());
        client.setFirstname(user.getFirstname());
        client.setLastname(user.getLastname());
        client.setEmail(user.getEmail());
        client.setAddress(user.getAddress());
        client.setRole(user.getRole());
        
        return client;
    }
    
    public Employee getEmployeeByUserId(int id) {
        String query = "SELECT * FROM Employees WHERE userid = " + id;
        
        Employee employee = new Employee();
        
        try (Statement stmt = this.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int clientid = resultSet.getInt("id");
                employee.setEmployeeId(clientid);
                employee.setId(id);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = this.getUserById(id);
        
        employee.setUsername(user.getUsername());
        employee.setPassword(user.getPassword());
        employee.setFirstname(user.getFirstname());
        employee.setLastname(user.getLastname());
        employee.setEmail(user.getEmail());
        employee.setAddress(user.getAddress());
        employee.setRole(user.getRole());
        
        return employee;
    }
}
