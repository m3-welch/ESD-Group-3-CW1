/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.Time;
import java.time.LocalTime;
/**
 *
 * @author conranpearce
 */
public class Bookings {
    private int id;
    private int employeeid;
    private int clientid;
    private boolean isSurgery;
    private Date date;
    private Time starttime;
    private Time endtime;
    private long slot;
    private boolean hasBeenPaid;
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
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
    
    public void setIsSurgery(boolean isSurgery) {
        this.isSurgery = isSurgery;
    }
    
    public boolean getIsSurgery() {
        return this.isSurgery;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setStartTime(Time starttime) {
        this.starttime = starttime;
    }
    
    public Time getStartTime() {
        return this.starttime;
    }
    
    public void setEndTime(Time endtime) {
        this.endtime = endtime;
    }
    
    public Time getEndTime() {
        return this.endtime;
    }
    
    public void setSlot(long slot) {
        this.slot = slot;
    }
    
    public long getSlot() {
        return this.slot;
    }
    
    public void setHasBeenPaid(boolean hasBeenPaid) {
        this.hasBeenPaid = hasBeenPaid;
    }
    
    public boolean getHasBeenPaid() {
        return this.hasBeenPaid;
    }
    
    public void cancel(DBConnection dbcon, int bookingId) {
        String query = "SELECT * FROM BookingSlots WHERE id = " + bookingId;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setDate(resultSet.getDate("date"));
                this.setStartTime(resultSet.getTime("starttime"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        LocalDate date = LocalDate.parse(this.date.toString());
        LocalTime time = LocalTime.parse(this.starttime.toString());
        LocalDateTime dt = LocalDateTime.of(date, time);
        
        LocalDateTime current = LocalDateTime.now();
        
        if (current.isBefore(dt)) {
            query = "DELETE FROM BookingSlots WHERE id = " + bookingId;
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                int resultSet = stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    public int getID(DBConnection dbcon, String user) {
        String query = "SELECT id FROM Users WHERE username = '" + user + "'";
        int userid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return userid;
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
    
    // Create a booking by appending data to the bookingslot table
    public void createBooking(DBConnection dbcon, String username, String employeeUsername, boolean isSurgery, Date date, Time startTime, Time endTime, long slot, boolean hasBeenPaid) {
        String query = "";
        
        // Get the user's id depending on the username
        int userid = getID(dbcon, username);
        
        // Get the client id from the userid in the Clients table
        query = "SELECT id FROM Clients WHERE userid = " + userid;
        int clientid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                clientid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        int employeeidbefore = getID(dbcon, employeeUsername);
        
        // Get the employee id from the userid in the Employees table
        query = "SELECT id FROM Employees WHERE userid = " + employeeidbefore;
        int employeeid = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Insert booking into BookingSlots table
        query = "INSERT INTO BookingSlots (employeeid, clientid, issurgery, date, starttime, endtime, slot, hasbeenpaid) VALUES (" + employeeid + ", " + clientid + ", " + isSurgery + ", '" + date + "', '" + startTime + "', '" + endTime + "', " + slot + ", " + hasBeenPaid + ")"; 

        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
       
    }
}
