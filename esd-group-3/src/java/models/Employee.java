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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morgan
 */
public class Employee extends User {
    private int employeeId;
    private Boolean isFullTime;
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getEmployeeId() {
        return this.employeeId;
    }
    
    public void setFullTime(Boolean isFullTime) {
        this.isFullTime = isFullTime;
    }
    
    public Boolean isFullTime() {
        return this.isFullTime;
    }
    
    public void create(
        DBConnection dbcon,
        String username,
        String password,
        String firstname,
        String lastname,
        String email,
        String address,
        String role,
        String isFullTime,
        LocalDate dob
    ) {
        String query = "INSERT INTO Users (username, password, firstname, lastname,"
            + "email, address, role, dob) VALUES ('" + username + "', '" 
            + password + "', '" + firstname + "', '" + lastname + "', '" + email 
            + "', '" + address + "', '" + role + "', '" + dob + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        query = "INSERT INTO Employees (userid, isfulltime) VALUES (" + userid + ", " + isFullTime + ")";
         
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "SELECT id FROM Employees WHERE username = '" + username + "'";
        
        int employeeid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        this.setId(userid);
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setAddress(address);
        this.setRole(role);
        this.setEmployeeId(employeeid);
        this.setDob(dob);
    }
    
    public Employee retrieveEmployeeByUserId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Employees WHERE userid = " + id;
        
        Employee employee = new Employee();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int employeeid = resultSet.getInt("id");
                Boolean is_fulltime = resultSet.getBoolean("isFullTime");
                employee.setEmployeeId(employeeid);
                employee.setId(id);
                employee.setFullTime(is_fulltime);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, id);
        
        employee.setUsername(user.getUsername());
        employee.setPassword(user.getPassword());
        employee.setFirstname(user.getFirstname());
        employee.setLastname(user.getLastname());
        employee.setEmail(user.getEmail());
        employee.setAddress(user.getAddress());
        employee.setRole(user.getRole());
        employee.setDob(user.getDob());
        
        return employee;
    }
    
    public Employee retrieveEmployeeByEmployeeId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Employees WHERE id = " + id;
        
        Employee employee = new Employee();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int userid = resultSet.getInt("userid");
                Boolean is_fulltime = resultSet.getBoolean("isFullTime");
                employee.setEmployeeId(id);
                employee.setId(userid);
                employee.setFullTime(is_fulltime);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, employee.getId());
        
        employee.setUsername(user.getUsername());
        employee.setPassword(user.getPassword());
        employee.setFirstname(user.getFirstname());
        employee.setLastname(user.getLastname());
        employee.setEmail(user.getEmail());
        employee.setAddress(user.getAddress());
        employee.setRole(user.getRole());
        employee.setDob(user.getDob());
        
        return employee;
    }
    
    public List<Employee> retrieveAllEmployees(DBConnection dbcon) {
        List<Employee> employees = new ArrayList<Employee>();
        
        String query = "SELECT * FROM Employees";
            
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int user_id = resultSet.getInt("userid");

                Employee employee = new Employee().retrieveEmployeeByUserId(dbcon, user_id);

                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return employees;
    }
    
    public List<Employee> filteredRetrieveAllEmployees(DBConnection dbcon, String filter) {
        List<Employee> employees = new ArrayList<Employee>();
        
        String query = "SELECT id FROM Users";
        if(filter.equals("all")) {
            query = query.concat(" WHERE role = 'doctor' OR role = 'nurse'");
        }
        else {
            query = query.concat(" WHERE role = '" + filter + "'");
        }
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int user_id = resultSet.getInt("id");

                Employee employee = new Employee().retrieveEmployeeByUserId(dbcon, user_id);

                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return employees;
    }
}