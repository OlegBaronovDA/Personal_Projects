package nl.rug.oop.rts.menuMVC.model.graph;

import java.awt.*;

/**
 * Utility class for handling position-related calculations.
 */
public class Position {

    /**
     * Calculates the distance from a point to a line defined by two points.
     * @param px The x-coordinate of the point.
     * @param py The y-coordinate of the point.
     * @param x1 The x-coordinate of the first point defining the line.
     * @param y1 The y-coordinate of the first point defining the line.
     * @param x2 The x-coordinate of the second point defining the line.
     * @param y2 The y-coordinate of the second point defining the line.
     * @return The distance from the point to the line.
     */
    public static double pointToLineDistance(int px, int py, int x1, int y1, int x2, int y2) {
        return Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) / Math.hypot(x2 - x1, y2 - y1);
    }

    /**
     * Calculates the initial position for a node.
     * @return A Point object representing the initial position.
     */
    public static Point calculateInitialNodePosition() {
        int x = 100;
        int y = 100;
        return new Point(x, y);
    }

}