/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import dbcon.DBConnection;
import java.time.LocalDate;

/**
 *
 * @author Harrison B
 */
public class Prescriptions {
    private int clientid;
    private int employeeid;
    private String drug_name;
    private String dosage;
    private Boolean is_repeat;
    private LocalDate date;
    
    public void setClientId(int clientid) {
        this.clientid = clientid;
    }
    
    public int getClient() {
        return this.clientid;
    }
    
    public void setEmployeeId(int employeeid) {
        this.employeeid = employeeid;
    }
    
    public int getEmployeeId() {
        return this.employeeid;
    }
    
    public void setDrugName(String drug_name) {
        this.drug_name = drug_name;
    }
    
    public String getDrugName() {
        return this.drug_name;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getDosage() {
        return this.dosage;
    }
    
    public void setIsRepeat(Boolean is_repeat) {
        this.is_repeat = is_repeat;
    }
    
    public Boolean getIsRepeat() {
        return this.is_repeat;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalDate getDate() {
        return this.date;
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
        
    }
}
