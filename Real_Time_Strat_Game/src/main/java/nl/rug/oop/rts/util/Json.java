package nl.rug.oop.rts.util;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Unit;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling JSON serialization and saving of simulation state.
 */
public class Json {

    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";

    /**
     * Saves the current simulation state to a JSON file.
     * @param graph  The graph representing the simulation state.
     * @param parent The parent component to display error messages.
     */
    public static void saveSimulationState(Graph graph, Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                // Get the selected file
                FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile() + ".json");
                // Write the simulation state to the file in JSON format
                fileWriter.write(serializeToJSON(graph));
                // Close the file writer
                fileWriter.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error occurred while saving the file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Serializes the graph to a JSON string.
     * @param graph The graph to be serialized.
     * @return The JSON string representing the serialized graph.
     */
    private static String serializeToJSON(Graph graph) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{").append(NEW_LINE);
        jsonBuilder.append("  \"Nodes\": [").append(NEW_LINE);

        List<String> nodeStrings = new ArrayList<>();
        for (Node node : graph.getNodes().values()) {
            nodeStrings.add(serializeNode(node));
        }
        if (!nodeStrings.isEmpty()) {
            jsonBuilder.append("    ").append(String.join(COMMA + NEW_LINE
                    + "    ", nodeStrings)).append(NEW_LINE);
        }
        jsonBuilder.append("  ],").append(NEW_LINE);

        jsonBuilder.append("  \"Edges\": [").append(NEW_LINE);
        List<String> edgeStrings = new ArrayList<>();
        for (Edge edge : graph.getEdges().values()) {
            edgeStrings.add(serializeEdge(edge));
        }
        if (!edgeStrings.isEmpty()) {
            jsonBuilder.append("    ").append(String.join(COMMA
                    + NEW_LINE + "    ", edgeStrings)).append(NEW_LINE);
        }
        jsonBuilder.append("  ]").append(NEW_LINE);

        jsonBuilder.append("}").append(NEW_LINE);

        return jsonBuilder.toString();
    }

    /**
     * Serializes a node to a JSON string.
     * @param node The node to be serialized.
     * @return The JSON string representing the serialized node.
     */
    private static String serializeNode(Node node) {
        StringBuilder nodeBuilder = new StringBuilder();

        nodeBuilder.append("{").append(NEW_LINE);
        nodeBuilder.append("      \"Id\": ").append(node.getId()).append(COMMA).append(NEW_LINE);
        nodeBuilder.append("      \"Name\": \"").append(node.getName()).append("\",").append(NEW_LINE);

        // Serialize armies
        nodeBuilder.append("      \"Armies\": [");
        List<String> armyStrings = new ArrayList<>();
        for (Army army : node.getArmies()) {
            armyStrings.add(serializeArmy(army));
        }
        if (armyStrings.isEmpty()) {
            nodeBuilder.append("]");
        } else {
            nodeBuilder.append(NEW_LINE).append("        ").append(String.join(COMMA
                    + NEW_LINE + "        ", armyStrings)).append(NEW_LINE).append("      ]");
        }

        // Serialize events
        nodeBuilder.append(COMMA).append(NEW_LINE).append("      \"Events\": [");
        List<String> eventStrings = new ArrayList<>();
        for (String event : node.getEvents()) {
            eventStrings.add("        \"" + event + "\"");
        }
        if (eventStrings.isEmpty()) {
            nodeBuilder.append("]");
        } else {
            nodeBuilder.append(NEW_LINE).append(String.join(COMMA
                    + NEW_LINE, eventStrings)).append(NEW_LINE).append("      ]");
        }

        nodeBuilder.append(NEW_LINE).append("    }");

        return nodeBuilder.toString();
    }

