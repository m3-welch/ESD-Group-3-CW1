/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
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
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.*;
import java.time.*;
/**
 *
 * @author conranpearce
 */
public class Bookings {
    private int id;
    private int employeeid;
    private int clientid;
    private Date date;
    private Time starttime;
    private Time endtime;
    
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
    
    public Time getEndId() {
        return this.endtime;
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
    public int getIdFromDatabase(DBConnection dbcon, String user) {
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
    
    // Create a booking by appending data to the bookingslot table
    public void createBooking(DBConnection dbcon, String username, String employeeUsername, Date date, Time startTime, Time endTime) {
        String query = "";
        
        // Get the user's id depending on the username
        int userid = getIdFromDatabase(dbcon, username);
        
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
        int employeeidbefore = getIdFromDatabase(dbcon, employeeUsername);
        
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
        query = "INSERT INTO BookingSlots (employeeid, clientid, date, starttime, endtime) VALUES (" + employeeid + ", " + clientid + ", '" + date + "', '" + startTime + "', '" + endTime +  "')"; 

        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
       
    }
    
    public String getName(DBConnection dbcon, String queryGetName) {
        int individualsID = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryGetName);
            while (resultSet.next()) {
                individualsID = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
                
        String query = "SELECT firstname, lastname FROM Users WHERE id = " + individualsID;
        
        String firstNameEmployee = "", lastNameEmployee = "";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                firstNameEmployee = resultSet.getString("firstname");
                lastNameEmployee = resultSet.getString("lastname");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return firstNameEmployee + " " + lastNameEmployee;
    }
    
    // Return the bookings from the table
    public void getBookings(DBConnection dbcon, String query) {
        String employeeName = "", clientName = "";

        try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {                    
                    this.setStartTime(resultSet.getTime("starttime"));
                    this.setEndTime(resultSet.getTime("endtime"));
                    this.setEmployeeId(resultSet.getInt("employeeid"));
                    this.setClientId(resultSet.getInt("clientid"));
                    this.setDate(resultSet.getDate("date"));
                    
                    // Get the employee and clients name
                    String queryGetEmployeeName = "SELECT userid FROM Employees WHERE id = " + this.employeeid;
                    employeeName = getName(dbcon, queryGetEmployeeName);
                    String queryGetClientName = "SELECT userid FROM Clients WHERE id = " + this.clientid;
                    clientName = getName(dbcon, queryGetClientName);

                    // Print out the booking information in full in chronological order
                    System.out.println("\nEmployee Name: "+ employeeName);
                    System.out.println("Client Name: "+ clientName);
                    System.out.println("Date: "+ this.date);
                    System.out.println("Start time: "+ this.starttime);
                    System.out.println("End time: "+ this.endtime);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }	
    }
    
    // Return the query which should be sent, depending on the date range and the employee id
    public String setSelectAllWithDateQuery(String startingDate, String endingDate, int employee) {
        // Set the starting date and ending date into the sql format after being recieved as a string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date startDate = null, endDate = null;
        java.sql.Date sqlStartDate = null, sqlEndDate = null;
        try {
            startDate = sdf.parse(startingDate);
            endDate = sdf.parse(endingDate);
            sqlStartDate = new Date(startDate.getTime());
            sqlEndDate = new Date(endDate.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // If the start and end date is the same then only return schedule for one day
        String query = "";
        if (sqlStartDate.equals(sqlEndDate)) {
            System.out.println("\nSchedule for " + sqlStartDate + ":");
            // If no employee selected then return all, else return the specific employees schedule
            if (employee == 0) {
                query = "SELECT * FROM BookingSlots WHERE date  = '" + sqlStartDate + "'";            
            } else {
                query = "SELECT * FROM BookingSlots WHERE date  = '" + sqlStartDate + "' AND employeeid = " + employee;        
            }
        // Else return the schedule for the date range
        } else {
            System.out.println("\nSchedule for " + sqlStartDate + " to " + sqlEndDate + ":");
            if (employee == 0) {
                query = "SELECT * FROM BookingSlots WHERE (date BETWEEN '" + sqlStartDate + "' AND '" + sqlEndDate + "')";
            } else {
                query = "SELECT * FROM BookingSlots WHERE (date BETWEEN '" + sqlStartDate + "' AND '" + sqlEndDate + "') AND employeeid = " + employee;
            }
        }
        
        return query;
    }
    
    // Return a schedule for today of the whole
    public void getAllBookingsForDateSpecified(DBConnection dbcon, int employee, String startingDate, String endingDate) {   
        
        String query = "";
        query = setSelectAllWithDateQuery(startingDate, endingDate, employee);
                        
        ArrayList<LocalDateTime> scheduleStartTime = new ArrayList<LocalDateTime>();
        
        // Set the scheduleStartTime array with the dates and times set within the bookingSlot table
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setStartTime(resultSet.getTime("starttime"));
                this.setEndTime(resultSet.getTime("endtime"));
                this.setDate(resultSet.getDate("date"));
                // Set the date and time in a compariable format
                LocalDate date = LocalDate.parse(this.date.toString());
                LocalTime time = LocalTime.parse(this.starttime.toString());
                LocalDateTime dt = LocalDateTime.of(date, time);
                // Append to the array list
                scheduleStartTime.add(dt);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }	
        
        // Sort the time and dates in chronological order
        Collections.sort(scheduleStartTime);
        
        // Loop through the sorted time and dates list, to then select the booking information
        for (int counter = 0; counter < scheduleStartTime.size(); counter++) { 	
                        
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = scheduleStartTime.get(counter).format(formatter);
            String[] dateTimeSplit = formattedDateTime.split(" ");
            String date = dateTimeSplit[0];
            String time = dateTimeSplit[1];
            
            // Select either all bookings or a booking for a specific employee
            if (employee == 0) {
                query = "SELECT * FROM BookingSlots WHERE date  = '" + date + "' AND starttime = '" + time + "'";
            } else {
                query = "SELECT * FROM BookingSlots WHERE date  = '" + date + "' AND starttime = '" + time + "' AND employeeid = " + employee;
            }
            
            // Output the bookings from the table
            getBookings(dbcon, query);
        }
    }
}
