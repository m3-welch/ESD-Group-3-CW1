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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.*;

/**
 *
 * @author morgan
 */
public class Client extends User {
    private int clientid;
    private String type;
    
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void create(
        DBConnection dbcon,
        String username,
        String password,
        String firstname,
        String lastname,
        String email,
        String address,
        String role,
        String type
    ) {
        String query = "INSERT INTO Users (username, password, firstname, lastname,"
            + "email, address, role) VALUES ('" + username + "', '" 
            + password + "', '" + firstname + "', '" + lastname + "', '" + email 
            + "', '" + address + "', '" + role + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        query = "INSERT INTO Clients (userid, type) VALUES (" + userid + ", '" + type + "')";
         
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "SELECT id FROM Clients WHERE username = '" + username + "'";
        
        int clientid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                clientid = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        this.setId(userid);
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setAddress(address);
        this.setRole(role);
        this.setClientId(clientid);
        this.setType(type);
    }
    
    public Client retrieveClientByUserId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Clients WHERE userid = " + id;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setClientId(resultSet.getInt("id"));
                this.setType(resultSet.getString("type"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, (int)id);
        
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setEmail(user.getEmail());
        this.setAddress(user.getAddress());
        this.setRole(user.getRole());
        
        return this;
    }
    
    public Client retrieveClientByUsername(DBConnection dbcon, String username) {
        String query = "SELECT id FROM Users WHERE username = '" + username + "'";
        
        int userid = 0;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                userid = resultSet.getInt("id");
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        this.retrieveClientByUserId(dbcon, userid);
        
        return this;
    }
    
    public List<Client> getAll(DBConnection dbcon, String filter) {
        List<Client> clients = new ArrayList<Client>();
        
        if (filter.equals("all")) {
            String query = "SELECT * FROM Clients";
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {
                    int user_id = resultSet.getInt("userid");

                    Client client = new Client().retrieveClientByUserId(dbcon, user_id);

                    clients.add(client);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            String query = "SELECT * FROM Clients WHERE type = '" + filter + "'";
            
            try (Statement stmt = dbcon.conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(query);
                while (resultSet.next()) {
                    int user_id = resultSet.getInt("userid");

                    Client client = new Client().retrieveClientByUserId(dbcon, user_id);

                    clients.add(client);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        
        return clients;
    }
    
    public void drop(DBConnection dbcon) {
        
        String query = "";
                
        
//        DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';

//        query = "DELETE FROM Users";
        query = "DELETE FROM Clients WHERE userid=3";
//        query = "DELETE FROM Users WHERE username='connie'";

        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public long calculateTotalCost(DBConnection dbcon, ArrayList<Bookings> bookings){
        long tCost = 0l;
        Bookings currentBooking = new Bookings();
        Price p = new Price();
        for (int i = 0; i < bookings.size(); i++){
            currentBooking = bookings.get(i); 
            String role = currentBooking.getRoleFromId(dbcon);
            String apptType;
            long timeDiff = Duration.between(currentBooking.getEndTime().toLocalTime(), currentBooking.getStartTime().toLocalTime()).toMinutes();
            long slots = timeDiff/10;
            //time in doctor surgeries
            if(role == "doctor" && currentBooking.getIsSurgery()){
                //is doctor surgery
                apptType = "surgery";
                tCost += p.calcPrice(apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && currentBooking.getIsSurgery()){
                //is nurse surgery
                apptType = "surgery";
                tCost += p.calcPrice(apptType, role, slots);
            }

            else if(role == "doctor" && !currentBooking.getIsSurgery()){
                //is doctor consultation
                apptType = "consultaion";
                tCost += p.calcPrice(apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && !currentBooking.getIsSurgery()){
                //is nurse consultation
                apptType = "consultaion";
                tCost += p.calcPrice(apptType, role, slots);
            }
        }
        return tCost;
    }
    
    public ArrayList<String> getInvoice(DBConnection dbcon, int clid){        
        String query = "SELECT *" +
	"FROM BookingSlots" +
        " WHERE clientid = " + clid + " AND hasbeenpaid = FALSE";
        ArrayList<String> invoice = new ArrayList<>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Bookings> bookingsArray = new ArrayList<>();
            invoice.add("Is Surgery | Date | Start Time | End Time | Cost");
            while(rs.next()){
                ArrayList<Bookings> singleBooking = new ArrayList<>();
                Bookings b = new Bookings();
                b.setId(Integer.parseInt(rs.getString("id")));
                b.setEmployeeId(Integer.parseInt(rs.getString("employeeid")));
                b.setClientId(Integer.parseInt(rs.getString("clientid")));
                b.setIsSurgery(rs.getBoolean("issurgery"));
                b.setDate(rs.getDate("date"));
                b.setStartTime(rs.getTime("starttime"));
                b.setEndTime(rs.getTime("endtime"));
                b.setSlot(rs.getLong("slot"));
                b.setHasBeenPaid(rs.getBoolean("hasbeenpaid"));
                bookingsArray.add(b);
                singleBooking.add(b);
                invoice.add(String.valueOf(b.getIsSurgery()) + "\t" + String.valueOf(b.getDate()) +
                        "\t" + String.valueOf(b.getStartTime()) + "\t" +String.valueOf(b.getEndTime()) +
                        "\t" + String.valueOf(this.calculateTotalCost(dbcon, singleBooking)));
            }
            invoice.add("Total = " + String.valueOf(this.calculateTotalCost(dbcon, bookingsArray)));
                       
        } catch (SQLException e) {
            System.out.println(e);
        }
        return invoice;
    }
    
    public void payBill(DBConnection dbcon, String clid){
        String query = "UPDATE BookingSlots SET hasbeenpaid = True WHERE hasbeenpaid = False;";
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Payment made");
        } catch (SQLException e) {
            System.out.println(e);
        }
    } 
}
