package nl.rug.oop.rts.menuMVC.model.drawer;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Unit;
import nl.rug.oop.rts.menuMVC.model.graph.Edge;
import nl.rug.oop.rts.menuMVC.model.graph.Graph;
import nl.rug.oop.rts.menuMVC.model.graph.Node;
import nl.rug.oop.rts.menuMVC.simulationView.Combat;
import nl.rug.oop.rts.menuMVC.model.army.Faction;
import nl.rug.oop.rts.util.TextureLoader;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * The GraphDrawer class handles drawing the graph, including edges, armies on edges,
 * and combat effects on nodes and edges.
 */
public class GraphDrawer {
    // Map to store node locations
    private final Map<Node, Point> nodeLocations;
    private final TextureLoader textureLoader = TextureLoader.getInstance();
    // Graph instance
    private final Graph graph;

    /**
     * Constructor for GraphDrawer.
     * @param nodeLocations Map containing node locations.
     * @param graph The graph to be drawn.
     */
    public GraphDrawer(Map<Node, Point> nodeLocations, Graph graph) {
        this.nodeLocations = nodeLocations;
        this.graph = graph;
    }

    /**
     * Draws an edge on the graph.
     * @param g The Graphics context.
     * @param edge The edge to be drawn.
     * @param isSelected Whether the edge is selected.
     */
    public void drawEdge(Graphics g, Edge edge, boolean isSelected) {
        Point src = nodeLocations.get(edge.getSource());
        Point dest = nodeLocations.get(edge.getDestination());

        if (src != null && dest != null) {
            int offset = 25;
            g.setColor(isSelected ? Color.BLUE : Color.BLACK);
            g.drawLine(src.x + offset, src.y + offset, dest.x + offset, dest.y + offset);

            // Draw armies on the edge
            drawArmiesEdge(g, edge);
        }
    }

    /**
     * Draws the armies on the edge.
     * @param g The Graphics context.
     * @param edge The edge where the armies are located.
     */
    private void drawArmiesEdge(Graphics g, Edge edge) {
        List<Army> armies = edge.getArmies();
        if (armies == null || armies.isEmpty()) {
            return;
        }

        int armyCount = Math.min(armies.size(), 4);

        Point src = nodeLocations.get(edge.getSource());
        Point dest = nodeLocations.get(edge.getDestination());

        if (src != null && dest != null) {
            EdgeDrawer context = createEdgeDrawingContext(src, dest, armyCount, edge);

            for (int i = 0; i < armyCount; i++) {
                drawArmyOnEdge(g, armies.get(i), context, i);
            }
        }
    }

    /**
     * Creates the context for drawing armies on an edge.
     * @param src The source point of the edge.
     * @param dest The destination point of the edge.
     * @param armyCount The number of armies to draw.
     * @param edge The edge on which the armies are located.
     * @return An EdgeDrawer context containing drawing information.
     */
    private EdgeDrawer createEdgeDrawingContext(Point src, Point dest, int armyCount, Edge edge) {
        double distance = calculateDistance(src, dest);
        double step = calculateStep(distance, armyCount);
        boolean reverseDirection = determineDirection(edge);
        double perpAngle = calculatePerpendicularAngle(src, dest);

        return new EdgeDrawer(src, dest, distance, step, reverseDirection, perpAngle);
    }

    /**
     * Calculates the distance between two points.
     * @param src The source point.
     * @param dest The destination point.
     * @return The distance between the points.
     */
    private double calculateDistance(Point src, Point dest) {
        return Math.sqrt(Math.pow(dest.x - src.x, 2) + Math.pow(dest.y - src.y, 2));
    }

    /**
     * Calculates the step size for drawing armies along an edge.
     * @param distance The total distance of the edge.
     * @param armyCount The number of armies to draw.
     * @return The step size for placing armies along the edge.
     */
    private double calculateStep(double distance, int armyCount) {
        return distance / (armyCount + 1);
    }

    /**
     * Determines the direction of the edge based on the IDs of the source and destination nodes.
     * @param edge The edge to be evaluated.
     * @return True if the direction is reversed, false otherwise.
     */
    private boolean determineDirection(Edge edge) {
        return edge.getSource().getId() > edge.getDestination().getId();
    }

