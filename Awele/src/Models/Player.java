package Models;
import Views.iSelectable;

public class Player {
    private byte m_id;
    private String m_name;
    private iSelectable m_behaviour;

    /**
     * Creates a new Player
     * @param id Its ID
     * @param name Its name
     */
    public Player(byte id, String name){
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
    public byte getID() {
        return this.m_id;
    }

    /**
     * Returns the Player's name
     * @return Name
     */
    public String getName() {
        return this.m_name;
    }
}
