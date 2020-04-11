package Models;

import Controllers.iSelectable;
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
    public void setID(int m_id) {
        if(m_id < 0)
            throw new InvalidParameterException("ID must not be negative");

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
            throw new NullPointerException("Cannot set a NULL behaviour");

        this.m_behaviour = behaviour;
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
            throw new NullPointerException("Behaviour must be instantiated first (cannot be null)");

        return this.m_behaviour.selectSlot();
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
