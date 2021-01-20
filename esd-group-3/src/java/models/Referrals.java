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
    private int employeeid;
    private String name;
    private String address;
    
    private void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClientId() {
        return this.clientid;
    }
    
    private void setEmployeeId(int employeeid) {
        this.employeeid = employeeid;
    }
    
    public int getEmployeeId() {
        return this.employeeid;
    }
    
    private void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    private void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
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
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(this.getNameArr()));
        names.addAll(Arrays.asList(new_names));
        
        this.nameArr = names.toArray(this.nameArr);
    }
    
    private void addAddresses(String[] new_addresses) {
        ArrayList<String> addresses = new ArrayList<String>(Arrays.asList(this.getAddressArr()));
        addresses.addAll(Arrays.asList(new_addresses));
        
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
        int employeeid,
        int clientid,
        String name,
        String address
    ) {
        // String cid = String.valueOf(clientid); //Convert clientid to string for query
        String query = "INSERT INTO Referrals (employeeid, clientid, name, address) VALUES"
                + "(" + employeeid + ", " + clientid + ", '" + name + "', '" + address + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] nameArr = {name};
        String[] addressArr = {address};
        
        this.setEmployeeId(employeeid);
        this.setClientId(clientid);
        this.addNames(nameArr);
        this.addAddresses(addressArr);
    }
    
    public void retreiveAllForClient (
        DBConnection dbcon,
        int clientid
    ) {
        String cid = String.valueOf(clientid);
        String query = "SELECT name, address FROM Referrals WHERE clientid = "
                + cid;
        
        this.setClientId(clientid);
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
                
        this.setNameArr(nameArr);
        this.setAddressArr(addressArr);
    }
    
    public void retreiveAllForEmployee (
        DBConnection dbcon,
        int employeeid
    ) {
        String cid = String.valueOf(employeeid);
        String query = "SELECT name, address FROM Referrals WHERE employeeid = "
                + employeeid;
        
        this.setEmployeeId(employeeid);
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
                
        this.setNameArr(nameArr);
        this.setAddressArr(addressArr);
    }
    
    public List<Referrals> getAllFor(DBConnection dbcon, String id_type, int id) {
        String query = "SELECT * FROM Referrals WHERE " + id_type + " = "
                + id;
        
        List<Referrals> refs = new ArrayList<Referrals>();;
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Referrals ref = new Referrals();
                ref.setName(resultSet.getString("name"));
                ref.setAddress(resultSet.getString("address"));
                ref.setClientId(resultSet.getInt("clientid"));
                ref.setEmployeeId(resultSet.getInt("employeeid"));
                refs.add(ref);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return refs;
    }
}
