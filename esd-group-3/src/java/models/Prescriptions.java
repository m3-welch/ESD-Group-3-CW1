/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harrison B
 */
public class Prescriptions {
    private int clientid;
    private int prescription_count = 0;
    private int[] employeeid;
    private String[] drug_name;
    private String[] dosage;
    private Boolean[] is_repeat;
    private LocalDate[] date_start;
    private LocalDate[] date_end;
    
    private void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClient() {
        return this.clientid;
    }
    
    private void setPrescriptionCount(int prescription_count) {
        this.prescription_count = prescription_count;
    }
    
    private void increasePrescriptionCount() {
        this.prescription_count++;
    }
    
    private void decreasePrescriptionCount() {
        this.prescription_count--;
    }
    
    public int getPrescriptionCount() {
        return this.prescription_count;
    }
    
    private void setEmployeeId(int[] employeeid) {
        this.employeeid = employeeid;
    }
    
    public int[] getEmployeeId() {
        return this.employeeid;
    }
    
    private void setDrugName(String[] drug_name) {
        this.drug_name = drug_name;
    }
    
    public String[] getDrugName() {
        return this.drug_name;
    }
    
    private void setDosage(String[] dosage) {
        this.dosage = dosage;
    }
    
    public String[] getDosage() {
        return this.dosage;
    }
    
    private void setIsRepeat(Boolean[] is_repeat) {
        this.is_repeat = is_repeat;
    }
    
    public Boolean[] getIsRepeat() {
        return this.is_repeat;
    }
    
    private void setDateStart(LocalDate[] date_start) {
        this.date_start = date_start;
    }
    
    public LocalDate[] getDateStart() {
        return this.date_start;
    }
    
    private void setDateEnd(LocalDate[] date_end) {
        this.date_end = date_end;
    }
    
    public LocalDate[] getDateEnd() {
        return this.date_end;
    }
    
    public void addPrescription(
        int employeeid,
        String drug_name,
        String dosage,
        Boolean is_repeat,
        LocalDate date_start,
        LocalDate date_end
    ) {
        this.increasePrescriptionCount();
        int new_pres_count = this.getPrescriptionCount();
        
        //
        String[] drugs = new String[new_pres_count];
        drugs = this.getDrugName();
        drugs[new_pres_count-1] = drug_name;
        this.setDrugName(drugs);
        
        String[] old_dosage = this.getDosage();
    }
    
    public void create(
        DBConnection dbcon,
        int clientid,
        int employeeid,
        String drug_name,
        String dosage,
        Boolean is_repeat,
        LocalDate date_start,
        LocalDate date_end
    ) {
        String query = "INSERT INTO Prescriptions (clientid, employeeid, "
                + "drug_name, dosage, is_repeat, date_start, date_end) VALUES"
                + "(" + clientid + ", " + employeeid + ", '" + drug_name + "', "
                + "'" + dosage + "', " + is_repeat + ", '" + date_start + "', "
                + "'" + date_end + "')";
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.addPrescription(employeeid, drug_name, dosage, is_repeat, date_start, date_end);
    }
}
