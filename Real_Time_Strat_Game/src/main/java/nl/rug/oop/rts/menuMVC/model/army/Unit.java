package nl.rug.oop.rts.menuMVC.model.army;

/**
 * Class representing a unit in the game.
 */
public class Unit {
    private String name;
    private int damage;
    private int health;

    /**
     * Constructor for creating a unit with specified name, damage, and health.
     * @param name The name of the unit.
     * @param damage The damage value of the unit.
     * @param health The health value of the unit.
     */
    public Unit(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = health;
    }

    /**
     * Getter for retrieving the name of the unit.
     * @return The name of the unit.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for retrieving the damage value of the unit.
     * @return The damage value of the unit.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Method for increasing the damage value of the unit.
     * @param newDamage The amount by which to increase the damage value.
     */
    public void increaseDamage(int newDamage) {
        this.damage += newDamage;
    }

    /**
     * Method for reducing the damage value of the unit.
     * @param newDamage The amount by which to reduce the damage value.
     */
    public void reduceDamage(int newDamage) {
        this.damage -= newDamage;
    }

    /**
     * Getter for retrieving the health value of the unit.
     * @return The health value of the unit.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method for applying damage to the unit.
     * @param damage The amount of damage to apply.
     */
    public void applyDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }
}