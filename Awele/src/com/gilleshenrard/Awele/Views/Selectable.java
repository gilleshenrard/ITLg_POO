/****************************************************************************************************/
/*  Abstract Class Selectable                                                                       */
/*  Defines the base of a Strategy design pattern to choose players' behaviours on the fly          */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 27/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Views;

import com.gilleshenrard.Awele.Controllers.BoardController;
import com.gilleshenrard.Awele.Models.Point;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class Selectable {
    private BoardController m_controller;
    private int m_id;
    private ArrayList<Integer> m_array;

    /**
     * Create a new Selectable (selection behaviour base class)
     * @param boardController Board controller to use
     */
    public Selectable(BoardController boardController){
        if(boardController == null)
            throw new NullPointerException("Selectable() : NULL instance of BoardController");

        this.m_controller = boardController;
        this.m_array = new ArrayList<>();
    }

    /**
     * Define whether the implementing class is an AI or not
     * @return true if AI, false otherwise
     */
    public abstract boolean isAI();

    /**
     * Implementations will select a slot to play on the board. Base class checks if legal slots are available
     * @return 0 if available, -2 otherwise
     */
    public int selectSlot(){
        if (this.getController() == null)
            throw new NullPointerException("Selectable.SelectSlot() : Board controller is not instantiated");

        //clear the array of legal slots
        this.m_array.clear();

        //fill the array with legal slots available
        Point p = new Point(0, this.m_id - 1);
        for(int i=0 ; i<6 ; i++) {
            p.setX(i);
            if (this.m_controller.isLegal(p))
                this.m_array.add(i);
        }

        //if no slots available, return code
        if (this.m_array.size() == 0)
            return -2;
        else
            return 0;
    }

    /**
     * Get the board controller used
     * @return Board controller
     */
    protected BoardController getController(){
        return this.m_controller;
    }

    /**
     * Set the ID of the player to which the behaviour is assigned
     * @param ID ID of the player
     * @throws InvalidParameterException
     */
    public void setID(int ID) throws InvalidParameterException{
        if (ID != 1 && ID != 2)
            throw new InvalidParameterException("Selectable.setID() : Invalid ID (" + ID + ")");

        this.m_id = ID;
    }

    /**
     * Get the ID of the player to which the behaviour is assigned
     * @return ID of the player
     */
    protected int getID(){
        return this.m_id;
    }

    /**
     * Get the array of legal slots
     * @return Array of legal slots
     */
    protected ArrayList<Integer> getLegal(){
        return this.m_array;
    }
}
