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
    private Statement m_statement;

    /**
     * Create a new SQLite DB manipulation object
     * @param dBPath
     */
    public DBSQLite(String dBPath) {
        this.m_path = dBPath;
        this.m_connection = null;
        this.m_statement = null;
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

            //create the prepared statement manager
            this.m_statement = this.m_connection.createStatement();
            Logger.getLogger(App.class.getName()).log(Level.FINE, "Prepared statement manager successfully created");
        }
        catch (ClassNotFoundException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
        catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Closte the current DB connection
     */
    public void close() {
        try {
            this.m_statement.close();
            this.m_connection.close();
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }
}
