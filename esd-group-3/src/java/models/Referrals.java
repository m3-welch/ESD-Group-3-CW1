/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harrison B
 */
public class Referrals {
    private int clientid;
    private String name;
    private String address;
    
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void create(
        DBConnection dbcon,
        int clientid,
        String name,
        String address
    ) {
        String cid = String.valueOf(clientid); //Convert clientid to string for query
        String query = "INSERT INTO Referrals (clientid, name, address) VALUES"
                + "('" + cid + "', '" + name + "', '" + address + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.setClientId(clientid);
        this.setName(name);
        this.setAddress(address);
    }
}
