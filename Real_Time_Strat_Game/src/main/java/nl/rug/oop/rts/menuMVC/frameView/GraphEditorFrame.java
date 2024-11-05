package nl.rug.oop.rts.menuMVC.frameView;

import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.event.Event;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main frame of the graph editor application.
 */
public class GraphEditorFrame extends JFrame {
    private static final String FONT_NAME = "Arial";
    private GraphPanel graphPanel;
    private JPanel detailsPanel;
    private JLabel selectedNodeLabel;
    private JLabel eventMessageLabel;
    private JLabel eventMessageInfo;
    private JLabel eventMenu;
    private JLabel nameGame;
    private JTextField editNameTextField;
    private final JButton deleteEventButton = new JButton("Delete Event");
    private JComboBox<Event> eventComboBox;

    /**
     * Constructs a new GraphEditorFrame object.
     */
    public GraphEditorFrame() {
        setupFrame();
        createGraphPanel();
        createDetailsPanel();
        setupSplitPane();
        setupEventButtons();
    }

    /**
     * Sets up the properties of the main frame.
     */
    private void setupFrame() {
        setTitle("Graph Editor");
        setSize(1400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * Creates and configures the graph panel.
     */
    private void createGraphPanel() {
        Graph graph = new Graph();
        graphPanel = new GraphPanel(graph) {
            @Override
            public void selectNode(Node node) {
                super.selectNode(node);
                updateSelectedNode(node);
            }

            @Override
            public void selectEdge(Edge edge) {
                super.selectEdge(edge);
                updateSelectedEdge(edge);
            }
        };
    }

    /**
     * Creates and configures the details of the side panel.
     */
    private void createDetailsPanel() {
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.DARK_GRAY);

        nameGame = createLabel("Real Time Simulation", 30);
        selectedNodeLabel = createLabel("Nothing selected", 18);
        editNameTextField = createTextField();
        editNameTextField.setVisible(false);
        eventMessageInfo = createLabel("Action Info:", 20);
        eventMessageLabel = createLabel("Action messages will appear here", 15);
        eventMenu = createLabel("Event Menu:", 20);

        detailsPanel.add(nameGame);
        detailsPanel.add(selectedNodeLabel);
        detailsPanel.add(editNameTextField);
        detailsPanel.add(eventMessageInfo);
        detailsPanel.add(eventMessageLabel);
        detailsPanel.add(eventMenu);
    }

    /**
     * Creates a JLabel with the specified text and font size.
     * @param text The text to be displayed on the label.
     * @param fontSize The font size of the label.
     * @return The created JLabel.
     */
    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize));
        return label;
    }

    /**
     * Creates a JTextField for editing node/edge names.
     * @return The created JTextField.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.addActionListener(e -> editName());
        return textField;
    }

    /**
     * Configures the split pane to display the details panel and graph panel side by side.
     */
    private void setupSplitPane() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailsPanel, graphPanel);
        splitPane.setEnabled(false);
        splitPane.setResizeWeight(0.3);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(splitPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Configures the event buttons for triggering and deleting events.
     */
    private void setupEventButtons() {
        eventComboBox = new JComboBox<>(Event.values());

        JButton triggerEventButton = new JButton("Trigger Event");
        triggerEventButton.addActionListener(e -> triggerSelectedEvent());
        triggerEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(triggerEventButton);

        deleteEventButton.addActionListener(e -> deleteSelectedEvent());
        deleteEventButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(deleteEventButton);
    }

    /**
     * Deletes all events associated with the selected node or edge.
     */
    private void deleteSelectedEvent() {
        Node selectedNode = graphPanel.getSelectedNode();
        Edge selectedEdge = graphPanel.getSelectedEdge();

        if (selectedNode != null && selectedEdge == null) {
            selectedNode.deleteAllEvents();
            eventMessageLabel.setText("All events deleted from " + selectedNode.getName());
        } else if (selectedEdge != null && selectedNode == null) {
            selectedEdge.deleteAllEvents();
            eventMessageLabel.setText("All events deleted from " + selectedEdge.getName());
        }
    }

    /**
     * Triggers the selected event and applies it to the selected node or edge.
     */
    private void triggerSelectedEvent() {
        Event[] availableEvents = Event.values();

        Event selectedEvent = (Event) JOptionPane.showInputDialog(
                this,
                "Select the event to trigger:",
                "Trigger Event",
                JOptionPane.PLAIN_MESSAGE,
                null,
                availableEvents,
                null);

        if (selectedEvent != null) {
            Node selectedNode = graphPanel.getSelectedNode();
            Edge selectedEdge = graphPanel.getSelectedEdge();

            if (selectedNode != null && selectedEdge == null) {
                selectedNode.applyEvent(selectedEvent);
                eventMessageLabel.setText("Selected Event Added");
            } else if (selectedEdge != null && selectedNode == null) {
                selectedEdge.applyEvent(selectedEvent);
                eventMessageLabel.setText("Selected Event Added");
            } else {
                eventMessageLabel.setText("Please select a node or an edge");
            }
        } else {
            eventMessageLabel.setText("No event selected to trigger");
        }
    }

    /**
     * Edits the name of the selected node or edge.
     */
    private void editName() {
        if (graphPanel.getSelectedNode() != null) {
            String newName = editNameTextField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            graphPanel.getSelectedNode().setName(newName);
            updateSelectedNode(graphPanel.getSelectedNode());
            eventMessageLabel.setText("Node name updated to " + newName);
        } else if (graphPanel.getSelectedEdge() != null) {
            String newName = editNameTextField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            graphPanel.getSelectedEdge().setName(newName);
            updateSelectedEdge(graphPanel.getSelectedEdge());
            eventMessageLabel.setText("Edge name updated to " + newName);
        } else {
            JOptionPane.showMessageDialog(this, "Nothing selected",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the details panel with information about the selected node.
     * @param node The selected node.
     */
    private void updateSelectedNode(Node node) {
        if (node != null) {
            selectedNodeLabel.setText("Node:");
            editNameTextField.setText(node.getName());
            editNameTextField.setVisible(true);
        } else {
            selectedNodeLabel.setText("No node selected");
            editNameTextField.setVisible(false);
        }
    }

    /**
     * Updates the details panel with information about the selected edge.
     * @param edge The selected edge.
     */
    private void updateSelectedEdge(Edge edge) {
        if (edge != null) {
            StringBuilder labelText = new StringBuilder();
            labelText.append("Source: ").append(edge.getSource().getName());
            labelText.append(" Destination: ").append(edge.getDestination().getName());
            labelText.append(" Edge:");
            selectedNodeLabel.setText(labelText.toString());
            editNameTextField.setText(edge.getName());
            editNameTextField.setVisible(true);
        } else {
            selectedNodeLabel.setText("Nothing selected");
            editNameTextField.setVisible(false);
        }
    }
}