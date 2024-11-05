package nl.rug.oop.rts.menuMVC.simulationView;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Team;
import nl.rug.oop.rts.menuMVC.model.army.Unit;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;
import nl.rug.oop.rts.util.TextureLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manages combat interactions between armies on nodes and edges.
 */
public class Combat {

    /**
     * Checks if combat is happening on a specific node.
     * @param node The node to check.
     * @return True if combat is happening, false otherwise.
     */
    public boolean isCombatHappeningOnNode(Node node) {
        List<Army> armies = node.getArmies();
        if (armies == null || armies.size() < 2){
            return false;
        }
        return hasOpposingTeams(armies);
    }

    /**
     * Checks if combat is happening on a specific edge.
     * @param edge The edge to check.
     * @return True if combat is happening, false otherwise.
     */
    public boolean isCombatHappeningOnEdge(Edge edge) {
        Edge clone = edge.getCloneEdge();
        List<Army> armies = edge.getArmies();
        List<Army> armiesClone = (clone != null) ? clone.getArmies() : null;
        List<Army> combinedArmies = combineArmies(armies, armiesClone);
        if (combinedArmies == null || combinedArmies.size() < 2){
            return false;
        }
        return hasOpposingTeams(combinedArmies);
    }

    /**
     * Checks if there are opposing teams in a list of armies.
     * @param armies The list of armies.
     * @return True if opposing teams are found, false otherwise.
     */
    private boolean hasOpposingTeams(List<Army> armies) {
        Team firstTeam = armies.get(0).getTeam();
        for (Army army : armies) {
            if (army.getTeam() != firstTeam){
                return true;
            }
        }
        return false;
    }

    /**
     * Combines two lists of armies into one.
     * @param armies1 First list of armies.
     * @param armies2 Second list of armies.
     * @return Combined list of armies.
     */
    private List<Army> combineArmies(List<Army> armies1, List<Army> armies2) {
        if (armies1 == null && armies2 == null){
            return null;
        }
        List<Army> combinedArmies = new ArrayList<>();
        if (armies1 != null){
            combinedArmies.addAll(armies1);
        }
        if (armies2 != null){
            combinedArmies.addAll(armies2);
        }
        return combinedArmies;
    }

    /**
     * Initiates combat across the entire graph.
     * @param graph The graph containing the nodes and edges.
     */
    public void initiateCombat(Graph graph) {
        TextureLoader.playAudioClip("WAR");
        engageOnNodes(graph);
        engageOnEdges(graph);
    }

    /**
     * Initiates combat on nodes.
     * @param graph The graph containing the nodes.
     */
    private void engageOnNodes(Graph graph) {
        for (Node node : graph.getNodes().values()) {
            if (isCombatHappeningOnNode(node)) {
                engageNode(node);
            }
        }
    }

    /**
     * Initiates combat on edges.
     * @param graph The graph containing the edges.
     */
    private void engageOnEdges(Graph graph) {
        for (Edge edge : graph.getEdges().values()) {
            if (isCombatHappeningOnEdge(edge)) {
                engageEdge(edge);
            }
        }
    }

    /**
     * Initiates combat on a specific node.
     * @param node The node to engage in combat.
     */
    private void engageNode(Node node) {
        List<Army> armies = node.getArmies();
        List<Army> armiesToRemove = new ArrayList<>();
        for (Army attackingArmy : armies) {
            List<Army> potentialTargets = findPotentialTargets(armies, attackingArmy);
            engageTarget(attackingArmy, potentialTargets, armiesToRemove);
        }
        armies.removeAll(armiesToRemove);
    }

    /**
     * Initiates combat on a specific edge.
     * @param edge The edge to engage in combat.
     */
    private void engageEdge(Edge edge) {
        List<Army> armies = edge.getArmies();
        List<Army> armiesClone = (edge.getCloneEdge() != null) ? edge.getCloneEdge().getArmies() : null;
        List<Army> combinedArmies = combineArmies(armies, armiesClone);
        if (combinedArmies == null){
            return;
        }
        List<Army> armiesToRemove = new ArrayList<>();
        for (Army attackingArmy : combinedArmies) {
            List<Army> potentialTargets = findPotentialTargets(combinedArmies, attackingArmy);
            engageTarget(attackingArmy, potentialTargets, armiesToRemove);
        }
        armies.removeAll(armiesToRemove);
        if (armiesClone != null){
            armiesClone.removeAll(armiesToRemove);
        }
    }

    /**
     * Finds potential targets for an attacking army.
     * @param armies The list of armies.
     * @param attackingArmy The attacking army.
     * @return List of potential targets.
     */
    private List<Army> findPotentialTargets(List<Army> armies, Army attackingArmy) {
        List<Army> potentialTargets = new ArrayList<>();
        for (Army targetArmy : armies) {
            if (targetArmy.getTeam() != attackingArmy.getTeam()) {
                potentialTargets.add(targetArmy);
            }
        }
        return potentialTargets;
    }

    /**
     * Engages a target army with an attacking army.
     * @param attackingArmy The attacking army.
     * @param potentialTargets List of potential target armies.
     * @param armiesToRemove List of armies to remove after combat.
     */
    private void engageTarget(Army attackingArmy, List<Army> potentialTargets, List<Army> armiesToRemove) {
        if (!potentialTargets.isEmpty()) {
            Random random = new Random();
            Army targetArmy = potentialTargets.get(random.nextInt(potentialTargets.size()));
            List<Unit> targetUnits = targetArmy.getUnits();
            if (!targetUnits.isEmpty()) {
                Unit targetUnit = targetUnits.get(random.nextInt(targetUnits.size()));
                int damage = random.nextInt(10) + 1;
                targetUnit.applyDamage(damage);
                if (targetUnit.getHealth() <= 0) {
                    targetUnits.remove(targetUnit);
                    if (targetUnits.isEmpty()) {
                        armiesToRemove.add(targetArmy);
                    }
                }
            }
        }
    }
}