package com.gilleshenrard.Awele;

/**
 * Launcher class with a wrapper main method to trick java into thinking this is not a JavaFX application
 */
public class Launcher {
    /**
     * Wrapper main calling the class extending Application
     * @param args Program arguments
     */
    public static void main(String[] args) {
        com.gilleshenrard.Awele.App.main(args);
    }
}
