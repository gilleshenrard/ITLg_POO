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
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSQLite {
    private String m_path;
    private Connection m_connection;
    private Statement m_saveStatement;

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
        String saveQuery = "INSERT INTO " + this.m_path + "(startTime, duration, winner, seedsPlayer1, seedsPlayer2)" +
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
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Save the current game in the DB
     */
    public void saveGame() {

    }
}
