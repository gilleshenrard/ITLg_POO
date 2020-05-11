/****************************************************************************************************/
/*  Class KeyboardSelect                                                                            */
/*  Implementation of the Strategy design pattern                                                   */
/*  Allows a player to select a slot via a keyboard in console mode                                 */
/*  Author : Gilles Henrard                                                                         */
/*  Last update : 11/05/2020                                                                        */
/****************************************************************************************************/
package Views;

import java.util.Scanner;

public class KeyboardSelect implements iSelectable {
    private Scanner m_scanner;

    /**
     * Create a new Keyboard slot selector
     */
    public KeyboardSelect(){
        this.m_scanner = new Scanner(System.in);
    }

    /**
     * Tell if the current behaviour is an AI one or not
     * @return false
     */
    @Override
    public boolean isAI() {
        return false;
    }

    /**
     * Prompt the user for a slot selection, and get it
     * @return Slot selected by the user
     */
    @Override
    public int selectSlot() {
        System.out.println("Select which slot to harvest :");

        return this.m_scanner.nextInt();
    }
}
