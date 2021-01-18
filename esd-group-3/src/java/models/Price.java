
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
 * @author Samlong
 */
public class Price {

    
    private int id;
    private String appointmentType;
    private String employeeType;
    private float pricePerSlot;
    
    
    public Price() {
    }

    public Price(String appointmentType, String employeeType, float pricePerSlot) {
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
    
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
    
    public void update(int id) {     
        
        String query = "UPDATE Prices SET priceperslot = " + String.valueOf(this.getPricePerSlot()) +
                ", appointmenttype = '" + this.getAppointmentType() + 
                "', employeetype = '" + this.getEmployeeType() + "' WHERE Id = " + id;
        
        String queryPriceOnly = "UPDATE Prices SET priceperslot = " + String.valueOf(this.getPricePerSlot()) +
                "WHERE Id = " + id;
        
        String checkQuery = "SELECT COUNT(*) FROM Prices WHERE appointmenttype = '" + 
                this.getAppointmentType() + "' AND employeetype = '" + this.getEmployeeType() + "'";
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(checkQuery);
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                    ResultSet resset = stmt.executeQuery(checkQuery);
                    resset.next();
                    float pps = resset.getFloat(1);
                    if (pps == this.getPricePerSlot()){
                        System.out.println("Can't change - appointment employee combonation "
                        + "already exists");
                    }
                    else{
                        stmt.execute(queryPriceOnly);
                        System.out.println("Price changed");
                        }
                }
                else {
                    stmt.execute(query);
                    System.out.println("Price changed");
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
                System.out.println(e);
        }
    }
    
    public ArrayList retrievePriceTable() {
        ArrayList<Price> pricesArray = new ArrayList<Price>();
        String query = "SELECT * FROM Prices";
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {
                    Price tempPrice = new Price();
                    
                    tempPrice.setID(resultSet.getInt("id"));
                    tempPrice.setAppointmentType(resultSet.getString("appointmenttype"));
                    tempPrice.setEmployeeType(resultSet.getString("employeetype"));
                    tempPrice.setPricePerSlot(resultSet.getFloat("priceperslot"));
                    pricesArray.add(tempPrice);
                }
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
                System.out.println(e);
        }
        System.out.println(pricesArray);
        return pricesArray;
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
    
    //maybe redundent
    public float calcPrice(String appointmentType, String employeeType, float slots) {
        String query = "SELECT priceperslot FROM Prices WHERE appointmenttype = '" + 
                appointmentType + "' AND employeetype = '" + employeeType + "'";
        
        float price = 0;
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                price = rs.getFloat(1);
                System.out.println("Price got = " + price);
                price = price * slots;
            } catch (SQLException e) {
                System.out.println(e);
                
            }
        } catch (SQLException e) {
                System.out.println(e);
                
        }
        return price;
    }
    
    
    public void addPrice() {     
        
        String query = "INSERT INTO Prices (appointmenttype, employeetype,"
                + " priceperslot) VALUES ('" + this.getAppointmentType() + "', '" +
                this.getEmployeeType() + "', " +this.getPricePerSlot() + ")";
        
        String checkQuery = "SELECT COUNT(*) FROM Prices WHERE appointmenttype = '" + 
                this.getAppointmentType() + "' AND employeetype = '" + this.getEmployeeType() + "'";
        
        try {
            DBConnection dbcon = new DBConnection("smartcaretest", "", "");
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(checkQuery);
                rs.next();
                int count = rs.getInt(1);
                if (count > 0) {
                  System.out.println("Can't add - appointment employee combonation "
                          + "already exists \n edit price instead");
                }
                else {
                    stmt.execute(query);
                    System.out.println("Price added");
                }

            } catch (SQLException e) {
                System.out.println(e);
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
