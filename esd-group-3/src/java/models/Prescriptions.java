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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Harrison B
 */
public class Prescriptions {
    private int clientid;
    private int prescription_count = 0;
    private int[] employeeid = new int[1];
    private String[] drug_name = new String[1];
    private String[] dosage = new String[1];
    private Boolean[] is_repeat = new Boolean[1];
    private LocalDate[] date_start = new LocalDate[1];
    private LocalDate[] date_end = new LocalDate[1];
    
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
    
    private void addPrescription(
        int employeeid,
        String drug_name,
        String dosage,
        Boolean is_repeat,
        LocalDate date_start,
        LocalDate date_end
    ) {
        // Increase prescription amount
        this.increasePrescriptionCount();
        int new_pres_count = this.getPrescriptionCount();
        
        // Get old data, insert into array of size new_pres_count
        String[] drugs = Arrays.copyOf(this.getDrugName(), new_pres_count);
        String[] doses = Arrays.copyOf(this.getDosage(), new_pres_count);
        Boolean[] repeats = Arrays.copyOf(this.getIsRepeat(), new_pres_count);
        LocalDate[] starts = Arrays.copyOf(this.getDateStart(), new_pres_count);
        LocalDate[] ends = Arrays.copyOf(this.getDateEnd(), new_pres_count);
        
        // Attach new data
        drugs[new_pres_count - 1] = drug_name;
        doses[new_pres_count - 1] = dosage;
        repeats[new_pres_count - 1] = is_repeat;
        starts[new_pres_count - 1] = date_start;
        ends[new_pres_count - 1] = date_end;
        
        // Assign to attributes using setters
        this.setDrugName(drugs);
        this.setDosage(doses);
        this.setIsRepeat(repeats);
        this.setDateStart(starts);
        this.setDateEnd(ends);
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
    
    public void retreivePrescriptions(
            DBConnection dbcon,
            int clientid
    ) {
        String query = "SELECT employeeid, drug_name, dosage, is_repeat, "
                + "date_start, date_end FROM Prescriptions WHERE clientid = "
                + clientid;
        
        //Set up lists to record data
        List<Integer> employeeList = new ArrayList<Integer>();
        List<String> drugList = new ArrayList<String>();
        List<String> doseList = new ArrayList<String>();
        List<Boolean> repeatList = new ArrayList<Boolean>();
        List<String> startList = new ArrayList<String>();
        List<String> endList = new ArrayList<String>();
        
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                employeeList.add(resultSet.getInt("employeeid"));
                drugList.add(resultSet.getString("drug_name"));
                doseList.add(resultSet.getString("dosage"));
                repeatList.add(resultSet.getBoolean("is_repeat"));
                startList.add(resultSet.getString("date_start"));
                endList.add(resultSet.getString("date_end"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Convert lists to arrays
        int list_len = employeeList.size();
        LocalDate[] startArr = new LocalDate[list_len];
        LocalDate[] endArr = new LocalDate[list_len];
        int[] employeeArr = new int[list_len];
        // Convert date formats and integer list
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < list_len; i++) {
            startArr[i] = LocalDate.parse(startList.get(i), formatter);
            endArr[i] = LocalDate.parse(endList.get(i), formatter);
            employeeArr[i] = employeeList.get(i).intValue();
        }
        
        String drugArr[] = new String[list_len];
        drugArr = drugList.toArray(drugArr);
        String dosageArr[] = new String[list_len];
        dosageArr = doseList.toArray(dosageArr);
        Boolean repeatArr[] = new Boolean[list_len];
        repeatArr = repeatList.toArray(repeatArr);
        
        this.setClientId(clientid);
        this.setEmployeeId(employeeArr);
        this.setDrugName(drugArr);
        this.setDosage(dosageArr);
        this.setIsRepeat(repeatArr);
        this.setDateStart(startArr);
        this.setDateEnd(endArr);
    }
    
    public void renewRepeatPrescriptions (
            DBConnection dbcon,
            int clientid,
            String drug_name
    ) {
        String query = "SELECT is_repeat, date_end FROM Prescriptions WHERE "
                + "clientid = " + clientid + " AND drug_name = '" + drug_name + "'";
        
        Boolean repeatable = false;
        String date_str = "";
        
        // ASSUMING THERE IS 1 DRUG PERSRIPTION PER PERSON OF THE SAME NAME
        try (Statement stmt = dbcon.conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            if (!resultSet.next()) {
                System.out.println("No results found");
                return;
            }
            else {
                repeatable = resultSet.getBoolean("is_repeat");
                date_str = resultSet.getString("date_end");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Change date format
        LocalDate date_end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date_end = LocalDate.parse(date_str, formatter);
        // Add 1 month
        if (repeatable) {
            date_end = date_end.plusMonths(1);
        }
        else {
            System.out.println("Not a repeat prescription");
            return;  // Exit if not repeatable
        }
        
        // Setup query to update
        query = "UPDATE Prescriptions SET date_end = '" + date_end +"' WHERE "
                + "drug_name = '" + drug_name + "' AND clientid = " + clientid;
        try (Statement stmt = dbcon.conn.createStatement()) {
            int resultSet = stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        // Get index of drug location in their drug list
        int index = -1;
        String[] drug_array = this.getDrugName();
        for (int i = 0; i < drug_array.length; i++) {
            if (drug_name.equals(drug_array[i])) {
                index = i;
            }
        }
        
        // Update end date in class
        LocalDate[] dates = this.getDateEnd();
        dates[index] = date_end;
        this.setDateEnd(dates);
    }
}
