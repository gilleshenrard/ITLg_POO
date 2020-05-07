package Controllers;

public interface iObserver {
    void update();
    Object getContent();
    void setController(BoardController controller);
}
