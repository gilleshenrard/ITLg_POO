/****************************************************************************************************/
/*  Class DBSQLite                                                                                  */
/*  Allows SQLite DB manipulations (for now just addition)                                          */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 12/06/2020                                                                        */
/****************************************************************************************************/

package com.gilleshenrard.Awele.Models;

import com.gilleshenrard.Awele.App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSQLite {
    private String m_path;
    private Connection m_connection;
    private PreparedStatement m_saveStatement;

    /**
     * Create a new SQLite DB manipulation object
     * @param dBPath
     */
    public DBSQLite(String dBPath) {
        this.m_path = dBPath;
        this.m_connection = null;
        this.m_saveStatement = null;
    }

    /**
     * Negociate the connection with the database
     */
    public void connect() {
        try {
            //get the connection to the SQLite database
            Class.forName("org.sqlite.JDBC");
            this.m_connection = DriverManager.getConnection("jdbc:sqlite:" + this.m_path);
            Logger.getLogger(App.class.getName()).log(Level.FINE, "Database " + this.m_path + " successfully connected");

            this.setupStatements();
        }
        catch (ClassNotFoundException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Create the prepared statements
     * @throws SQLException
     */
    private void setupStatements() throws SQLException {
        //create the "save game" prepared statement
        String saveQuery = "INSERT INTO Game (startTime, duration, winner, seedsPlayer1, seedsPlayer2)" +
                            "VALUES (?,?,?,?,?);";
        this.m_saveStatement = this.m_connection.prepareStatement(saveQuery);
        Logger.getLogger(App.class.getName()).log(Level.FINE, "'Save game' prepared statement created");
    }

    /**
     * Closte the current DB connection
     */
    public void close() {
        try {
            this.m_saveStatement.close();
            this.m_connection.close();
            Logger.getLogger(App.class.getName()).log(Level.FINE, "Database closed");
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Save the current game in the DB
     * @param winner Name of the winner
     */
    public void saveGame(String winner) {
        try {
            //fill up the prepared statement parameters
            //startTime, duration, winner, seedsPlayer1, seedsPlayer2
            this.m_saveStatement.setString(1, "0");
            this.m_saveStatement.setInt(2, 0);
            this.m_saveStatement.setString(3, winner);
            this.m_saveStatement.setInt(4, Game.getInstance().getBoard().getStoredSeeds(1));
            this.m_saveStatement.setInt(5, Game.getInstance().getBoard().getStoredSeeds(2));

            //execute the query on the DB
            this.m_saveStatement.execute();
            Logger.getLogger(App.class.getName()).log(Level.INFO, "Current game saved in the DB");
        }
        catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
