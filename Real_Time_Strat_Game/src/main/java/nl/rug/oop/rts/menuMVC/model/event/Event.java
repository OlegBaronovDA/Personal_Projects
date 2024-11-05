package nl.rug.oop.rts.menuMVC.model.event;

/**
 * Enumeration representing different random events that can occur.
 */
public enum Event {
    STRONG_RAIN("Strong rain flooded the city - All units Health -10") {
        /**
         * Applies the effect of the strong rain event.
         * @param event The random event object.
         */
        @Override
        public void applyEvent(RandomEvent event) {
            event.reduceHealth();
        }
    },
    MOBILISATION("Mobilisation event - New units added") {
        /**
         * Applies the effect of the mobilisation event.
         * @param event The random event object.
         */
        @Override
        public void applyEvent(RandomEvent event) {
            event.addUnitsArmy();
        }
    },
    RELIGIOUS_SPIRIT("Religious spirit event - All units Damage +10") {
        /**
         * Applies the effect of the religious spirit event.
         * @param event The random event object.
         */
        @Override
        public void applyEvent(RandomEvent event) {
            event.increaseDamage();
        }
    },
    UPRISING("Uprising - All units Damage -10") {
        /**
         * Applies the effect of the uprising event.
         * @param event The random event object.
         */
        @Override
        public void applyEvent(RandomEvent event) {
            event.reduceDamage();
        }
    },
    SICKNESS("Sickness hits the army - All units Health -10") {
        /**
         * Applies the effect of the sickness event.
         * @param event The random event object.
         */
        @Override
        public void applyEvent(RandomEvent event) {
            event.reduceHealth();
        }
    };

    private final String description;

    /**
     * Constructs an Event object with a description.
     * @param description The description of the event.
     */
    Event(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the event.
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Abstract method to apply the effect of the event.
     * @param event The random event object.
     */
    public abstract void applyEvent(RandomEvent event);
}
