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
