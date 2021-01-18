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

/**
 *
 * @author Samlong
 */
public class Price {
    
    public void setPrice(DBConnection dbcon, String appointmentType, 
            String employeeType, String price) {
        String query = "UPDATE Prices SET priceperslot = " + price +
                " WHERE appointmenttype = '" + appointmentType + 
                "' AND employeetype = '" + employeeType + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Price changed");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public double getPrice(DBConnection dbcon, String appointmentType, 
            String employeeType, long slots) {
        String query = "SELECT priceperslot FROM Prices WHERE appointmenttype = '" + 
                appointmentType + "' AND employeetype = '" + employeeType + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            double price = rs.getLong(1);
            System.out.println("Price got = " + Double.toString(price));
            price = price * slots;
            return price;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
    }
    
    public void addPrice(DBConnection dbcon, String appointmentType,
            String employeeType, String price) {
        String query = "INSERT INTO Prices (appointmenttype, employeetype,"
                + " priceperslot) VALUES ('" + appointmentType + "', '" +
                employeeType + "', " + price + ")";
        
        String checkQuery = "SELECT COUNT(*) FROM Prices WHERE appointmenttype = '" + 
                appointmentType + "' AND employeetype = '" + employeeType + "'";

        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(checkQuery);
            rs.next();
            int count = rs.getInt(1);
            if (count > 0) {
              System.out.println("Can't add - appointment employee combo "
                      + "already exists \n use setPrice instead");
            }
            else {
                stmt.execute(query);
                System.out.println("Price added");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void removePrice(DBConnection dbcon, String appointmentType, 
            String employeeType, String price) {
        String query = "DELETE FROM Prices WHERE priceperslot = " + price +
                " AND appointmenttype = '" + appointmentType + 
                "' AND employeetype = '" + employeeType + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Price removed");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
