package nl.rug.oop.rts.menuMVC.model.army;

import nl.rug.oop.rts.menuMVC.model.graph.Edge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an army consisting of units from a similar team and faction.
 */
public class Army {
    private List<Unit> units; // The units in the army
    private Faction faction;
    private Team team;
    private boolean hasMoved = false; // Flag indicating if the army has moved
    private Edge destination; // The destination edge for the army

    /**
     * Constructs an army with the given faction.
     * @param faction The faction of the army.
     */
    public Army(Faction faction) {
        this.faction = faction;
        this.team = determineTeam(faction); // Determine the team based on the faction
        this.units = new ArrayList<>();
        addRandomUnits(); // Allocate a random number of units initially (1-3 units)
    }

    /**
     * Determines the team based on the given faction.
     * @param faction The faction of the army.
     * @return The team of the army (GOOD or EVIL).
     */
    private Team determineTeam(Faction faction) {
        return switch (faction) {
            case MEN, ELVES, DWARVES -> Team.GOOD;
            case MORDOR, ISENGARD, PUTIN -> Team.EVIL;
        };
    }

    /**
     * Gets the total health of the army by summing up the health of all units.
     * @return The total health of the army.
     */
    public int getArmyHealth(){
        int health = 0;
        for(Unit unit : units){
            health += unit.getHealth();
        }
        return health;
    }

    /**
     * Sets the destination edge for the army.
     * @param edge The destination edge.
     */
    public void setDestination(Edge edge){
        this.destination = edge;
    }

    /**
     * Gets the destination edge for the army.
     * @return The destination edge.
     */
    public Edge getDestination(){
        return this.destination;
    }

    /**
     * Adds a random number of units to the army.
     */
    public void addRandomUnits() {
        Random random = new Random();
        int numberOfUnits = random.nextInt(3) + 1; // Random number between 1 and 3

        for (int i = 0; i < numberOfUnits; i++) {
            String unitName = getRandomUnitName(faction);
            int damage = 10 + random.nextInt(11); // Random damage between 10 and 20
            int health = 50 + random.nextInt(51); // Random health between 50 and 100
            Unit unit = new Unit(unitName, damage, health);
            units.add(unit);
        }
    }

    /**
     * Adds a random number of units to the army for a specific event.
     */
    public void addRandomUnitsEvent() {
        Random random = new Random();
        int numberOfUnits = random.nextInt(3) + 1; // Random number between 1 and 3

        for (int i = 0; i < numberOfUnits; i++) {
            String unitName = getRandomUnitName(faction);
            int damage = 10 + random.nextInt(11); // Random damage between 10 and 20
            int health = 50 + random.nextInt(51); // Random health between 50 and 100
            Unit unit = new Unit(unitName, damage, health);
            units.add(unit);
        }
    }

    /**
     * Gets a random unit name for the given faction.
     * @param faction The faction of the army.
     * @return A random unit name.
     */
    private static String getRandomUnitName(Faction faction) {
        // Get all unit types for the specified faction
        UnitType[] units = UnitType.values();
        List<UnitType> factionUnits = new ArrayList<>();

        // Filter unit types by faction
        for (UnitType unit : units) {
            if (unit.getFaction() == faction) {
                factionUnits.add(unit);
            }
        }

        // Select a random unit type and return its name
        Random random = new Random();
        UnitType randomUnit = factionUnits.get(random.nextInt(factionUnits.size()));
        return randomUnit.name();
    }

    /**
     * Adds a unit to the army.
     * @param unit The unit to add.
     */
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * Removes a unit from the army.
     * @param unit The unit to remove.
     */
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    /**
     * Gets the list of units in the army.
     * @return The list of units.
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Gets the faction of the army.
     * @return The faction of the army.
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * Gets the team of the army.
     * @return The team of the army.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Converts the army to a string representation.
     * @return The string representation of the army.
     */
    public String toArmyString() {
        return faction.name();
    }

    /**
     * Checks if the army has moved.
     * @return True if the army has moved, false otherwise.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Sets whether the army has moved.
     * @param moved True if the army has moved, false otherwise.
     */
    public void setMoved(boolean moved) {
        hasMoved = moved;
    }

    /**
     * Gets the name of the army.
     * @return The name of the army.
     */
    public String getName(){
        return faction.name();
    }
}