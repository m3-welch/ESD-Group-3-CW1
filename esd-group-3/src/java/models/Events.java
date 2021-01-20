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
    
    public int cancelBooking(DBConnection dbcon, int appointmentid) {
//    public void cancelBooking(DBConnection dbcon, int clientid, int appointmentid) {
        String query = "";
        int resultSet = 0;
        // delete appointment from operations where operation is operation given the clientid and appointmentid
        query = "DELETE FROM Operations WHERE id = " + appointmentid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            resultSet = stmt.executeUpdate(query);
//            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return resultSet;
        // check if the booking has been cancelled or not, return true or false
    }
}
