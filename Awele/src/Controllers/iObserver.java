package Controllers;

public interface iObserver {
    void update();
    void setController(Object controller);
    void init();
}
