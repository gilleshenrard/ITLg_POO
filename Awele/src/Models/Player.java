package Models;
import Views.iSelectable;

import java.util.Objects;

public class Player {
    private int m_id;
    private String m_name;
    private iSelectable m_behaviour;

    /**
     * Creates a new Player
     * @param id Its ID
     * @param name Its name
     */
    public Player(int id, String name){
        this.m_id = id;
        this.m_name = name;
        this.m_behaviour = null;
    }

    /**
     * Sets the way the player selects a slot
     * @param behaviour Behaviour to adopt
     */
    public void setBehaviour(iSelectable behaviour) throws NullPointerException{
        if(behaviour == null)
            throw new NullPointerException("Cannot set a NULL behaviour");

        this.m_behaviour = behaviour;
    }

    /**
     * Returns the Player's ID
     * @return ID
     */
    public int getID() {
        return this.m_id;
    }

    /**
     * Returns the Player's name
     * @return Name
     */
    public String getName() {
        return this.m_name;
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
