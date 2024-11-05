package nl.rug.oop.rts.menuMVC.simulationView;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles the simulation of events and movements in the graph.
 */
public class Simulation {
    private Graph graph;

    /**
     * Constructs a Simulation object with the specified graph.
     * @param graph The graph to simulate events and movements on.
     */
    public Simulation(Graph graph) {
        this.graph = graph;
    }

    /**
     * Simulates a single time step for the graph, including movements and combat.
     */
    public void simulateSingleTimeStep() {
        Combat combat = new Combat();

        simulateNodeArmies(combat);
        simulateEdgeArmies(combat);
        resetArmyMovementFlags();
    }

    /**
     * Simulates the movements and combat for armies located on nodes.
     * @param combat The combat instance to handle combat logic.
     */
    private void simulateNodeArmies(Combat combat) {
        for (Node node : graph.getNodes().values()) {
            if (combat.isCombatHappeningOnNode(node)) {
                combat.initiateCombat(graph);
                return; // Exit if combat is initiated
            }
            moveArmiesFromNode(node);
        }
    }

    /**
     * Moves armies from a node to random outgoing edges.
     * @param node The node from which armies will be moved.
     */
    private void moveArmiesFromNode(Node node) {
        List<Army> armiesToMove = new ArrayList<>(node.getArmies());
        for (Army army : armiesToMove) {
            Edge randomEdge = getRandomOutgoingEdge(node);
            if (randomEdge != null) {
                army.setDestination(randomEdge);
                moveArmyToEdge(army, randomEdge);
                army.setMoved(true);
            }
        }
    }

    /**
     * Simulates the movements and combat for armies located on edges.
     * @param combat The combat instance to handle combat logic.
     */
    private void simulateEdgeArmies(Combat combat) {
        for (Edge edge : graph.getEdges().values()) {
            if (combat.isCombatHappeningOnEdge(edge)) {
                combat.initiateCombat(graph);
                return; // Exit if combat is initiated
            }
            moveArmiesAlongEdge(edge);
        }
    }

    /**
     * Moves armies along an edge to their destination nodes.
     * @param edge The edge along which armies will be moved.
     */
    private void moveArmiesAlongEdge(Edge edge) {
        List<Army> armiesToMove = new ArrayList<>(edge.getArmies());
        for (Army army : armiesToMove) {
            if (!army.hasMoved()) {
                moveArmyToNode(army, army.getDestination());
                army.setMoved(true);
            }
        }
    }

    /**
     * Resets the movement flags for all armies on nodes and edges.
     */
    private void resetArmyMovementFlags() {
        for (Node node : graph.getNodes().values()) {
            resetMovementFlagsForArmies(node.getArmies());
        }

        for (Edge edge : graph.getEdges().values()) {
            resetMovementFlagsForArmies(edge.getArmies());
        }
    }

    /**
     * Resets the movement flags for a list of armies.
     * @param armies The list of armies to reset.
     */
    private void resetMovementFlagsForArmies(List<Army> armies) {
        for (Army army : armies) {
            army.setMoved(false);
        }
    }

    /**
     * Retrieves a random outgoing edge from the specified node.
     * @param node The node to retrieve the edge from.
     * @return A random outgoing edge from the node, or null if none exists.
     */
    private Edge getRandomOutgoingEdge(Node node) {
        List<Edge> outgoingEdges = node.getEdges();
        if (!outgoingEdges.isEmpty()) {
            List<Edge> validEdges = new ArrayList<>();
            for (Edge edge : outgoingEdges) {
                if (!edge.getDestination().equals(node)) {
                    validEdges.add(edge);
                }
            }
            // If there are valid edges remaining, select a random one
            if (!validEdges.isEmpty()) {
                int randomIndex = new Random().nextInt(validEdges.size());
                return validEdges.get(randomIndex);
            }
        }
        return null;
    }

    /**
     * Moves an army to the specified edge.
     * @param army The army to move.
     * @param edge The edge to move the army to.
     */
    private void moveArmyToEdge(Army army, Edge edge) {
        Node source = edge.getSource();
        Node destination = edge.getDestination();

        if (source.getArmies().contains(army)) {
            source.removeArmy(army);
        } else if (destination.getArmies().contains(army)) {
            destination.removeArmy(army);
        }

        edge.addArmy(army);
    }

    /**
     * Moves an army to the specified node.
     * @param army The army to move.
     * @param edge The edge representing the destination node.
     */
    private void moveArmyToNode(Army army, Edge edge) {
        Node destination = edge.getDestination();
        edge.removeArmy(army);
        destination.addArmy(army);
    }

    /**
     * Simulates the events and movements for the graph.
     * @param eventMessageLabel The label to display event messages.
     * @param graph The graph to simulate.
     */
    public static void simulate(JLabel eventMessageLabel, Graph graph) {
        Simulation simulation = new Simulation(graph);
        simulation.simulateSingleTimeStep();

        StringBuilder messageBuilder = new StringBuilder();
        boolean hasEvent = appendEventsToStringBuilder(messageBuilder, graph);

        eventMessageLabel.setText(hasEvent ? "<html>" + messageBuilder + "</html>" : "");
        eventMessageLabel.getParent().setVisible(hasEvent);
    }

    /**
     * Appends event messages to the string builder.
     * @param messageBuilder The string builder to append to.
     * @param graph The graph containing nodes and edges.
     * @return True if there are events, false otherwise.
     */
    protected static boolean appendEventsToStringBuilder(StringBuilder messageBuilder, Graph graph) {
        boolean hasEvent = false;

        for (Node node : graph.getNodes().values()) {
            appendEventIfExists(messageBuilder, node.getName(), node.getEventName(), "Node");
            hasEvent |= (node.getEventName() != null);
        }

        for (Edge edge : graph.getEdges().values()) {
            appendEventIfExists(messageBuilder, edge.getName(), edge.getEventName(), "Edge");
            hasEvent |= (edge.getEventName() != null);
        }

        return hasEvent;
    }

    /**
     * Appends an event message to the string builder if the event exists.
     * @param messageBuilder The string builder to append to.
     * @param name The name of the node or edge.
     * @param eventName The name of the event.
     * @param type The type of the element (Node or Edge).
     */
    private static void appendEventIfExists(StringBuilder messageBuilder, String name, String eventName, String type) {
        if (eventName != null) {
            messageBuilder.append(type).append(" ").append(name).append(": ").append(eventName).append("<br>");
        }
    }
}