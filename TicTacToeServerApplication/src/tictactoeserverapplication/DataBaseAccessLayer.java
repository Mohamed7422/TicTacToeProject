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

    //Insert A list of players
    public boolean insertPlayers(List<Player> players) {
        try {
            String insertQuery = "INSERT INTO PLAYER (USERNAME, PASSWORD, SCORE , STATUS) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertQuery);

            for (Player player : players) {
                ps.setString(1, player.getName());
                ps.setString(2, player.getPassword());
                ps.setInt(3, player.getScore());
                ps.setString(4, player.getStatus());
                ps.addBatch();
            }

            ps.executeBatch();
            ps.close();
            return true;
        } catch (SQLException ex) {
            //Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
            //System.out.println("Result set : "+resultSet.getString("USERNAME"));

        } catch (SQLException ex) {
            System.out.println("Not Found!!");
            Logger
                    .getLogger(DataBaseAccessLayer.class
                            .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    //get all online players
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
            System.out.println("get players from dao:" + onlinePlayers.size());
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return onlinePlayers;

    }

    //return a specific player by his username
    /* public Player getPlayerByUserName(String username) {
        Player player = null;
        String query = "SELECT * FROM PLAYER WHERE USERNAME = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                int score = rs.getInt("SCORE");
                String status = rs.getString("STATUS");

                player = new Player(name, password, score, status);
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return player;
    } */
    //Update player status
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
