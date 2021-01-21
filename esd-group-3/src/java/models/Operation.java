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
import java.time.Duration;
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
    private String description;

    public Operation(
        int operationid,
        int employeeid,
        int clientid,
        LocalDate date,
        LocalTime starttime,
        LocalTime endtime,
        float charge,
        boolean is_paid,
        boolean is_surgery,
        String description
    ) {
        this.operationid = operationid;
        this.employeeid = employeeid;
        this.clientid = clientid;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.charge = charge;
        this.is_paid = is_paid;
        this.is_surgery = is_surgery;
        this.description = description;
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
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDate getDate() {
        return this.date;
    }
    
    public void setStartTime(LocalTime starttime) {
        this.starttime = starttime;
    }
    
    public LocalTime getStartTime() {
        return this.starttime;
    }
    
    public void setEndTime(LocalTime endtime) {
        this.endtime = endtime;
    }
    
    public LocalTime getEndTime() {
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
   
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
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
                this.setDate(LocalDate.parse(resultSet.getDate("date").toString()));
                this.setStartTime(LocalTime.parse(resultSet.getTime("starttime").toString()));
                this.setEndTime(LocalTime.parse(resultSet.getTime("endtime").toString()));
                this.setCharge(Float.parseFloat(resultSet.getString("charge")));
                this.setIsPaid(resultSet.getBoolean("is_paid"));
                this.setIsSurgery(resultSet.getBoolean("is_surgery"));
                this.setIsNhs(isNhsPatient(dbcon, this.clientid));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public ArrayList retrieveAllOperationsWhere(DBConnection dbcon, int filter, boolean unpaid, LocalDate startDate, LocalDate endDate, int clientid) {
        ArrayList<Operation> operationsArray = new ArrayList<Operation>();
        String query = "SELECT * FROM Operations WHERE date BETWEEN '" + startDate.toString() + "' AND '" + endDate.toString() + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            if(!resultSet.next()) {
                return operationsArray;
            }
            while (resultSet.next()) {
                Operation tempOp = new Operation();

                tempOp.setOperationId(Integer.parseInt(resultSet.getString("id")));
                tempOp.setEmployeeId(Integer.parseInt(resultSet.getString("employeeid")));
                tempOp.setClientId(Integer.parseInt(resultSet.getString("clientid")));
                tempOp.setDate(LocalDate.parse(resultSet.getDate("date").toString()));
                tempOp.setStartTime(LocalTime.parse(resultSet.getTime("starttime").toString()));
                tempOp.setEndTime(LocalTime.parse(resultSet.getTime("endtime").toString()));
                tempOp.setCharge(Float.parseFloat(resultSet.getString("charge")));
                tempOp.setIsPaid(resultSet.getBoolean("is_paid"));
                tempOp.setIsSurgery(resultSet.getBoolean("is_surgery"));
                tempOp.setIsNhs(isNhsPatient(dbcon, tempOp.clientid));
                
                // clientid = 0 means all clientids, else must match
                if (clientid == 0 || clientid == tempOp.clientid) {
                    // all patient types
                    if (filter == 0) {
                        // unpaid only filter && op is not paid
                        if (unpaid && !tempOp.is_paid) {
                            operationsArray.add(tempOp);
                        }
                        // not unpaid only
                        else if (!unpaid) {
                            operationsArray.add(tempOp);
                        }
                    }
                    // nhs patients only
                    else if (filter == 1 && tempOp.is_nhs) {
                        if (unpaid && !tempOp.is_paid) {
                            operationsArray.add(tempOp);
                        }  
                        else if (!unpaid) {
                            operationsArray.add(tempOp);
                        }
                    }
                    // private patients only
                    else if (filter == 2 && !tempOp.is_nhs) {
                        if (unpaid && !tempOp.is_paid) {
                            operationsArray.add(tempOp);
                        }  
                        else if (!unpaid) {
                            operationsArray.add(tempOp);
                        }
                    }
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
        int employee_userid,
        int client_userid,
        LocalDate date,
        LocalTime starttime,
        LocalTime endtime,
        float charge,
        boolean is_paid,
        boolean is_surgery,
        String description
    ) {
        Employee employee = new Employee();
        employee = employee.retrieveEmployeeByUserId(dbcon, employee_userid);
        int employeeId = employee.getEmployeeId();
        Client client = new Client();
        client.retrieveClientByUserId(dbcon, client_userid);
        int clientId = client.getClientId();
        
        this.setOperationId(operationid);
        this.setEmployeeId(employeeId);
        this.setClientId(clientId);
        this.setDate(date);
        this.setStartTime(starttime);
        this.setEndTime(endtime);
        this.setCharge(charge);
        this.setIsPaid(is_paid);
        this.setIsSurgery(is_surgery);
        
        float cost = this.calculateOperationCost(dbcon, this);
        
        String query = "INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ("
                + employeeId + ", " + clientId + ", '" + date + "', '" + starttime + "', '" + endtime + "', " + cost + ", " + is_paid + ", " + is_surgery + ", '" + description + "')";
         
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "SELECT id FROM Operations WHERE 'employeeid' = " + employeeid + " AND 'clientid' = " + clientid + " AND 'date' = '" + date + "' AND 'starttime' = '" + starttime + "')";
        
        int operationid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                operationid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public String getRoleFromId(DBConnection dbcon){
        String query = "SELECT role FROM Users WHERE id = " + new Employee().retrieveEmployeeByEmployeeId(dbcon, this.employeeid).getId();
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
    
    public float calculateOperationCost(DBConnection dbcon, Operation op) {
        long timeDiff = Duration.between(op.getStartTime(), op.getEndTime()).toMinutes();
        long slots = timeDiff/10;
        
        Price p = new Price();
        String role = op.getRoleFromId(dbcon);
        String apptType;
        Double cost = 0.00;
        //time in doctor surgeries
            if(role.equals("doctor") && op.getIsSurgery()){
                //is doctor surgery
                apptType = "surgery";
                cost = p.getPrice(dbcon, apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role.equals("nurse") && op.getIsSurgery()){
                //is nurse surgery
                apptType = "surgery";
                cost = p.getPrice(dbcon, apptType, role, slots);
            }

            else if(role.equals("doctor") && !op.getIsSurgery()){
                //is doctor consultation
                apptType = "consultation";
                cost = p.getPrice(dbcon, apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role.equals("nurse") && !op.getIsSurgery()){
                //is nurse consultation
                apptType = "consultation";
                cost = p.getPrice(dbcon, apptType, role, slots);
            }
        this.setCharge(cost.floatValue());
        return cost.floatValue();
    }

    public String getEmpLastNameFromId(DBConnection dbcon){
        String query = "SELECT userid FROM Employees WHERE id = " + this.employeeid;
        int userid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "SELECT lastname FROM Users WHERE id = " + userid;
        String name = "Unknown";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                name = resultSet.getString("lastname");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return name;
    }
    
    public String getClientFullNameFromId(DBConnection dbcon) {
        String query = "SELECT userid FROM Clients WHERE id = " + this.clientid;
        int userid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        query = "SELECT firstname, lastname FROM Users WHERE id = " + userid;
        String firstname = "Unknown";
        String lastname = "Unknown";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                firstname = resultSet.getString("firstname");
                lastname = resultSet.getString("lastname");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return firstname + " " + lastname;
    }
    
    public void payByOperationId(DBConnection dbcon, int opId) {
        String query = "UPDATE Operations SET is_paid = TRUE WHERE id = " + opId;
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateThisOperation(DBConnection dbcon) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String paid;
        if (this.is_paid) {
            paid = "TRUE";
        }
        else { paid = "FALSE"; }
        
        String query = "UPDATE Operations "
                + "SET starttime = '" + this.starttime.format(dtf) + "', endtime = '" + this.endtime.format(dtf) + "', charge = " + this.charge + ", is_paid = " + paid + ", description = '" + this.description + "' "
                + "WHERE id = " + this.operationid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
