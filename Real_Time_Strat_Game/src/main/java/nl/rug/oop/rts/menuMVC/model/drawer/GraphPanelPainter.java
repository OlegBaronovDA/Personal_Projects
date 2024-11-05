package nl.rug.oop.rts.menuMVC.model.drawer;

import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;
import nl.rug.oop.rts.util.TextureLoader;

import java.awt.*;

/**
 * The GraphPanelPainter class is responsible for painting the GraphPanel components.
 */
public class GraphPanelPainter {
    private final GraphPanel graphPanel;
    private final GraphDrawer graphDrawer;
    private final NodeDrawer nodeDrawer;
    private final TextureLoader textureLoader;

    /**
     * Constructs a GraphPanelPainter object.
     * @param graphPanel The GraphPanel to be painted.
     */
    public GraphPanelPainter(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        this.graphDrawer = graphPanel.getGraphDrawer();
        this.nodeDrawer = graphPanel.getNodeDrawer();
        this.textureLoader = graphPanel.getTextureLoader();
    }

    /**
     * Paints the components of the graph panel.
     * @param g The Graphics object used for painting.
     */
    public void paintComponent(Graphics g) {
        Image backgroundImage = textureLoader.getTexture("mapTexture", graphPanel.getWidth(), graphPanel.getHeight());
        g.drawImage(backgroundImage, 0, 0, graphPanel);
        drawEdges(g);
        drawNodes(g);
        graphDrawer.drawCombatEffects(g);
    }

    /**
     * Draws the edges of the graph.
     * @param g The Graphics object used for drawing.
     */
    private void drawEdges(Graphics g) {
        graphPanel.getGraph().getEdges().values().forEach(edge -> {
            boolean isSelected = graphPanel.getSelectedEdge() == edge ||
                    (graphPanel.getSelectedEdge() != null && graphPanel.getSelectedEdge().getCloneEdge() == edge);
            graphDrawer.drawEdge(g, edge, isSelected);
        });
    }

    /**
     * Draws the nodes of the graph.
     * @param g The Graphics object used for drawing.
     */
    private void drawNodes(Graphics g) {
        graphPanel.getGraph().getNodes().forEach((nodeId, node) -> {
            Point location = graphPanel.getNodeLocations().get(node);
            if (location != null) {
                nodeDrawer.drawNode(g, node, location, node == graphPanel.getSelectedNode());
            }
        });
    }
}