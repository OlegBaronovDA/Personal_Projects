package nl.rug.oop.rts.menuMVC.model.army;

/**
 * Enumeration representing teams in the game.
 */
public enum Team {
    GOOD(1),
    EVIL(-1);

    private final int VALUE;

    /**
     * Constructor for setting the value of the team.
     * @param value The value associated with the team.
     */
    Team(int value) {
        this.VALUE = value;
    }

    /**
     * Getter for retrieving the value associated with the team.
     * @return The value associated with the team.
     */
    public int getValue() {
        return VALUE;
    }
}