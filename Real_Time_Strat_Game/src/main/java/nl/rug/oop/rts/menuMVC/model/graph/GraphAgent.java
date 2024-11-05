package nl.rug.oop.rts.menuMVC.model.graph;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.event.Event;
import nl.rug.oop.rts.menuMVC.model.event.RandomEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An abstract class representing agents in the graph.
 */
public abstract class GraphAgent {
    private int id;
    private String name;
    private List<Army> armies; // The armies associated with the agent
    private boolean randomEventActive; // Flag indicating if a random event is active
    private String eventName; // The name of the current event
    private List<String> eventsPresent; // List of events present for the agent

    /**
     * Constructs a GraphAgent with the given ID and name.
     * @param id The ID of the agent.
     * @param name The name of the agent.
     */
    public GraphAgent(int id, String name) {
        this.id = id;
        this.name = name;
        this.armies = new ArrayList<>(); // Initialize the list of armies
        this.randomEventActive = false; // Random event is initially inactive
        this.eventsPresent = new ArrayList<>(); // Initialize the list of present events
    }

    /**
     * Gets the ID of the agent.
     * @return The ID of the agent.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the agent.
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the agent.
     * @return The name of the agent.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the agent.
     * @param newName The new name of the agent.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Gets the armies associated with the agent.
     * @return The list of armies.
     */
    public List<Army> getArmies() {
        return armies;
    }

    /**
     * Adds an army to the list of armies.
     * @param army The army to add.
     */
    public void addArmy(Army army) {
        armies.add(army);
        if (randomEventActive) {
            // Check if a random event should occur
            Random random = new Random();
            int chance = 1 + random.nextInt(101);

            // Ensure 50% chance of encountering a random event
            if (chance <= 50) {
                List<Event> availableEvents = new ArrayList<>();
                for (Event event : Event.values()) {
                    if (eventsPresent.contains(event.getDescription())) {
                        availableEvents.add(event);
                    }
                }

                // Apply a random event if available
                if (!availableEvents.isEmpty()) {
                    Event selectedEvent = availableEvents.get(random.nextInt(availableEvents.size()));
                    applySpecificEvent(selectedEvent);
                }
            } else {
                eventName = "Nothing happened";
            }
        }
    }

    /**
     * Removes an army from the list of armies.
     * @param army The army to remove.
     */
    public void removeArmy(Army army) {
        armies.remove(army);
    }

    /**
     * Applies a general event to the agent.
     * @param selectedEvent The event to apply.
     */
    public void applyEvent(Event selectedEvent){
        this.randomEventActive = true;
        this.eventsPresent.add(selectedEvent.getDescription());
    }

    /**
     * Applies a specific event to the agent.
     * @param selectedEvent The event to apply.
     */
    public void applySpecificEvent(Event selectedEvent) {
        if (eventsPresent.contains(selectedEvent.getDescription())) {
            RandomEvent event = new RandomEvent(armies);
            List<Army> armiesToRemove = new ArrayList<>();

            // Apply the event to each army
            selectedEvent.applyEvent(event);
            eventName = selectedEvent.getDescription();

            // Check for armies with health <= 0
            for (Army army : armies) {
                if (army.getArmyHealth() <= 0) {
                    armiesToRemove.add(army);
                }
            }
            armies.removeAll(armiesToRemove);
        }
    }

    /**
     * Deletes all events from the agent.
     */
    public void deleteAllEvents() {
        eventsPresent.clear();
        eventName = null;
        randomEventActive = false;
    }

    /**
     * Gets the name of the current event.
     * @return The name of the current event.
     */
    public String getEventName() {
        return eventName;
    }

    public List<String> getEvents() {
        return this.eventsPresent;
    }
}