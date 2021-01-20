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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Harrison B
 */
public class Events {
    private Operation[] ops;
    
    public Operation[] getOps() {
        return this.ops;
    }
    
    public void getOperationsFromDB(DBConnection dbcon, int userid) {
        // Set up query
        String role = getRoleFromUserId(dbcon, userid);
        String query = "";
        if (role.equals("doctor") || role.equals("nurse")) {
            int employeeid = getEmpIdFromUserId(dbcon, userid);
            query = "SELECT * FROM Operations WHERE employeeid = " + employeeid;
        }
        else {
            int cid = getCliIdFromUserId(dbcon, userid);
            query = "SELECT * FROM Operations WHERE clientid = " + cid;
        }
        
        ArrayList<Operation> ops = new ArrayList<>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int opid = resultSet.getInt("id");
                int employeeid = resultSet.getInt("employeeid");
                int clientid = resultSet.getInt("clientid");
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                LocalTime start = LocalTime.parse(resultSet.getString("starttime"));
                LocalTime end = LocalTime.parse(resultSet.getString("endtime"));
                float charge = resultSet.getFloat("charge");
                Boolean is_paid = resultSet.getBoolean("is_paid");
                Boolean is_surgery = resultSet.getBoolean("is_surgery");
                String description = resultSet.getString("description");
                
                ops.add(new Operation(opid, employeeid, clientid, date, start, end, charge, is_paid, is_surgery, description));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        Operation opArr[] = new Operation[ops.size()];
        opArr = ops.toArray(opArr);
        
        this.ops = opArr;
    }
    
    private String getRoleFromUserId(DBConnection dbcon, int userid) {
        // Get role from Users table
        String query = "SELECT role FROM Users WHERE id = " + userid;
        String role = "Unknown";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                role = resultSet.getString("role");
            }
        } catch (SQLException e) {
            System.out.println(e);
            
        }
        return role;
    }
    
    private int getEmpIdFromUserId(DBConnection dbcon, int userid) {
        String query = "SELECT id FROM Employees WHERE userid = " + userid;
        int empid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                empid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
            
        }
        return empid;
    }
    
    private int getCliIdFromUserId(DBConnection dbcon, int userid) {
        String query = "SELECT id FROM Clients WHERE userid = " + userid;
        int cid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                cid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
            
        }
        return cid;
    }
    
    public Operation[] getEventsBetweenDates(LocalDate start, LocalDate end) {
        ArrayList<Operation> opList = new ArrayList<>();
        
        for (Operation op : this.ops) {
            // For each operation, check if in range.
            if (op.getDate().isAfter(start) && 
                    op.getDate().isBefore(end)) {
                opList.add(op);
            // For each operation, check if the date is equal to so the start or end date.
            } else if ((op.getDate().equals(start)) || (op.getDate().equals(end))){
                opList.add(op);
            } 
        }
        
        Operation[] opArr = new Operation[opList.size()];
        opArr = opList.toArray(opArr);
        return opArr;
    }
    
    public void orderEvents() {
        ArrayList<Operation> orderList = new ArrayList<Operation>(Arrays.asList(ops));
        
        Collections.sort(orderList, (op1, op2) -> {
            if (op1.getDate().isBefore(op2.getDate())) return -1; 
            else return 1;
        });
        
        this.ops = orderList.toArray(this.ops);
    }
    
    public void getFilteredOperationsFromDB(DBConnection dbcon, String filter) {
        // Set up query
        // Get employeeids of them
        // Get operations where empid = found_empids
        
        String queryStart = "SELECT id FROM Users WHERE ";
        String query = null;
        if (filter.equals("all")) {
            query = queryStart.concat("role = 'doctor' OR role = 'nurse'");
        }
        else {
            query = queryStart.concat("role = '" + filter + "'");
        }
        
        ArrayList<Employee> employees = new ArrayList<>();
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int userid = resultSet.getInt("id");
                Employee emp = new Employee().retrieveEmployeeByUserId(dbcon, userid);
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        ArrayList<Operation> ops = new ArrayList<>();
        query = "SELECT * FROM Operations WHERE ";
        for (int i = 0; i < employees.size() - 1; i++) {
            query = query.concat("employeeid = " + 
                    String.valueOf(employees.get(i).getEmployeeId()) + 
                    " OR ");
        }
        query = query.concat("employeeid = " + 
                String.valueOf(employees.get(employees.size()-1).getEmployeeId()));
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                int opid = resultSet.getInt("id");
                int employeeid = resultSet.getInt("employeeid");
                int clientid = resultSet.getInt("clientid");
                LocalDate date = LocalDate.parse(resultSet.getString("date"));
                LocalTime start = LocalTime.parse(resultSet.getString("starttime"));
                LocalTime end = LocalTime.parse(resultSet.getString("endtime"));
                float charge = resultSet.getFloat("charge");
                Boolean is_paid = resultSet.getBoolean("is_paid");
                Boolean is_surgery = resultSet.getBoolean("is_surgery");
                String description = resultSet.getString("description");
                
                ops.add(new Operation(opid, employeeid, clientid, date, start, end, charge, is_paid, is_surgery, description));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        Operation opArr[] = new Operation[ops.size()];
        opArr = ops.toArray(opArr);
        
        this.ops = opArr;
    }
}
