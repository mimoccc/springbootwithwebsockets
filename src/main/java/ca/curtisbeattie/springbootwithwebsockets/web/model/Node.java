package ca.curtisbeattie.springbootwithwebsockets.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude
public class Node {
    private String id;
    private List<String> neighbors;

    protected Node() {
    }

    public Node(String id, List<String> neighbors) {
        this.id = id;
        this.neighbors = neighbors;
    }

    public String getId() {
        return id;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }
}
