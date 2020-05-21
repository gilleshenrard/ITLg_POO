/****************************************************************************************************/
/*  Class Player                                                                                    */
/*  Encloses the players manipulations                                                              */
/*  Each player has an ID, a name and a behaviour to select slots during the game                   */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package com.gilleshenrard.Awele.Models;

import com.gilleshenrard.Awele.Views.iSelectable;

import java.security.InvalidParameterException;
import java.util.Objects;

public class Player {
    private int m_id;
    private String m_name;
    private iSelectable m_behaviour;

    /**
     * Creates a new Player
     * @param id Its ID
     * @param name Its name
     * @throws InvalidParameterException
     */
    public Player(int id, String name) throws InvalidParameterException{
        this.setID(id);
        this.m_name = name;
        this.m_behaviour = null;
    }

    /**
     * Creates a new Player
     * @param id Its ID
     * @param name Its name
     * @param behaviour Behaviour of the player
     * @throws InvalidParameterException
     * @throws NullPointerException
     */
    public Player(int id, String name, iSelectable behaviour) throws InvalidParameterException, NullPointerException{
        this.setID(id);
        this.m_name = name;
        this.setBehaviour(behaviour);
    }

    /**
     * Set the ID of the player
     * @param m_id ID to give to the player
     * @throws InvalidParameterException
     */
    public void setID(int m_id) throws InvalidParameterException{
        if(m_id < 0)
            throw new InvalidParameterException("Player.setID() : negative ID (value : " + m_id + ")");

        this.m_id = m_id;
    }

    /**
     * Returns the Player's ID
     * @return ID
     */
    public int getID() {
        return this.m_id;
    }

    /**
     * Sets the way the player selects a slot
     * @param behaviour Behaviour to adopt
     * @throws NullPointerException
     */
    public void setBehaviour(iSelectable behaviour) throws NullPointerException{
        if(behaviour == null)
            throw new NullPointerException("Player.setBehaviour() : NULL instance of behaviour");

        this.m_behaviour = behaviour;
    }

    /**
     * Get the behaviour selected for the current Player
     * @return Behaviour
     */
    public iSelectable getBehaviour() {
        return this.m_behaviour;
    }

    /**
     * Tell if the player is an AI or not (instance of RandomSelect)
     * @return true if RandomSelect, false otherwise
     * @throws InvalidParameterException
     */
    public boolean isPlayerAI() throws NullPointerException {
        if (this.getBehaviour() == null)
            throw new NullPointerException("Player.isPlayerAI() : NULL instance of iSelectable");

        return this.getBehaviour().isAI();
    }

    /**
     * Returns the Player's name
     * @return Name
     */
    public String getName() {
        return this.m_name;
    }

    /**
     * Select the slot to harvest
     * @return Slot to harvest
     * @throws NullPointerException
     */
    public int selectSlot() throws NullPointerException{
        if (this.m_behaviour == null)
            throw new NullPointerException("Player.selectSlot() : NULL instance of behaviour");

        return this.getBehaviour().selectSlot();
    }

    /**
     * Tells if o is equal to the current Player
     * @param o Object to test
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return m_id == player.m_id &&
                m_name.equals(player.m_name) &&
                Objects.equals(m_behaviour, player.m_behaviour);
    }

    /**
     * Returns the hashcode of the current Player
     * @return Hashcode of the current Player
     */
    @Override
    public int hashCode() {
        return Objects.hash(m_id, m_name, m_behaviour);
    }
}
