package nl.rug.oop.rts.menuMVC.messageView;

import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;
import nl.rug.oop.rts.menuMVC.PanelObserver;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for managing the details panel.
 */
public class DetailsPanelManager implements PanelObserver {
    private final JPanel detailsPanel = new JPanel(new BorderLayout());
    private final JTextArea detailsTextArea = new JTextArea();
    private GraphPanel graphPanel;

    /**
     * Sets up the details panel within a split pane.
     *
     * @param graphPanel The graph panel to which the details panel will be added.
     * @return The JSplitPane containing the details panel.
     */
    public JSplitPane setupDetailsPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        detailsTextArea.setEditable(false);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JPanel(), detailsPanel);
        splitPane.setDividerLocation(1);
        splitPane.setResizeWeight(1);
        return splitPane;
    }

    /**
     * Updates the details panel based on the selected node or edge.
     */
    public void updateDetailsPanel() {
        Node selectedNode = graphPanel.getSelectedNode();
        Edge selectedEdge = graphPanel.getSelectedEdge();

        if (selectedNode != null) {
            detailsTextArea.setText("Node Name: " + selectedNode.getName());
        } else if (selectedEdge != null) {
            detailsTextArea.setText("Edge Name: " + selectedEdge.getName() +
                    "\nNode 1: " + selectedEdge.getSource().getName() +
                    "\nNode 2: " + selectedEdge.getDestination().getName());
        } else {
            detailsTextArea.setText("");
        }
    }

    /**
     * Called to update the details panel when the graph changes.
     */
    @Override
    public void update() {
        updateDetailsPanel();
    }
}