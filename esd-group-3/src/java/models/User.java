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
    int id;
    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    String address;
    String role;
    
    public User setId(int id) {
        this.id = id;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public User setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }
    
    public String getFirstname() {
        return this.firstname;
    }
    
    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    
    public String getLastname() {
        return this.lastname;
    }
    
    public User setEmail(String email) {
        this.email = email;
        return this;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public User setAddress(String address) {
        this.address = address;
        return this;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public User setRole(String role) {
        this.role = role;
        return this;
    }
    
    public String getRole() {
        return this.role;
    }
}