    /**
     * Calculates the perpendicular angle to the edge for offsetting army positions.
     * @param src The source point of the edge.
     * @param dest The destination point of the edge.
     * @return The perpendicular angle in radians.
     */
    private double calculatePerpendicularAngle(Point src, Point dest) {
        double edgeAngle = Math.atan2(dest.y - src.y, dest.x - src.x);
        return edgeAngle + Math.PI / 2;
    }

    /**
     * Draws an individual army on the edge.
     * @param g The Graphics context.
     * @param army The army to be drawn.
     * @param context The drawing context containing edge information.
     * @param index The index of the army in the list.
     */
    private void drawArmyOnEdge(Graphics g, Army army, EdgeDrawer context, int index) {
        int armyImageSize = 20;
        int health = army.getUnits().stream().mapToInt(Unit::getHealth).sum();
        Faction faction = army.getFaction();
        Image armyImage = NodeDrawer.getArmyImage(faction, armyImageSize);

        Point armyPosition = calculateArmyPosition(context, index, armyImageSize);
        drawArmyImageAndHealth(g, armyImage, health, armyPosition, armyImageSize);
    }

    /**
     * Calculates the position of an army on the edge.
     * @param context The drawing context containing edge information.
     * @param index The index of the army in the list.
     * @param armyImageSize The size of the army image.
     * @return The position of the army as a Point.
     */
    private Point calculateArmyPosition(EdgeDrawer context, int index, int armyImageSize) {
        double armyProgress = (index + 1) * context.getStep();
        if (context.isReverseDirection()) {
            armyProgress = context.getDistance() - armyProgress;
        }
        int armyX = (int) (context.getSrc().x + armyProgress * (context.getDest().x - context.getSrc().x)
                / context.getDistance());
        int armyY = (int) (context.getSrc().y + armyProgress * (context.getDest().y - context.getSrc().y)
                / context.getDistance());

        int offsetX = (int) (Math.cos(context.getPerpAngle()) * (index % 2 == 0 ? -1 : 1) * armyImageSize);
        int offsetY = (int) (Math.sin(context.getPerpAngle()) * (index % 2 == 0 ? -1 : 1) * armyImageSize);

        armyX += offsetX;
        armyY += offsetY;

        return new Point(armyX, armyY);
    }

    /**
     * Draws the army image and health value at the specified position.
     * @param g The Graphics context.
     * @param armyImage The image of the army.
     * @param health The total health of the army.
     * @param armyPosition The position to draw the army.
     * @param armyImageSize The size of the army image.
     */
    private void drawArmyImageAndHealth(Graphics g, Image armyImage, int health,
                                        Point armyPosition, int armyImageSize) {
        g.drawImage(armyImage, armyPosition.x - armyImageSize / 2, armyPosition.y - armyImageSize
                / 2, null);
        g.setColor(Color.RED);
        g.drawString("" + health, armyPosition.x, armyPosition.y - 5);
    }

    /**
     * Draws effects at a specified location.
     * @param g The Graphics context.
     * @param x The x-coordinate of the location.
     * @param y The y-coordinate of the location.
     * @return True if the drawing was successful, false otherwise.
     */
    public boolean drawEffects(Graphics g, int x, int y) {
        int effectSize = 50;
        Image effectImage = textureLoader.getTexture("flash", effectSize, effectSize);
        return g.drawImage(effectImage, x, y, null);
    }

    /**
     * Draws combat effects on nodes and edges where combat is happening.
     * @param g The Graphics context.
     */
    public void drawCombatEffects(Graphics g) {
        Combat combat = new Combat();
        for (Node node : graph.getNodes().values()) {
            if (combat.isCombatHappeningOnNode(node)) {
                Point location = nodeLocations.get(node);
                drawEffects(g, location.x, location.y);
            }
        }
        for (Edge edge : graph.getEdges().values()) {
            if (combat.isCombatHappeningOnEdge(edge)) {
                Point src = nodeLocations.get(edge.getSource());
                Point dest = nodeLocations.get(edge.getDestination());
                drawEffects(g, (src.x + dest.x) / 2, (src.y + dest.y) / 2);
            }
        }
    }
}