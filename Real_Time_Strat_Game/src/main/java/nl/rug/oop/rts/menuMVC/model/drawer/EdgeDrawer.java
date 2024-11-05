package nl.rug.oop.rts.menuMVC.model.drawer;

import java.awt.*;

/**
 * The EdgeDrawer class encapsulates the parameters and calculations
 * required for drawing armies on an edge in the graph.
 */
public class EdgeDrawer {
    private Point src; // the source point
    private Point dest; // destination point
    private double distance;
    private double step;
    private boolean reverseDirection;
    private double perpAngle;

    /**
     * Constructor for EdgeDrawer.
     * @param src              The source point of the edge.
     * @param dest             The destination point of the edge.
     * @param distance         The distance between the source and destination points.
     * @param step             The step size for positioning armies along the edge.
     * @param reverseDirection Indicates if the direction is reversed.
     * @param perpAngle        The perpendicular angle to the edge.
     */
    public EdgeDrawer(Point src, Point dest, double distance, double step, boolean reverseDirection, double perpAngle) {
        this.src = src;
        this.dest = dest;
        this.distance = distance;
        this.step = step;
        this.reverseDirection = reverseDirection;
        this.perpAngle = perpAngle;
    }

    /**
     * Gets the source point of the edge.
     * @return The source point.
     */
    public Point getSrc() {
        return src;
    }

    /**
     * Sets the source point of the edge.
     * @param src The source point to set.
     */
    public void setSrc(Point src) {
        this.src = src;
    }

    /**
     * Gets the destination point of the edge.
     * @return The destination point.
     */
    public Point getDest() {
        return dest;
    }

    /**
     * Sets the destination point of the edge.
     * @param dest The destination point to set.
     */
    public void setDest(Point dest) {
        this.dest = dest;
    }

    /**
     * Gets the distance between the source and destination points.
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the distance between the source and destination points.
     * @param distance The distance to set.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Gets the step size for positioning armies along the edge.
     * @return The step size.
     */
    public double getStep() {
        return step;
    }

    /**
     * Sets the step size for positioning armies along the edge.
     * @param step The step size to set.
     */
    public void setStep(double step) {
        this.step = step;
    }

    /**
     * Checks if the direction is reversed.
     * @return True if the direction is reversed, false otherwise.
     */
    public boolean isReverseDirection() {
        return reverseDirection;
    }

    /**
     * Sets the reverse direction flag.
     * @param reverseDirection The reverse direction flag to set.
     */
    public void setReverseDirection(boolean reverseDirection) {
        this.reverseDirection = reverseDirection;
    }

    /**
     * Gets the perpendicular angle to the edge.
     * @return The perpendicular angle.
     */
    public double getPerpAngle() {
        return perpAngle;
    }

    /**
     * Sets the perpendicular angle to the edge.
     * @param perpAngle The perpendicular angle to set.
     */
    public void setPerpAngle(double perpAngle) {
        this.perpAngle = perpAngle;
    }
}