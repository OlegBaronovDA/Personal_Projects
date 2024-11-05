package nl.rug.oop.rts.menuMVC.model.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a node in a graph.
 */
public class Node extends GraphAgent {
    private List<Edge> edges;

    /**
     * Constructor for creating a node with specified ID and name.
     * @param id The ID of the node.
     * @param name The name of the node.
     */
    public Node(int id, String name) {
        super(id, name);
        this.edges = new ArrayList<>();
    }

    /**
     * Method to add an edge to the node.
     * @param edge The edge to add.
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Method to remove an edge from the node.
     * @param edge The edge to remove.
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    /**
     * Method to get the list of edges connected to the node.
     * @return The list of edges connected to the node.
     */
    public List<Edge> getEdges() {
        return edges;
    }
}