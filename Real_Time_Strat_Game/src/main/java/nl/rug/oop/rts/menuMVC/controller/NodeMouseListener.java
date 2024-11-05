package nl.rug.oop.rts.menuMVC.controller;

import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handles mouse events for nodes and edges on the graph panel.
 */
public class NodeMouseListener extends MouseAdapter {
    private GraphPanel graphPanel;

    /**
     * Constructs a NodeMouseListener using graph panel.
     * @param graphPanel The graph panel to attach the listener to.
     */
    public NodeMouseListener(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     * @param e The MouseEvent containing the details of the click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Node clickedNode = graphPanel.getNodeAtPosition(x, y);
        if (clickedNode != null) {
            // If a node is already selected for edge creation, finish adding the edge.
            if (graphPanel.getFirstNodeForEdge() != null) {
                graphPanel.finishAddingEdge(clickedNode);
            } else {
                // Otherwise, select the clicked node.
                graphPanel.selectNode(clickedNode);
            }
        } else {
            // If no node is clicked, check if an edge was clicked.
            Edge clickedEdge = graphPanel.getEdgeAtPosition(x, y);
            if (clickedEdge != null) {
                // Select the clicked edge.
                graphPanel.selectEdge(clickedEdge);
            } else {
                // If neither node nor edge is clicked, deselect any selected node or edge.
                graphPanel.selectNode(null);
                graphPanel.selectEdge(null);
            }
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * Initiates node dragging if a node is selected.
     * @param e The MouseEvent containing the details of the press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Node pressedNode = graphPanel.getSelectedNode();
        if (pressedNode != null) {
            // Start dragging the selected node.
            graphPanel.startDraggingNode(x, y);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * Stops node dragging.
     * @param e The MouseEvent containing the details of the release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Stop dragging the node.
        graphPanel.stopDraggingNode();
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     * Drags the selected node to the new mouse position.
     * @param e The MouseEvent containing the details of the drag.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Drag the selected node to the new mouse position.
        graphPanel.dragSelectedNode(e.getX(), e.getY());
    }
}
