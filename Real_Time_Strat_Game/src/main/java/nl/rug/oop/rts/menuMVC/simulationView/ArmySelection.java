package nl.rug.oop.rts.menuMVC.simulationView;

import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;
import nl.rug.oop.rts.menuMVC.mainPanel.ButtonInitializer;
import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Faction;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import javax.swing.*;

/**
 * Class responsible for handling army selection operations.
 */
public class ArmySelection {
    /**
     * Method to add an army to a selected node.
     * @param selectedNode The node to which the army will be added.
     * @param graphPanel The graph panel to be repainted.
     * @param buttonInitializer The button initializer to update buttons.
     */
    public static void addArmy(Node selectedNode, GraphPanel graphPanel, ButtonInitializer buttonInitializer) {
        if (selectedNode != null) {
            Faction faction = (Faction) JOptionPane.showInputDialog(
                    graphPanel, "Select a faction:", "Add Army",
                    JOptionPane.QUESTION_MESSAGE, null, Faction.values(), Faction.values()[0]);

            if (faction != null) {
                selectedNode.addArmy(new Army(faction));
                graphPanel.repaint();
                buttonInitializer.updateButtons();
            }
        }
    }

    /**
     * Method to remove an army from a selected node.
     * @param selectedNode The node from which the army will be removed.
     * @param graphPanel The graph panel to be repainted.
     * @param buttonInitializer The button initializer to update buttons.
     */
    public static void removeArmy(Node selectedNode, GraphPanel graphPanel, ButtonInitializer buttonInitializer) {
        if (selectedNode != null && !selectedNode.getArmies().isEmpty()) {
            String armyToRemoveName = (String) JOptionPane.showInputDialog(
                    graphPanel, "Select an army to remove:", "Remove Army",
                    JOptionPane.QUESTION_MESSAGE, null,
                    selectedNode.getArmies().stream().map(Army::getName).toArray(String[]::new),
                    selectedNode.getArmies().get(0).getName());

            if (armyToRemoveName != null) {
                selectedNode.getArmies().removeIf(army -> army.getName().equals(armyToRemoveName));
                graphPanel.repaint();
                buttonInitializer.updateButtons();
            }
        } else {
            JOptionPane.showMessageDialog(graphPanel, "No armies to remove",
                    "Remove Army", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}