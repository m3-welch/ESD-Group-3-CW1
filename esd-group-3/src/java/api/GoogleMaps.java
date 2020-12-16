/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
/**
 *
 * @author morgan
 */
public class GoogleMaps {
    public String lookupAddress(String address) {
        HttpURLConnection connection = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            StringBuilder xmlStringBuilder = new StringBuilder();
            Document doc = builder.parse(new java.io.File("src/java/api/api_keys.xml"));
            
            Element root = doc.getDocumentElement();
            
            String maps_api_key = root.getElementsByTagName("maps").item(0).getTextContent().trim();
            
            //Create connection
            String url_string = "https://maps.googleapis.com/maps/api/place/findplacefromtext/xml?key=" + maps_api_key + "&inputtype=textquery&fields=formatted_address&input=" + URLEncoder.encode(address, "UTF-8"); 
            URL url = new URL(url_string);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream());
            wr.writeBytes(address);
            wr.close();

            //Get Response  
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                line = line.trim();
                response.append(line);
            }
            rd.close();
            
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            
            xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(response);
            ByteArrayInputStream input = new ByteArrayInputStream(
               xmlStringBuilder.toString().getBytes("UTF-8")
            );
            doc = builder.parse(input);
            
            root = doc.getDocumentElement();

            String formatted_address = root.getElementsByTagName("formatted_address").item(0).getTextContent();

            return formatted_address;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
