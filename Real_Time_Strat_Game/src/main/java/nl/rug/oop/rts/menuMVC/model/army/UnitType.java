package nl.rug.oop.rts.menuMVC.model.army;

/**
 * Enumerates different types of units with their respective factions.
 */
public enum UnitType {
    GONDOR_SOLDIER(Faction.MEN),
    TOWER_GUARD(Faction.MEN),
    ITHILIEN_RANGER(Faction.MEN),

    LORIEN_WARRIOR(Faction.ELVES),
    MIRKWOOD_ARCHER(Faction.ELVES),
    RIVENDELL_LANCER(Faction.ELVES),

    GUARDIAN(Faction.DWARVES),
    PHALANX(Faction.DWARVES),
    AXE_THROWER(Faction.DWARVES),

    ORC_WARRIOR(Faction.MORDOR),
    ORC_PIKEMAN(Faction.MORDOR),
    HARADRIM_ARCHER(Faction.MORDOR),

    URUK_HAI(Faction.ISENGARD),
    URUK_CROSSBOWMAN(Faction.ISENGARD),
    WARG_RIDER(Faction.ISENGARD),

    RU_ARMY(Faction.PUTIN),
    T34(Faction.PUTIN),
    MIG_35(Faction.PUTIN);

    private final Faction faction;

    /**
     * Constructs a UnitType with the specified faction.
     * @param faction The faction associated with the unit type.
     */
    UnitType(Faction faction) {
        this.faction = faction;
    }

    /**
     * Retrieves the faction associated with the unit type.
     * @return The faction.
     */
    public Faction getFaction() {
        return faction;
    }
}