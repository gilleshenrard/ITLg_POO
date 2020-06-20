package com.gilleshenrard.Awele.Models;

public enum DBFields {
    TIME("startTime"),
    CLOCK("duration"),
    WINNER("winner"),
    PLAYER1("seedsPlayer1"),
    PLAYER2("seedsPlayer2");

    private String m_fieldname;

    /**
     * Initialise a Database field with its column name in the DB
     * @param field Column name in the DB
     */
    DBFields(String field){
        this.m_fieldname = field;
    }

    @Override
    public String toString() {
        return this.m_fieldname;
    }
}
