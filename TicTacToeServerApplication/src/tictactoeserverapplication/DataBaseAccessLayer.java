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
import java.util.ArrayList;
import java.util.List;
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
            //String url = "jdbc:derby://localhost:1527/PlayerList";
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
    public boolean insertPlayer(Player player) {

        try {

            String insertQuery = "INSERT INTO PLAYER (UserName, Password) VALUES(?,?)";
            PreparedStatement ps = connection.prepareStatement(insertQuery);

            ps.setString(1, player.getName());
            ps.setString(2, player.getPassword());
            ps.executeUpdate();
            System.out.println("test");
            ps.close();
            return true;

        } catch (SQLException ex) {
            //Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
   

    
    public boolean insertGame(String PlayerName,String OpponentName,String PlayerSymb,String OpponentSymb,String DateTime,String WinningSymb,boolean IsRecorded) {
        try {
            String insertQuery = "INSERT INTO GAME (PLAYERNAME, OPPONENTNAME, PLAYERSYMB , OPENNETSYMB,DATETIME,WINNINGSYMB,ISRECORDED) VALUES (?, ?, ?, ?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(insertQuery);

            
                ps.setString(1, PlayerName);
                ps.setString(2, OpponentName);
                ps.setString(3, PlayerSymb);
                ps.setString(4, OpponentSymb);
                ps.setString(5, DateTime);
                ps.setString(6, WinningSymb);
                ps.setBoolean(7, IsRecorded);
                ps.addBatch();
            

            ps.executeBatch();
            ps.close();
            return true;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean updatePlayerScore(String username, int newScore) {
        try {
            int score=selectPlayerScore(username);
            if(score!=-1){
            
            score+=newScore;
            String query = "UPDATE PLAYER SET SCORE = ? WHERE USERNAME = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, score);
            ps.setString(2, username);
            ps.executeUpdate();
            ps.close();
            return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int selectPlayerScore(String username) {
        try {
           String query = "SELECT SCORE FROM PLAYER WHERE USERNAME = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("SCORE");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return -1;

        }
    }
    
    

    public boolean signIn(String name, String pass) {
        System.out.println(name + ":" + pass);
        try {

            String insertQuery = "SELECT * FROM PLAYER WHERE USERNAME = ? AND PASSWORD = ?";
            PreparedStatement ps = connection.prepareStatement(insertQuery);

            ps.setString(1, name);
            ps.setString(2, pass);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                ps.close();
                return true;
            } else {
                ps.close();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Not Found!!");
            Logger
                    .getLogger(DataBaseAccessLayer.class
                            .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    public List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        try {
            String query = "SELECT * FROM PLAYER WHERE STATUS = 'Online'";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Player player = new Player(rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getInt("SCORE"),
                        rs.getString("STATUS"));
                onlinePlayers.add(player);

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onlinePlayers;

    }

    public boolean updatePlayerStatus(String username, String newStatus) {
        try {
            String query = "UPDATE PLAYER SET STATUS = ? WHERE USERNAME = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newStatus);
            ps.setString(2, username);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getToken(String username) {
        try {
            String query = "SELECT TOKEN FROM PLAYER WHERE USERNAME = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TOKEN");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return -1;

        }
    }
    
    public String getUsername(int token) {
        try {
            String query = "SELECT USERNAME FROM PLAYER WHERE TOKEN = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("USERNAME");
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return null;

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
