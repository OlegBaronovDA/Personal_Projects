package nl.rug.oop.rts.menuMVC.mainPanel;

import nl.rug.oop.rts.menuMVC.PanelObserver;
import nl.rug.oop.rts.menuMVC.messageView.DetailsPanelManager;
import nl.rug.oop.rts.menuMVC.messageView.EventMessagePanelManager;
import nl.rug.oop.rts.menuMVC.controller.NodeMouseListener;
import nl.rug.oop.rts.menuMVC.model.graph.Position;
import nl.rug.oop.rts.menuMVC.model.drawer.GraphDrawer;
import nl.rug.oop.rts.menuMVC.model.drawer.GraphPanelPainter;
import nl.rug.oop.rts.menuMVC.model.drawer.NodeDrawer;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;
import nl.rug.oop.rts.util.TextureLoader;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The GraphPanel class represents the main panel for displaying and interacting with the graph.
 */
public class GraphPanel extends JPanel {
    private final Graph graph;
    private final Map<Node, Point> nodeLocations = new HashMap<>();
    private Node selectedNode;
    private Point dragStartPoint;
    private Edge selectedEdge;
    private Node firstNodeForEdge;
    private ButtonInitializer buttonInitializer;
    private final JSplitPane splitPane;
    private final JLabel eventMessageLabel;
    private final NodeDrawer nodeDrawer;
    private final GraphDrawer graphDrawer;
    private final TextureLoader textureLoader;
    private final DetailsPanelManager detailsPanelManager;
    private final EventMessagePanelManager eventMessagePanelManager;
    private final List<PanelObserver> observers = new ArrayList<>();
    private final GraphPanelPainter painter;

    /**
     * Constructs a GraphPanel object with the specified graph.
     * @param graph The graph to be displayed and interacted with.
     */
    public GraphPanel(Graph graph) {
        this.graph = graph;
        textureLoader = TextureLoader.getInstance();
        nodeDrawer = new NodeDrawer(textureLoader);
        graphDrawer = new GraphDrawer(nodeLocations, graph);
        painter = new GraphPanelPainter(this);
        buttonInitializer = new ButtonInitializer(this);
        buttonInitializer.initializeButtons();
        add(buttonInitializer.createButtonPanel(), BorderLayout.NORTH);
        eventMessagePanelManager = new EventMessagePanelManager();
        eventMessageLabel = eventMessagePanelManager.setupEventMessagePanel(this);
        detailsPanelManager = new DetailsPanelManager();
        splitPane = detailsPanelManager.setupDetailsPanel(this);
        addMouseListener(new NodeMouseListener(this));
        addMouseMotionListener(new NodeMouseListener(this));
        addObserver(buttonInitializer);
        addObserver(detailsPanelManager);
        addObserver(textureLoader);
    }

