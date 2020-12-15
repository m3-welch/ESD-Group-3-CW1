/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
    
   
    public void patientBooking(DBConnection dbcon, String username, String employeeUsername) {
//    public void patientBooking(DBConnection dbcon, String username, String employeeUsername, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String query = "";

        // Get the user's id depending on the username
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
        
        
        
        query = "SELECT id FROM Users WHERE username = '" + employeeUsername + "'";
        int employeeidbefore = 0;
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeidbefore = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
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
                
//        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        int year = 2014;
        int month = 10;
        int day = 31;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1); // <-- months start
                                            // at 0.
        cal.set(Calendar.DAY_OF_MONTH, day);

        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        
        java.sql.Time startTime = Time.valueOf("18:45:00");
        java.sql.Time endTime = Time.valueOf("18:55:00");

        query = "INSERT INTO BookingSlots (employeeid, clientid, date, starttime, endtime) VALUES (" + employeeid + ", " + clientid + ", '" + date + "', '" + startTime + "', '" + endTime +  "')"; 

        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
       
    }
    
}
