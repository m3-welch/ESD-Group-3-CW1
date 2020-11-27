/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author morgan
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String role;
    
    public User setId(int id) {
        this.id = id;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return this.role;
    }
}
