package models;

import java.math.BigInteger;
import java.security.MessageDigest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author conranpearce
 */
public class HashPassword {
    // Hashing of a password using SHA-1, from source http://oliviertech.com/java/generate-SHA1-hash-from-a-String/
    public String setHashPassword(String password) {
        String sha1Pwd = "";

        // Using java libraries
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("utf8"));
            sha1Pwd = String.format("%040x", new BigInteger(1,digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return sha1Pwd;
    }
}
