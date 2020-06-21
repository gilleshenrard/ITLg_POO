/****************************************************************************************************/
/*  Class DBScores                                                                                  */
/*  built solely to ease up TableView manipulations                                                 */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 21/06/2020                                                                        */
/****************************************************************************************************/

package com.gilleshenrard.Awele.Views.JFX;

import javafx.beans.property.SimpleStringProperty;

public class DBScores {
    private SimpleStringProperty m_time;
    private SimpleStringProperty m_clock;
    private SimpleStringProperty m_winner;
    private SimpleStringProperty m_pl1;
    private SimpleStringProperty m_pl2;

    /**
     * Create a new DBScores (Dataview linking values from the DB and StringProperties)
     * @param time Date and time of the beginning of a Game
     * @param clock Duration of a Game
     * @param winner Name of the winner
     * @param pl1 Amount of seeds stored by the player 1
     * @param pl2 Amount of seeds stored by the player 2
     */
    DBScores(String time, String clock, String winner, String pl1, String pl2) {
        this.m_time = new SimpleStringProperty(time);
        this.m_clock = new SimpleStringProperty(clock);
        this.m_winner = new SimpleStringProperty(winner);
        this.m_pl1 = new SimpleStringProperty(pl1);
        this.m_pl2 = new SimpleStringProperty(pl2);
    }

    /**
     * Get the Date and Time of the beginning of a game
     * @return Date and Time
     */
    public SimpleStringProperty timeProperty() {
        return this.m_time;
    }

    /**
     * Get the duration of a Game
     * @return Elapsed time
     */
    public SimpleStringProperty clockProperty() {
        return this.m_clock;
    }

    /**
     * Get the Name of the winner
     * @return Name
     */
    public SimpleStringProperty winnerProperty() {
        return this.m_winner;
    }

    /**
     * Get the Amount of seeds stored by the player 1
     * @return Amount
     */
    public SimpleStringProperty player1Property() {
        return this.m_pl1;
    }

    /**
     * Get the Amount of seeds stored by the player 2
     * @return Amount
     */
    public SimpleStringProperty player2Property() {
        return this.m_pl2;
    }

    /**
     * Get a string with all the DB fields
     * @return String representing a row
     */
    @Override
    public String toString() {
        String tmp = this.timeProperty().get() + "\n" +
                    this.clockProperty().get() + "\n" +
                    this.winnerProperty().get() + "\n" +
                    this.player1Property().get() + "\n" +
                    this.player2Property().get();

        return tmp;
    }
}
