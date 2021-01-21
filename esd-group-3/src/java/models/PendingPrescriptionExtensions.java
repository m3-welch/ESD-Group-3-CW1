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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morgan
 */
public class PendingPrescriptionExtensions {
    private int id;
    private int prescriptionid;
    private LocalDate newEndDate;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPrescriptionId() {
        return this.prescriptionid;
    }
    
    public void setPrescriptionId(int prescriptionid) {
        this.prescriptionid = prescriptionid;
    }
    
    public LocalDate getNewEndDate() {
        return this.newEndDate;
    }
    
    public void setNewEndDate(LocalDate newEndDate) {
        this.newEndDate = newEndDate;
    }
    
    public void requestRepeatPrescriptionExtension (
            DBConnection dbcon,
            int prescriptionid,
            LocalDate newEndDate
    ) {
        String query = "INSERT INTO PendingPrescriptionExtensions (prescriptionid, newenddate) VALUES (" + prescriptionid + ", '" + newEndDate + "')";
             
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<PendingPrescriptionExtensions> getAll(DBConnection dbcon) {
        String query = "SELECT * FROM PendingPrescriptionExtensions";
                
        List<PendingPrescriptionExtensions> ppes = new ArrayList<PendingPrescriptionExtensions>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                PendingPrescriptionExtensions ppe = new PendingPrescriptionExtensions();
                ppe.setId(resultSet.getInt("id"));
                ppe.setPrescriptionId(resultSet.getInt("prescriptionid"));
                ppe.setNewEndDate(LocalDate.parse(resultSet.getDate("newEndDate").toString()));
                ppes.add(ppe);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return ppes;
    }
    
    public void approve(DBConnection dbcon, int approvalid) {
        PendingPrescriptionExtensions ppe = this.getPendingPrescriptionExtensionById(dbcon, approvalid);

        String query = "UPDATE Prescriptions SET date_end = '" + ppe.getNewEndDate() + "' WHERE id = " + ppe.getPrescriptionId();
             
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        query = "DELETE FROM PendingPrescriptionExtensions WHERE prescriptionid = " + ppe.getPrescriptionId();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deny(DBConnection dbcon, int approvalid) {
        PendingPrescriptionExtensions ppe = this.getPendingPrescriptionExtensionById(dbcon, approvalid);
        String query = "DELETE FROM PendingPrescriptionExtensions WHERE prescriptionid = " + ppe.getPrescriptionId();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PendingPrescriptionExtensions getPendingPrescriptionExtensionById(DBConnection dbcon, int approvalid) {
        String query = "SELECT * FROM PendingPrescriptionExtensions WHERE id = " + approvalid;
                        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                this.setId(resultSet.getInt("id"));
                this.setPrescriptionId(resultSet.getInt("prescriptionid"));
                this.setNewEndDate(LocalDate.parse(resultSet.getDate("newEndDate").toString()));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this;
    }
}
