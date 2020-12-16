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
    
    public String getEmployeeName(DBConnection dbcon, String queryGetEmployeeName) {
        int idOfEmployee = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryGetEmployeeName);
            while (resultSet.next()) {
                idOfEmployee = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        String query = "SELECT firstname, lastname FROM Users WHERE id = " + idOfEmployee;
        
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
    
    // Return a schedule for today of the whole
    public void getAllBookingsForDateSpecified(DBConnection dbcon, String startingDate, String endingDate) {        
                
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

        System.out.println("startingDate converted to java.sql.Date : " + sqlStartDate);
        System.out.println("endingDate converted to java.sql.Date : " + sqlEndDate);
        
        String query = "";
        
        if (sqlStartDate.equals(sqlEndDate)) {
            System.out.println("Schedule for " + sqlStartDate + ":");
            query = "SELECT * FROM BookingSlots WHERE date  = '" + sqlStartDate + "'";

            // Put in singular date query
        } else {
            System.out.println("Schedule for " + sqlStartDate + " to " + sqlEndDate + ":");
            query = "SELECT * FROM BookingSlots WHERE (date BEtWEEN '" + sqlStartDate + "' AND '" + sqlEndDate + "')";
        }
        
//        String query = "SELECT * FROM BookingSlots WHERE date  = '" + sqlStartDate + "'";
        
        String employeeName = "";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
                            
            while (resultSet.next()) {
                this.setStartTime(resultSet.getTime("starttime"));
                this.setEndTime(resultSet.getTime("endtime"));
                this.setEmployeeId(resultSet.getInt("employeeid"));
                this.setClientId(resultSet.getInt("clientid"));
                this.setDate(resultSet.getDate("date"));
                
                String queryGetEmployeeName = "SELECT id FROM Employees WHERE userid = " + this.employeeid;
                employeeName = getEmployeeName(dbcon, queryGetEmployeeName);
                
                ///////// PUT SCHEDULE IN ORDER - BASE THIS ON TIMES, NOT EMPLOYEES - ALLOW OVERLAP FOR WHICHEVER START TIME IS FIRST
                
                System.out.println("\nEmployee Name: "+ employeeName);
                // NEED TO PRINT OUT CLIENT NAME HERE - USER OBJECt MEthOD MORGAN MEtIONED
                System.out.println("Date: "+ this.date);
                System.out.println("Start time: "+ this.starttime);
                System.out.println("End time: "+ this.endtime);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    
    // Return a schedule for a doctor or a nurse
    public void getEmployeeSchedule(DBConnection dbcon, int employee, String todayOrFuture) {
        
        java.sql.Date todaysDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        
        String query = "";
                
        if (todayOrFuture == "today") {
            // Get the schedule from today
            
            query = "SELECT * FROM BookingSlots WHERE date  = '" + todaysDate + "' AND employeeid = " + employee;
            
            ArrayList<String> schedule = new ArrayList<String>();
            ArrayList<LocalDateTime> scheduleStartTime = new ArrayList<LocalDateTime>();
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);

                System.out.println("Schedule for employee: " + employee + "\ntoday's date: " + todaysDate);

                while (resultSet.next()) {
                    this.setStartTime(resultSet.getTime("starttime"));
                    this.setEndTime(resultSet.getTime("endtime"));
////                    this.setEmployeeId(resultSet.getInt("employeeid"));
//                    this.setClientId(resultSet.getInt("clientid"));
                    this.setDate(resultSet.getDate("date"));

////                    System.out.println("\nEmployeeID "+ this.employeeid);
//                    System.out.println("\nClientID "+ this.clientid);
//                    System.out.println("Start time "+ this.starttime);
//                    System.out.println("End time "+ this.endtime);
   
                    LocalDate date = LocalDate.parse(this.date.toString());
                    LocalTime time = LocalTime.parse(this.starttime.toString());
                    LocalDateTime dt = LocalDateTime.of(date, time);
                                        
//                    String appenedTogether = "ClietnID:" + this.clientid + " Start Time:" + this.starttime + " End Time:" + this.endtime; 
                    
                    scheduleStartTime.add(dt);
                    
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            
            System.out.println("\nPrint scheduleStartTime");
            for (int counter = 0; counter < scheduleStartTime.size(); counter++) { 		      
//                System.out.println("\nschedule.get(counter) " + schedule.get(counter)); 
                System.out.println("scheduleStartTime.get(counter) " + scheduleStartTime.get(counter)); 
                
            }   	
            
            System.out.println("\nSort array list");
            
            Collections.sort(scheduleStartTime);
            
            for (int counter = 0; counter < scheduleStartTime.size(); counter++) { 	
                System.out.println(scheduleStartTime.get(counter));
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedDateTime = scheduleStartTime.get(counter).format(formatter);
                
                System.out.println("FORMATTED DATE TIME " + formattedDateTime);
                
                String[] dateTimeSplit = formattedDateTime.split(" ");
                
                String time = dateTimeSplit[1];

                
                
                 query = "SELECT * FROM BookingSlots WHERE date  = '" + todaysDate + "' AND employeeid = " + employee + " AND startTime = '" + time + "'";
            
//                ArrayList<String> schedule = new ArrayList<String>();

                try (Statement stmt = dbcon.conn.createStatement()) {
                    ResultSet resultSet = stmt.executeQuery(query);

                    System.out.println("Schedule for employee: " + employee + "\ntoday's date: " + todaysDate);

                    while (resultSet.next()) {
                        this.setStartTime(resultSet.getTime("starttime"));
                        this.setEndTime(resultSet.getTime("endtime"));
    //                    this.setEmployeeId(resultSet.getInt("employeeid"));
                        this.setClientId(resultSet.getInt("clientid"));
                        this.setDate(resultSet.getDate("date"));

    //                    System.out.println("\nEmployeeID "+ this.employeeid);
                        System.out.println("\nClientID "+ this.clientid);
                        System.out.println("Start time "+ this.starttime);
                        System.out.println("End time "+ this.endtime);

                       
                        String appenedTogether = "ClietnID:" + this.clientid + " Start Time:" + this.starttime + " End Time:" + this.endtime; 

                        schedule.add(appenedTogether);

                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                
            }	      
            
            // For loop to select data using date and append in order
            
            System.out.println("\nSchedule ordered:");
            for (int counter = 0; counter < schedule.size(); counter++) { 	
                System.out.println(schedule.get(counter));
            }

            
            
        } else if (todayOrFuture == "future") {
            // Get the schedule from today and the future
        }
        
        // Determine here which employee schedule we are getting
        
        // If this schedule is for today only
        
        // Or if this schedule is from today and in the future (including today) but not showing previous
        
        
        // Input a certain employee id/other way, a variable for today or in the future
        // If today
        //      Return the schedule for today for an employee with the date, client name, time
        
        // If future
        //      Return the schedule for today and onwards for an employee with the date, client name, time
        
        
    }
}
