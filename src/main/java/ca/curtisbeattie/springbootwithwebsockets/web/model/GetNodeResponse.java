package ca.curtisbeattie.springbootwithwebsockets.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class GetNodeResponse {
    private Node node;

    protected GetNodeResponse() {
    }

    public GetNodeResponse(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
