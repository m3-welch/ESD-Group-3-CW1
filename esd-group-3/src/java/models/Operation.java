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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Austin
 */
public class Operation {
    private int operationid;
    private int employeeid;
    private int clientid;
    private LocalDate date;
    private LocalTime starttime;
    private LocalTime endtime;
    private float charge;
    private boolean is_paid;
    private boolean is_nhs;
    private boolean is_surgery;

    public Operation(int operationid, int employeeid, int clientid, LocalDate date, LocalTime starttime, LocalTime endtime, float charge, boolean is_paid, boolean is_surgery) {
        this.operationid = operationid;
        this.employeeid = employeeid;
        this.clientid = clientid;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.charge = charge;
        this.is_paid = is_paid;
        this.is_surgery = is_surgery;
    }
    
    public Operation() {
    }
    
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);
    }
    
    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatted_date = this.date.format(formatter);
        return formatted_date;
    }
    
    public LocalDate getDateLocalDate() {
        return this.date;
    }
    
    public void setStartTime(String starttime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.starttime = LocalTime.parse(starttime, formatter);
    }
    
    public String getStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatted_time = this.starttime.format(formatter);
        return formatted_time;
    }
    
    public LocalTime getStartLocalTime() {
        return this.starttime;
    }
    
    public void setEndTime(String endtime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.endtime = LocalTime.parse(endtime, formatter);
    }
    
    public String getEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatted_time = this.endtime.format(formatter);
        return formatted_time;
    }
    
    public LocalTime getEndLocalTime() {
        return this.endtime;
    }
    
    public void setCharge(float charge) {
        this.charge = charge;
    }
    
    public float getCharge() {
        return this.charge;
    }
    
    public void setIsPaid(boolean is_paid) {
        this.is_paid = is_paid;
    }
    
    public boolean getIsPaid() {
        return this.is_paid;
    }
    
    public void setIsNhs(boolean is_nhs) {
        this.is_nhs = is_nhs;
    }
    
    public boolean getIsNhs() {
        return this.is_nhs;
    }
    
    public boolean getIsSurgery() {
        return is_surgery;
    }

    public void setIsSurgery(boolean is_surgery) {
        this.is_surgery = is_surgery;
    }
   
    public int countAllOperations(DBConnection dbcon) {
                
        String query = "SELECT COUNT(*) AS rowcount FROM Operations";
        
        int noOfOperations = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();
            noOfOperations = resultSet.getInt("rowcount");
            resultSet.close();
        } 
        catch (SQLException e) {
            System.out.println(e);
        }
        
        return noOfOperations;
    }
    
    public boolean isNhsPatient(DBConnection dbcon, int patientId) {
        
        String query = "SELECT isnhs FROM Clients WHERE id = " + patientId;
        
        boolean is_nhs = false;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                is_nhs = resultSet.getBoolean("isnhs");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return is_nhs;
        
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
                this.setIsPaid(resultSet.getBoolean("is_paid"));
                this.setIsSurgery(resultSet.getBoolean("is_surgery"));
                this.setIsNhs(isNhsPatient(dbcon, this.clientid));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public ArrayList retrieveAllOperationsWhere(DBConnection dbcon, boolean all, boolean is_nhs, String start_date, String end_date) {
        ArrayList<Operation> operationsArray = new ArrayList<Operation>();
        String query;
        if (start_date != null && end_date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startdate = LocalDate.parse(start_date, formatter);
            LocalDate enddate = LocalDate.parse(end_date, formatter);
            
            query = "SELECT * FROM Operations WHERE date BETWEEN '" + startdate + "' AND '" + enddate + "'";
        }
        else {
            query = "SELECT * FROM Operations";
        }
        
            
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Operation tempOp = new Operation();

                tempOp.setOperationId(Integer.parseInt(resultSet.getString("id")));
                tempOp.setEmployeeId(Integer.parseInt(resultSet.getString("employeeid")));
                tempOp.setClientId(Integer.parseInt(resultSet.getString("clientid")));
                tempOp.setDate(resultSet.getString("date"));
                tempOp.setStartTime(resultSet.getString("starttime"));
                tempOp.setEndTime(resultSet.getString("endtime"));
                tempOp.setCharge(Float.parseFloat(resultSet.getString("charge")));
                tempOp.setIsPaid(resultSet.getBoolean("is_paid"));
                tempOp.setIsSurgery(resultSet.getBoolean("is_surgery"));
                tempOp.setIsNhs(isNhsPatient(dbcon, tempOp.clientid));

                if (all) {
                    operationsArray.add(tempOp);
                }
                else if (is_nhs && tempOp.is_nhs) {
                    operationsArray.add(tempOp);
                }
                else if (!is_nhs && !tempOp.is_nhs) {
                    operationsArray.add(tempOp);
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        
        return operationsArray;
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
        boolean is_paid,
        boolean is_surgery) {
        
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
        
        query = "INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, slot, is_paid, is_surgery) VALUES ("
                + employeeid + ", " + clientid + ", '" + date + "', '" + starttime + "', '" + endtime + "', " + charge + ", " + slot + ", " + is_paid + ", " + is_surgery + ")";
         
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
        this.setIsPaid(is_paid);
        this.setIsSurgery(is_surgery);
    }
    
    public String getRoleFromId(DBConnection dbcon){
        String query = "SELECT role FROM Users WHERE id = " + this.employeeid;
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
}
