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
        
        String isNHS;
        if (type.equals("NHS")) {
            isNHS = "TRUE";
        } else {
            isNHS = "FALSE";
        }
        
        query = "INSERT INTO Clients (userid, isNHS) VALUES (" + userid + ", " + isNHS + ")";
         
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
    
    public long calculateTotalCost(DBConnection dbcon, ArrayList<Operation> operations){
        long tCost = 0l;
        Operation currentOp = new Operation();
        Price p = new Price();
        for (int i = 0; i < operations.size(); i++){
            currentOp = operations.get(i); 
            String role = currentOp.getRoleFromId(dbcon);
            String apptType;
            long timeDiff = Duration.between(currentOp.getEndLocalTime(), currentOp.getStartLocalTime()).toMinutes();
            long slots = timeDiff/10;
            //time in doctor surgeries
            if(role == "doctor" && currentOp.getIsSurgery()){
                //is doctor surgery
                apptType = "surgery";
                tCost += p.getPrice(dbcon, apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && currentOp.getIsSurgery()){
                //is nurse surgery
                apptType = "surgery";
                tCost += p.getPrice(dbcon, apptType, role, slots);
            }

            else if(role == "doctor" && !currentOp.getIsSurgery()){
                //is doctor consultation
                apptType = "consultaion";
                tCost += p.getPrice(dbcon, apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && !currentOp.getIsSurgery()){
                //is nurse consultation
                apptType = "consultaion";
                tCost += p.getPrice(dbcon, apptType, role, slots);
            }
        }
        return tCost;
    }
    
    public ArrayList<String> getInvoice(DBConnection dbcon, int clid){        
        String query = "SELECT *" +
	"FROM Operations" +
        " WHERE clientid = " + clid + " AND is_paid = FALSE";
        ArrayList<String> invoice = new ArrayList<>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Operation> operationsArray = new ArrayList<>();
            invoice.add("Is Surgery | Date | Start Time | End Time | Cost");
            while(rs.next()){
                ArrayList<Operation> singleOp = new ArrayList<>();
                Operation op = new Operation();
                op.setOperationId(Integer.parseInt(rs.getString("id")));
                op.setEmployeeId(Integer.parseInt(rs.getString("employeeid")));
                op.setClientId(Integer.parseInt(rs.getString("clientid")));
                op.setIsSurgery(rs.getBoolean("issurgery"));
                op.setDate(rs.getString("date"));
                op.setStartTime(rs.getString("starttime"));
                op.setEndTime(rs.getString("endtime"));
                //op.setSlot(rs.getLong("slot"));
                op.setIsPaid(rs.getBoolean("ispaid"));
                operationsArray.add(op);
                singleOp.add(op);
                invoice.add(String.valueOf(op.getIsSurgery()) + "\t" + String.valueOf(op.getDate()) +
                        "\t" + String.valueOf(op.getStartTime()) + "\t" +String.valueOf(op.getEndTime()) +
                        "\t" + String.valueOf(this.calculateTotalCost(dbcon, singleOp)));
            }
            invoice.add("Total = " + String.valueOf(this.calculateTotalCost(dbcon, operationsArray)));
                       
        } catch (SQLException e) {
            System.out.println(e);
        }
        return invoice;
    }
    
    public void payBill(DBConnection dbcon, String clid){
        String query = "UPDATE Operations SET hasbeenpaid = True WHERE hasbeenpaid = False;";
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
            System.out.println("Payment made");
        } catch (SQLException e) {
            System.out.println(e);
        }
    } 
}