    /**
     * Adds an observer to the list of observers.
     * @param observer The observer to be added.
     */
    public void addObserver(PanelObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers about changes.
     */
    public void notifyObservers() {
        for (PanelObserver observer : observers) {
            observer.update();
        }
        repaint();
    }

    /**
     * Selects the specified node.
     * @param node The node to be selected.
     */
    public void selectNode(Node node) {
        selectedNode = node;
        selectedEdge = null;
        notifyObservers();
    }

    /**
     * Retrieves first node selected for edge.
     * @return First node.
     */
    public Node getFirstNodeForEdge(){
        return this.firstNodeForEdge;
    }

    /**
     * Selects the specified edge.
     * @param edge The edge to be selected.
     */
    public void selectEdge(Edge edge) {
        selectedEdge = edge;
        selectedNode = null;
        notifyObservers();
    }

    /**
     * Retrieves the currently selected node.
     * @return The currently selected node.
     */
    public Node getSelectedNode() {
        return selectedNode;
    }

    /**
     * Retrieves the label for displaying event messages.
     * @return The label for displaying event messages.
     */
    public JLabel getEventMessageLabel() {
        return eventMessageLabel;
    }

    /**
     * Retrieves the button initializer.
     * @return The button initializer.
     */
    public ButtonInitializer getButtonInitializer() {
        return this.buttonInitializer;
    }

    /**
     * Retrieves the currently selected edge.
     * @return The currently selected edge.
     */
    public Edge getSelectedEdge() {
        return selectedEdge;
    }

    /**
     * Retrieves the graph associated with this panel.
     * @return The graph associated with this panel.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Initiates the dragging of a node at the specified coordinates.
     * @param x The x-coordinate of the mouse pointer.
     * @param y The y-coordinate of the mouse pointer.
     */
    public void startDraggingNode(int x, int y) {
        dragStartPoint = new Point(x, y);
    }

    /**
     * Stops the dragging of a node.
     */
    public void stopDraggingNode() {
        dragStartPoint = null;
    }

    /**
     * Paints the components of the graph panel.
     * @param g The Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        painter.paintComponent(g);
    }

    /**
     * Retrieves the node locations map.
     * @return The map of node locations.
     */
    public Map<Node, Point> getNodeLocations() {
        return nodeLocations;
    }

    /**
     * Retrieves the GraphDrawer instance.
     * @return The GraphDrawer instance.
     */
    public GraphDrawer getGraphDrawer() {
        return graphDrawer;
    }

    /**
     * Retrieves the NodeDrawer instance.
     * @return The NodeDrawer instance.
     */
    public NodeDrawer getNodeDrawer() {
        return nodeDrawer;
    }

    /**
     * Retrieves the TextureLoader instance.
     * @return The TextureLoader instance.
     */
    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    /**
     * Drags the selected node to the specified coordinates.
     * @param x The x-coordinate of the mouse pointer.
     * @param y The y-coordinate of the mouse pointer.
     */
    public void dragSelectedNode(int x, int y) {
        if (selectedNode != null && dragStartPoint != null) {
            Point currentPoint = new Point(x, y);
            int deltaX = currentPoint.x - dragStartPoint.x;
            int deltaY = currentPoint.y - dragStartPoint.y;
            Point nodeLocation = nodeLocations.get(selectedNode);
            if (nodeLocation != null) {
                // Moves point to new location using derived values
                nodeLocation.translate(deltaX, deltaY);
                nodeLocation.setLocation(
                        // Prevents nodes from moving off the screen
                        Math.max(0, Math.min(getWidth() - 65, nodeLocation.x)),
                        Math.max(0, Math.min(getHeight() - 65, nodeLocation.y))
                );
                repaint();
            }
            dragStartPoint = currentPoint;
        }
    }

    /**
     * Adds a new node to the graph.
     */
    protected void addNode() {
        String nodeName = JOptionPane.showInputDialog(this, "Enter node name:",
                "Add Node", JOptionPane.PLAIN_MESSAGE);
        if (nodeName != null && !nodeName.trim().isEmpty()) {
            int randomId = new Random().nextInt(1000);
            Node newNode = new Node(randomId, nodeName);
            graph.addNode(newNode);
            nodeLocations.put(newNode, Position.calculateInitialNodePosition());
            notifyObservers();
        }
    }

    /**
     * Removes the selected node from the graph.
     */
    protected void removeSelectedNode() {
        if (selectedNode != null) {
            graph.removeNode(selectedNode);
            nodeLocations.remove(selectedNode);
            selectedNode = null;
            notifyObservers();
        }
    }

    /**
     * Initiates the process of adding an edge between nodes.
     */
    protected void startAddingEdge() {
        firstNodeForEdge = selectedNode;
    }

    /**
     * Finalizes the process of adding an edge between nodes.
     * @param secondNode The second node to connect with the edge.
     */
    public void finishAddingEdge(Node secondNode) {
        if (firstNodeForEdge != null && secondNode != null && secondNode != firstNodeForEdge) {
            String edgeName = JOptionPane.showInputDialog(this, "Enter edge name:",
                    "Add Edge", JOptionPane.PLAIN_MESSAGE);

            if (edgeName != null && !edgeName.trim().isEmpty()) {
                Random random = new Random();
                int edgeId = random.nextInt(1000);

                Edge newEdge = new Edge(edgeId, edgeName, firstNodeForEdge, secondNode);
                Edge clone = new Edge(edgeId + 1, edgeName, secondNode, firstNodeForEdge);
                newEdge.setCloneEdge(clone);
                graph.addEdge(newEdge);
                graph.addEdge(newEdge.getCloneEdge());
                selectEdge(newEdge);
                notifyObservers();
            }
            firstNodeForEdge = null;
        }
    }

    /**
     * Removes the selected edge from the graph.
     */
    protected void removeSelectedEdge() {
        if (selectedEdge != null) {
            graph.removeEdge(selectedEdge);
            graph.removeEdge(selectedEdge.getCloneEdge());
            selectedEdge = null;
            notifyObservers();
        }
    }

    /**
     * Retrieves the node at the specified position.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The node at the specified position, or null if none.
     */
    public Node getNodeAtPosition(int x, int y) {
        for (Map.Entry<Node, Point> entry : nodeLocations.entrySet()) {
            Node node = entry.getKey();
            Point location = entry.getValue();
            if (location != null && x >= location.x && x <= location.x + 65 &&
                    y >= location.y && y <= location.y + 65) {
                return node;
            }
        }
        return null;
    }

    /**
     * Retrieves the edge at the specified position.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The edge at the specified position, or null if none.
     */
    public Edge getEdgeAtPosition(int x, int y) {
        int threshold = 5;
        for (Edge edge : graph.getEdges().values()) {
            Point src = nodeLocations.get(edge.getSource());
            Point dest = nodeLocations.get(edge.getDestination());
            if (src != null && dest != null) {
                double distance = Position.pointToLineDistance(x, y, src.x + 25, src.y + 25,
                        dest.x + 25, dest.y + 25);
                if (distance < threshold) {
                    return edge;
                }
            }
        }
        return null;
    }
}