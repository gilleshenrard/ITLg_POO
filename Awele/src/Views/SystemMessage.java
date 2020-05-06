package Views;

import Controllers.GameController;

public abstract class SystemMessage {

    /**
     * Create a new SystemMessage
     * @param controller Game controller to use
     * @throws NullPointerException
     */
    public SystemMessage(GameController controller) throws NullPointerException{
        if (controller == null)
            throw new NullPointerException("GameView() : NULL instance of GameController");

        controller.setView(this);
    }

    public abstract void displayMessage(String msg);
    public abstract void displayWarning(String msg);
    public abstract void displayError(String msg);
    public abstract void displaySeparator();
}
