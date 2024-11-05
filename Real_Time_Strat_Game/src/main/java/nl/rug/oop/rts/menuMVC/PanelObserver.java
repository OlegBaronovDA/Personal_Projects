package nl.rug.oop.rts.menuMVC;

/**
 * The PanelObserver interface should be implemented by any class that needs to be notified
 * about changes in the state of the GraphPanel.
 */
public interface PanelObserver {
    /**
     * This method is called whenever the observed object is changed.
     */
    void update();
}


