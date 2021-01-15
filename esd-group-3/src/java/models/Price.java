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
import java.util.ArrayList;

/**
 *
 * @author Sam
 */
public class Price {

    
    private String employeeType;
    private String appointmentType;
    private float pricePerSlot;
    
    
    public Price() {
    }

    public Price(String employeeType, String appointmentType, float pricePerSlot) {
        this.employeeType = employeeType;
        this.appointmentType = appointmentType;
        this.pricePerSlot = pricePerSlot;
    }
    
    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public float getPricePerSlot() {
        return pricePerSlot;
    }

    public void setPricePerSlot(float pricePerSlot) {
        this.pricePerSlot = pricePerSlot;
    }
    
    public void setPrice(DBConnection dbcon, String id) {
        String query = "UPDATE Prices SET priceperslot = " + String.valueOf(this.getPricePerSlot()) +
                ", appointmenttype = '" + this.getAppointmentType() + 
                "', employeetype = '" + this.getEmployeeType() + "' WHERE Id = " + id;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            System.out.println("+++++++++++++++++++++++++++++ Triggered");
            stmt.execute(query);
            System.out.println("Price changed");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public ArrayList retrievePriceTable(DBConnection dbcon) {
        ArrayList<Price> pricesArray = new ArrayList<Price>();
        String query = "SELECT * FROM Prices";
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Price tempPrice = new Price();
                
                tempPrice.setAppointmentType(resultSet.getString("appointmenttype"));
                tempPrice.setEmployeeType(resultSet.getString("employeetype"));
                tempPrice.setPricePerSlot(resultSet.getFloat("priceperslot"));
                pricesArray.add(tempPrice);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(pricesArray);
        return pricesArray;
    }
    
    //maybe redundent
    public float getPrice(DBConnection dbcon, String appointmentType, 
            String employeeType, float slots) {
        String query = "SELECT priceperslot FROM Prices WHERE appointmenttype = '" + 
                appointmentType + "' AND employeetype = '" + employeeType + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            float price = rs.getFloat(1);
            System.out.println("Price got = " + price);
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
    
    public void removePrice() {
        String query = "DELETE FROM Prices WHERE priceperslot = " + this.getPricePerSlot() +
                " AND appointmenttype = '" + this.getAppointmentType() + 
                "' AND employeetype = '" + this.getEmployeeType() + "'";
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            try (Statement stmt = dbcon.conn.createStatement()) {
                stmt.execute(query);
                System.out.println("Price removed");
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }       
    }
}
