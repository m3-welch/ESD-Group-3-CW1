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
    private Boolean is_nhs;
  
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    public void setIsNhs(Boolean is_nhs) {
        this.is_nhs = is_nhs;
    }
    
    public Boolean getIsNhs() {
        return this.is_nhs;
    }
    
    public String getClientType() {
        if (this.is_nhs) {
            return "NHS";
        }
        else {
            return "private";
        }
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
        String type,
        LocalDate dob
    ) {
        String query = "INSERT INTO Users (username, password, firstname, lastname,"
            + "email, address, role, dob) VALUES ('" + username + "', '" 
            + password + "', '" + firstname + "', '" + lastname + "', '" + email 
            + "', '" + address + "', '" + role + "', '" + dob + "')";
        
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
        
        Boolean isNHS;
        if (type.equals("NHS")) {
            isNHS = true;
        } else {
            isNHS = false;
        }
        
        query = "INSERT INTO Clients (userid, isNHS) VALUES (" + userid + ", " + isNHS + ")";
         
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
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
        
        this.setId(userid);
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setAddress(address);
        this.setRole(role);
        this.setClientId(clientid);
        this.setIsNhs(isNHS);
        this.setDob(dob);
    }
    
    public Client retrieveClientByUserId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Clients WHERE userid = " + id;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setClientId(resultSet.getInt("id"));
                this.setIsNhs(resultSet.getBoolean("isNhs"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, (int)id);
        
        this.setId(id);
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setEmail(user.getEmail());
        this.setAddress(user.getAddress());
        this.setRole(user.getRole());
        this.setDob(user.getDob());
        
        return this;
    }
    
    public Client retrieveClientByClientId(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Clients WHERE id = " + id;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setId(resultSet.getInt("userid"));
                this.setIsNhs(resultSet.getBoolean("isNhs"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, this.getId());
        
        this.setId(id);
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        this.setEmail(user.getEmail());
        this.setAddress(user.getAddress());
        this.setRole(user.getRole());
        this.setDob(user.getDob());
        
        return this;
    } 
    
    // Similar to retrieveClientByUserId but with differences to support delteing a patient
    public Client retrieveClientByIdDrop(DBConnection dbcon, int id) {
        String query = "SELECT * FROM Clients WHERE id = " + id;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setClientId(resultSet.getInt("userid"));
                this.setIsNhs(resultSet.getBoolean("isNHS"));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        User user = new User();
        user.retrieveByUserId(dbcon, this.getClientId());
        
        this.setUsername(user.getUsername());
        this.setFirstname(user.getFirstname());
        this.setLastname(user.getLastname());
        
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
        
        if ("all".equals(filter)) {
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
            String isNHS;
            
            if ("NHS".equals(filter)) {
                isNHS = "TRUE";
            } else {
                isNHS = "FALSE";
            }
            
            String query = "SELECT * FROM Clients WHERE isNHS = " + isNHS;
            
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
        
        String query = "DELETE FROM Clients WHERE userid=" + this.getClientId();
        String query2 = "DELETE FROM Users WHERE role='client' AND username='" + this.getUsername() + "' AND firstname='" + this.getFirstname() + "' AND lastname='" + this.getLastname() + "'";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            System.out.println(query + " and " + query2);
            //stmt.execute(query);
            stmt.execute(query2);
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
            long timeDiff = Duration.between(currentOp.getEndTime(), currentOp.getStartTime()).toMinutes();
            long slots = timeDiff/10;
            //time in doctor surgeries
            if(role == "doctor" && currentOp.getIsSurgery()){
                //is doctor surgery
                apptType = "surgery";
                tCost += p.calcPrice(apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && currentOp.getIsSurgery()){
                //is nurse surgery
                apptType = "surgery";
                tCost += p.calcPrice(apptType, role, slots);
            }

            else if(role == "doctor" && !currentOp.getIsSurgery()){
                //is doctor consultation
                apptType = "consultaion";
                tCost += p.calcPrice(apptType, role, slots);
            }
            //time in nurse surgeries
            else if(role == "nurse" && !currentOp.getIsSurgery()){
                //is nurse consultation
                apptType = "consultaion";
                tCost += p.calcPrice(apptType, role, slots);
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
                op.setDate(LocalDate.parse(rs.getDate("date").toString()));
                op.setStartTime(LocalTime.parse(rs.getTime("starttime").toString()));
                op.setEndTime(LocalTime.parse(rs.getTime("endtime").toString()));
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
        } catch (SQLException e) {
            System.out.println(e);
        }
    } 
}
