/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserverapplication;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 


/**
 *
 * @author muham
 */
public class DataBaseAccessLayer {

    private Connection connection;

    public DataBaseAccessLayer() {
        // Establish database connection
        try {
            String url = "jdbc:derby://localhost:1527/TicTacToeDB";
            String username = "root";
            String password = "root";
            Driver d = new org.apache.derby.jdbc.ClientDriver();
            DriverManager.registerDriver(d);
            connection = DriverManager.getConnection(url, username, password);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // Other methods for executing queries and interacting with the database
   
    //Implement CRUD(Create Insert - READ )
    
    public void insertPlayer(Player player){
    
 
        try {
    
      String insertQuery =  "INSERT INTO PLAYER (UserName, Password, Score , Status) VALUES(?,?,?,?)";
         PreparedStatement ps = connection.prepareStatement(insertQuery);
        
      ps.setString(1,player.getName());
      ps.setString(2,player.getPassword());
      ps.setInt(3,player.getScore());
      ps.setString(4,player.getStatus());
       
       ps.executeUpdate();
       System.out.println("test");
       ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
  
  
    }
    
    
    
 


    public void close() {
        // Close the database connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
 