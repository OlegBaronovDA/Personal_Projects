package nl.rug.oop.rts.menuMVC.model.event;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Unit;

import java.util.List;

/**
 * Class representing a random event affecting armies.
 */
public class RandomEvent {
    private List<Army> armies;

    /**
     * Constructor for creating a random event with specified armies.
     * @param armies The list of armies affected by the random event.
     */
    public RandomEvent(List<Army> armies) {
        this.armies = armies;
    }

    /**
     * Method to add random units to each army in the list.
     */
    public void addUnitsArmy() {
        for (Army army : armies) {
            army.addRandomUnitsEvent();
        }
    }

    /**
     * Method to reduce the health of each unit in each army by 10.
     */
    public void reduceHealth() {
        for (Army army : armies) {
            List<Unit> units = army.getUnits();
            for (Unit unit : units) {
                unit.applyDamage(10);
            }
        }
    }

    /**
     * Method to increase the damage of each unit in each army by 10.
     */
    public void increaseDamage() {
        for (Army army : armies) {
            List<Unit> units = army.getUnits();
            for (Unit unit : units) {
                unit.increaseDamage(10);
            }
        }
    }

    /**
     * Method to reduce the damage of each unit in each army by 10.
     */
    public void reduceDamage() {
        for (Army army : armies) {
            List<Unit> units = army.getUnits();
            for (Unit unit : units) {
                unit.reduceDamage(10);
            }
        }
    }
}