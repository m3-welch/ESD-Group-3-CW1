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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morgan
 */
public class Operation {
    private int operationid;
    private int employeeid;
    private int clientid;
    private String date;
    private String starttime;
    private String endtime;
    private float charge;
    private int slot;
    private boolean isnhs;
    
    public void setOperationId(int operationid) {
        this.operationid = operationid;
    }
    
    public int getOperationId() {
        return this.operationid;
    }
    
    public void setEmployeeId(int employeeid) {
        this.employeeid = employeeid;
    }
    
    public int getEmployeeId() {
        return this.employeeid;
    }
    
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setStartTime(String starttime) {
        this.starttime = starttime;
    }
    
    public String getStartTime() {
        return this.starttime;
    }
    
    public void setEndTime(String endtime) {
        this.endtime = endtime;
    }
    
    public String getEndTime() {
        return this.endtime;
    }
    
    public void setCharge(float charge) {
        this.charge = charge;
    }
    
    public float getCharge() {
        return this.charge;
    }
    
    public void setSlot(int slot) {
        this.slot = slot;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public void setIsnhs(boolean isnhs) {
        this.isnhs = isnhs;
    }
    
    public boolean getIsNhs() {
        return this.isnhs;
    }
    
    public int countAllOperations(DBConnection dbcon) {
                
        String query = "SELECT COUNT(*) FROM Operations";
        
        int noOfOperations = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                noOfOperations = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return noOfOperations;
    }
    
    public boolean is_nhs_patient(DBConnection dbcon, int patientId) {
        
        String query = "SELECT isnhs FROM Clients WHERE id = " + patientId;
        
        boolean isnhs = false;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                isnhs = Boolean.parseBoolean(resultSet.getString("isnhs"));
                // isnhs = resultSet.getBoolean("isnhs");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return isnhs;
        
    }
    
    public void retrieveByOperationId(DBConnection dbcon, int opId) {
        String query = "SELECT * FROM Operations WHERE id = " + opId;

        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setOperationId(Integer.parseInt(resultSet.getString("id")));
                this.setEmployeeId(Integer.parseInt(resultSet.getString("employeeid")));
                this.setClientId(Integer.parseInt(resultSet.getString("clientid")));
                this.setDate(resultSet.getString("date"));
                this.setStartTime(resultSet.getString("starttime"));
                this.setEndTime(resultSet.getString("endtime"));
                this.setCharge(Float.parseFloat(resultSet.getString("charge")));
                this.setSlot(Integer.parseInt(resultSet.getString("slot")));
                boolean isnhs = this.is_nhs_patient(dbcon, this.clientid);
                this.setIsnhs(isnhs);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void create(
        DBConnection dbcon,
        String employee_userid,
        String client_userid,
        String date,
        String starttime,
        String endtime,
        float charge,
        int slot,
        boolean isnhs) {
        
        String query = "SELECT id FROM Employees WHERE 'userid' = " + employee_userid;
        
        int employeeid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "SELECT id FROM Clients WHERE 'userid' = " + client_userid;
        
        int clientid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                clientid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, isnhs) VALUES ("
                + employeeid + ", " + clientid + ", '" + date + "', '" + starttime + "', '" + endtime + "', " + charge + ", " + slot + ")";
         
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "SELECT id FROM Operations WHERE 'employeeid' = " + employeeid + " AND 'clientid' = " + clientid + " AND 'date' = " + date + " AND 'starttime' = " + starttime + ")";
        
        int operationid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                operationid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        this.setOperationId(operationid);
        this.setEmployeeId(employeeid);
        this.setClientId(clientid);
        this.setDate(date);
        this.setStartTime(starttime);
        this.setEndTime(endtime);
        this.setCharge(charge);
        this.setSlot(slot);
        this.setIsnhs(is_nhs_patient(dbcon, clientid));
    }
}