    /**
     * Serializes an edge to a JSON string.
     * @param edge The edge to be serialized.
     * @return The JSON string representing the serialized edge.
     */
    private static String serializeEdge(Edge edge) {
        StringBuilder edgeBuilder = new StringBuilder();

        edgeBuilder.append("{").append(NEW_LINE);
        edgeBuilder.append("      \"Id\": ").append(edge.getId()).append(COMMA).append(NEW_LINE);
        edgeBuilder.append("      \"Name\": \"").append(edge.getName()).append("\",").append(NEW_LINE);
        edgeBuilder.append("      \"Node1\": ").append(edge.getSource().getId()).append(COMMA).append(NEW_LINE);
        edgeBuilder.append("      \"Node2\": ").append(edge.getDestination().getId()).append(COMMA).append(NEW_LINE);

        // Serialize armies
        edgeBuilder.append("      \"Armies\": [");
        List<String> armyStrings = new ArrayList<>();
        for (Army army : edge.getArmies()) {
            armyStrings.add(serializeArmy(army));
        }
        if (armyStrings.isEmpty()) {
            edgeBuilder.append("]");
        } else {
            edgeBuilder.append(NEW_LINE).append("        ").append(String.join(COMMA
                    + NEW_LINE + "        ", armyStrings)).append(NEW_LINE).append("      ]");
        }

        // Serialize events
        edgeBuilder.append(COMMA).append(NEW_LINE).append("      \"Events\": [");
        List<String> eventStrings = new ArrayList<>();
        for (String event : edge.getEvents()) {
            eventStrings.add("        \"" + event + "\"");
        }
        if (eventStrings.isEmpty()) {
            edgeBuilder.append("]");
        } else {
            edgeBuilder.append(NEW_LINE).append(String.join(COMMA
                    + NEW_LINE, eventStrings)).append(NEW_LINE).append("      ]");
        }

        edgeBuilder.append(NEW_LINE).append("    }");

        return edgeBuilder.toString();
    }

    /**
     * Serializes an army to a JSON string.
     * @param army The army to be serialized.
     * @return The JSON string representing the serialized army.
     */
    private static String serializeArmy(Army army) {
        StringBuilder armyBuilder = new StringBuilder();

        armyBuilder.append("{").append(NEW_LINE);
        armyBuilder.append("          \"Name\": \"").append(army.getName()).append("\",").append(NEW_LINE);
        armyBuilder.append("          \"Faction\": \"").append(army.getFaction()).append("\",").append(NEW_LINE);
        armyBuilder.append("          \"Team\": ").append(army.getTeam().getValue()).append(COMMA).append(NEW_LINE);
        armyBuilder.append("          \"Units\": [");

        List<Unit> units = army.getUnits();
        if (units.isEmpty()) {
            armyBuilder.append("]");
        } else {
            armyBuilder.append(NEW_LINE);
            for (int i = 0; i < units.size(); i++) {
                armyBuilder.append("            ").append(serializeUnit(units.get(i)));
                if (i < units.size() - 1) {
                    armyBuilder.append(COMMA).append(NEW_LINE);
                } else {
                    armyBuilder.append(NEW_LINE);
                }
            }
            armyBuilder.append("          ]");
        }

        armyBuilder.append(NEW_LINE).append("        }");

        return armyBuilder.toString();
    }

    /**
     * Serializes a unit to a JSON string.
     * @param unit The unit to be serialized.
     * @return The JSON string representing the serialized unit.
     */
    private static String serializeUnit(Unit unit) {
        StringBuilder unitBuilder = new StringBuilder();

        unitBuilder.append("{").append(NEW_LINE);
        unitBuilder.append("              \"Name\": \"").append(unit.getName()).append("\",").append(NEW_LINE);
        unitBuilder.append("              \"Damage\": ").append(unit.getDamage()).append(COMMA).append(NEW_LINE);
        unitBuilder.append("              \"Health\": ").append(unit.getHealth()).append(NEW_LINE);
        unitBuilder.append("            }");

        return unitBuilder.toString();
    }
}