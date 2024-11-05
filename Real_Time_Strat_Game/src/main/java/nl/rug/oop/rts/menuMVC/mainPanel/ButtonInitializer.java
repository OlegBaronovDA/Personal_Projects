package nl.rug.oop.rts.menuMVC.mainPanel;

import nl.rug.oop.rts.menuMVC.PanelObserver;
import nl.rug.oop.rts.menuMVC.simulationView.ArmySelection;
import nl.rug.oop.rts.menuMVC.simulationView.Simulation;
import nl.rug.oop.rts.util.Json;
import nl.rug.oop.rts.util.TextureLoader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Initializes and manages the buttons in the GUI.
 */
public class ButtonInitializer implements PanelObserver {
    private final GraphPanel graphPanel;
    private JButton addNodeButton;
    private JButton removeNodeButton;
    private JButton addEdgeButton;
    private JButton removeEdgeButton;
    private JButton addArmyButton;
    private JButton removeArmyButton;
    private JButton playAudioButton;
    private JButton stopAudioButton;
    private JButton simulateButton;
    private JButton saveButton;
    private JButton[] buttons;

    /**
     * Constructs a ButtonInitializer object.
     * @param graphPanel The GraphPanel associated with the buttons.
     */
    public ButtonInitializer(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    @Override
    public void update() {
        updateButtons();
    }

    /**
     * Initializes all buttons and sets up their action listeners.
     */
    public void initializeButtons() {
        createButtons();
        configureButtonSizes();
        addActionListeners();
    }

    /**
     * Creates all buttons.
     */
    private void createButtons() {
        // Create buttons
        addNodeButton = new JButton("+ City");
        removeNodeButton = new JButton("- City");
        addEdgeButton = new JButton("+ Route");
        removeEdgeButton = new JButton("- Route");
        addArmyButton = new JButton("+ Army");
        removeArmyButton = new JButton("- Army");
        playAudioButton = new JButton("Audio on");
        stopAudioButton = new JButton("Audio off");
        simulateButton = new JButton("Simulate");
        saveButton = new JButton("JSON");

        // Store buttons in an array
        buttons = new JButton[10];
        buttons[0] = addNodeButton;
        buttons[1] = removeNodeButton;
        buttons[2] = addEdgeButton;
        buttons[3] = removeEdgeButton;
        buttons[4] = addArmyButton;
        buttons[5] = removeArmyButton;
        buttons[6] = playAudioButton;
        buttons[7] = stopAudioButton;
        buttons[8] = simulateButton;
        buttons[9] = saveButton;
    }

    /**
     * Configures the sizes of all buttons.
     */
    private void configureButtonSizes() {
        // Set button sizes
        Dimension buttonSize = new Dimension(100, 30);
        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
        }
    }

    /**
     * Sets up action listeners for all buttons.
     */
    private void addActionListeners() {
        addNodeButton.addActionListener(e -> {
            graphPanel.addNode();
            updateButtons();
        });
        removeNodeButton.addActionListener(e -> {
            graphPanel.removeSelectedNode();
            updateButtons();
        });
        addEdgeButton.addActionListener(e -> {
            graphPanel.startAddingEdge();
            updateButtons();
        });
        removeEdgeButton.addActionListener(e -> {
            graphPanel.removeSelectedEdge();
            updateButtons();
        });
        addArmyButton.addActionListener(e -> {
            ArmySelection.addArmy(graphPanel.getSelectedNode(), graphPanel, graphPanel.getButtonInitializer());
            updateButtons();
        });
        removeArmyButton.addActionListener(e -> {
            ArmySelection.removeArmy(graphPanel.getSelectedNode(), graphPanel, graphPanel.getButtonInitializer());
            updateButtons();
        });
        playAudioButton.addActionListener(e -> TextureLoader.playAudio());
        stopAudioButton.addActionListener(e -> TextureLoader.stopAudio());
        simulateButton.addActionListener(e -> {
            Simulation.simulate(graphPanel.getEventMessageLabel(), graphPanel.getGraph());
            graphPanel.repaint();
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Json.saveSimulationState(graphPanel.getGraph(), graphPanel);
            }
        });
    }

    /**
     * Creates a panel containing all buttons.
     * @return The panel containing the buttons.
     */
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add buttons to the panel
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    /**
     * Updates the enabled/disabled state of buttons based on the selected node and edge.
     */
    public void updateButtons() {
        removeNodeButton.setEnabled(graphPanel.getSelectedNode() != null);
        addEdgeButton.setEnabled(graphPanel.getSelectedNode() != null);
        removeEdgeButton.setEnabled(graphPanel.getSelectedEdge() != null);
        addArmyButton.setEnabled(graphPanel.getSelectedNode() != null);
        removeArmyButton.setEnabled(graphPanel.getSelectedNode() != null
                && !graphPanel.getSelectedNode().getArmies().isEmpty());
    }
}
