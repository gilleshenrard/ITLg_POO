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
     * Prompt the user for a slot selection, and get it
     * @return Slot selected by the user
     */
    @Override
    public int selectSlot() {
        System.out.println("Select which slot to harvest :");

        return this.m_scanner.nextInt();
    }

    /**
     * Do nothing
     */
    @Override
    public void refresh() {}
}
