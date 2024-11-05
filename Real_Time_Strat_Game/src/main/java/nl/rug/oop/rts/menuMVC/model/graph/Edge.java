package nl.rug.oop.rts.menuMVC.model.graph;

/**
 * Class representing an edge in a graph.
 */
public class Edge extends GraphAgent {
    private Node source;
    private Node destination;
    private Edge cloneEdge;

    /**
     * Constructor for creating an edge with specified ID, name, source, and destination nodes.
     * @param id The ID of the edge.
     * @param name The name of the edge.
     * @param source The source node of the edge.
     * @param destination The destination node of the edge.
     */
    public Edge(int id, String name, Node source, Node destination) {
        super(id, name);
        this.source = source;
        this.destination = destination;
    }

    /**
     * Method to set the clone edge.
     * @param cloneEdge The clone edge to set.
     */
    public void setCloneEdge(Edge cloneEdge) {
        this.cloneEdge = cloneEdge;
    }

    /**
     * Method to get the destination node of the edge.
     * @return The destination node of the edge.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Method to get the source node of the edge.
     * @return The source node of the edge.
     */
    public Node getSource() {
        return source;
    }

    /**
     * Method to get the clone edge.
     * @return The clone edge.
     */
    public Edge getCloneEdge() {
        return this.cloneEdge;
    }
}