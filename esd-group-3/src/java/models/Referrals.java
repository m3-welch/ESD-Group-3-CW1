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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harrison B
 */
public class Referrals {
    private int clientid;
    private String[] nameArr = new String[0];
    private String[] addressArr = new String[0];
    
    private void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    private void setNameArr(String[] name) {
        this.nameArr = name;
    }
    
    public String[] getNameArr() {
        return this.nameArr;
    }
    
    private void setAddressArr(String[] address) {
        this.addressArr = address;
    }
    
    public String[] getAddressArr() {
        return this.addressArr;
    }
    
    private void addNames(String[] new_names) {
        List<String> names = Arrays.asList(this.getNameArr());
        Collections.addAll(names, new_names);
        
        this.nameArr = names.toArray(this.nameArr);
    }
    
    private void addAddresses(String[] new_addresses) {
        List<String> addresses = Arrays.asList(this.getAddressArr());
        Collections.addAll(addresses, new_addresses);
        
        this.addressArr = addresses.toArray(this.addressArr);
    }
    
    private int CountResultSetSize(ResultSet rs) {
        int size = 1;
        try {
            if (rs.next() == true) {
                size++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Referrals.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return size;
    }
    
    public void create(
        DBConnection dbcon,
        int clientid,
        String name,
        String address
    ) {
        // String cid = String.valueOf(clientid); //Convert clientid to string for query
        String query = "INSERT INTO Referrals (clientid, name, address) VALUES"
                + "(" + clientid + ", '" + name + "', '" + address + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] nameArr = {name};
        String[] addressArr = {address};
        
        this.setClientId(clientid);
        this.addNames(nameArr);
        this.addAddresses(addressArr);
    }
    
    public void retreiveAll (
        DBConnection dbcon,
        int clientid
    ) {
        String cid = String.valueOf(clientid);
        String query = "SELECT name, address FROM Referrals WHERE clientid = "
                + cid;
        
        List<String> nameList = new ArrayList<String>();
        List<String> addressList = new ArrayList<String>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                nameList.add(resultSet.getString("name"));
                addressList.add(resultSet.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        String nameArr[] = new String[nameList.size()];
        nameArr = nameList.toArray(nameArr);
        String addressArr[] = new String[addressList.size()];
        addressArr = addressList.toArray(addressArr);
        
        System.out.println(Arrays.toString(nameArr));
        System.out.println(Arrays.toString(addressArr));
        
        this.setNameArr(nameArr);
        this.setAddressArr(addressArr);
    }
}
