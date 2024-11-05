package nl.rug.oop.rts.menuMVC.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a graph consisting of nodes and edges.
 */
public class Graph {
    private Map<Integer, Node> nodes;
    private Map<Integer, Edge> edges;

    /**
     * Constructs a Graph object with empty nodes and edges maps.
     */
    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    /**
     * Adds a node to the graph.
     * @param node The node to add.
     */
    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    /**
     * Removes a node from the graph.
     * @param node The node to remove.
     */
    public void removeNode(Node node) {
        nodes.remove(node.getId());

        // Collect edges to be removed
        List<Edge> edgesToRemove = new ArrayList<>(node.getEdges());

        // Remove collected edges
        for (Edge edge : edgesToRemove) {
            removeEdge(edge);
        }
    }

    /**
     * Adds an edge to the graph.
     * @param edge The edge to add.
     */
    public void addEdge(Edge edge) {
        // Check if the edge already exists
        if (containsSimilarEdge(edge)) {
            return; // Prevent adding the edge
        }

        edges.put(edge.getId(), edge);
        // Add edge to source and destination nodes
        edge.getSource().addEdge(edge);
        edge.getDestination().addEdge(edge);
    }

    /**
     * Checks if a similar edge already exists in the graph.
     * @param newEdge The new edge to check.
     * @return True if a similar edge exists, false otherwise.
     */
    private boolean containsSimilarEdge(Edge newEdge) {
        for (Edge existingEdge : edges.values()) {
            if (existingEdge.getSource() == newEdge.getSource() &&
                    existingEdge.getDestination() == newEdge.getDestination()) {
                return true; // Similar edge already exists
            }
        }
        return false; // No similar edge found
    }

    /**
     * Removes an edge from the graph.
     * @param edge The edge to remove.
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge.getId());
        // Remove edge from source and destination nodes
        edge.getSource().removeEdge(edge);
        edge.getDestination().removeEdge(edge);
    }

    /**
     * Gets the map of edges in the graph.
     * @return The map of edges.
     */
    public Map<Integer, Edge> getEdges() {
        return edges;
    }

    /**
     * Gets the map of nodes in the graph.
     * @return The map of nodes.
     */
    public Map<Integer, Node> getNodes() {
        return nodes;
    }
}