/****************************************************************************************************/
/*  Class DBSQLite                                                                                  */
/*  Allows SQLite DB manipulations (for now just addition)                                          */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 12/06/2020                                                                        */
/****************************************************************************************************/

package com.gilleshenrard.Awele.Models;

import com.gilleshenrard.Awele.App;

import java.sql.*;

import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBSQLite {
    private String m_path;
    private Connection m_connection;
    private PreparedStatement m_saveStatement;
    private PreparedStatement m_selectStatement;
    private ResultSet m_gamesResultSet;
    private boolean m_rowsremaining;

    /**
     * Create a new SQLite DB manipulation object
     * @param dBPath
     */
    public DBSQLite(String dBPath) {
        this.m_path = dBPath;
        this.m_connection = null;
        this.m_saveStatement = null;
        this.m_selectStatement = null;
        this.m_gamesResultSet = null;
        this.m_rowsremaining = false;
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
        //startTime, duration, winner, seedsPlayer1, seedsPlayer2
        String saveQuery = "INSERT INTO Game (" + DBFields.TIME.toString() + ", " + DBFields.CLOCK.toString() + ", " + DBFields.WINNER.toString() + ", " + DBFields.PLAYER1.toString() + ", " + DBFields.PLAYER2.toString() + ")" +
                            "VALUES (?,?,?,?,?);";
        this.m_saveStatement = this.m_connection.prepareStatement(saveQuery);
        Logger.getLogger(App.class.getName()).log(Level.FINE, "'Save game' prepared statement created");

        //create the "select scores" prepared statement
        String SelectQuery = "SELECT * FROM Game;";
        this.m_selectStatement = this.m_connection.prepareStatement(SelectQuery);
        Logger.getLogger(App.class.getName()).log(Level.FINE, "'Select scores' prepared statement created");
    }

    /**
     * Closte the current DB connection
     */
    public void close() {
        try {
            this.m_saveStatement.close();
            this.m_selectStatement.close();
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
    public void saveGame(int winner) {
        try {
            //format the game start time to a string 'yyyy-MM-dd HH:mm:ss'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = Game.getInstance().getTime().format(formatter);

            //fill up the prepared statement parameters
            //startTime, duration, winner, seedsPlayer1, seedsPlayer2
            this.m_saveStatement.setString(1, formatDateTime);
            this.m_saveStatement.setString(2, Game.getInstance().getClock().toString());
            this.m_saveStatement.setString(3, Game.getInstance().getName(winner));
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

    /**
     * Fill the local Result Set with all the games saved in the DB
     */
    public void selectGames() {
        try {
            this.m_gamesResultSet = this.m_selectStatement.executeQuery();
        }
        catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Select the next row in the database
     * @return true if row has been selected, false if no more rows
     */
    public boolean selectNext() {
        try {
            this.m_rowsremaining = this.m_gamesResultSet.next();
        }
        catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return this.m_rowsremaining;
    }

    /**
     * Get the value of the current database row at the column specified
     * @param column Column from which get the value
     * @return Value of the field located in the current database row and the column specified
     */
    public String getField(DBFields column) {
        if (this.m_rowsremaining) {
            try {
                switch (column) {
                    case TIME:
                    case CLOCK:
                    case WINNER:
                        return this.m_gamesResultSet.getString(column.toString());

                    case PLAYER1:
                    case PLAYER2:
                        return Integer.toString(this.m_gamesResultSet.getInt(column.toString()));

                    default:
                        throw new SQLException("Invalid database column name ('" + column.toString() + "')");
                }
            }
            catch (SQLException e) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        }

        return null;
    }
}
