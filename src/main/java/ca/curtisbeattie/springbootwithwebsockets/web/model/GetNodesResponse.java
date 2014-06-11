package ca.curtisbeattie.springbootwithwebsockets.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude
public class GetNodesResponse {
    private List<Node> nodes;

    protected GetNodesResponse() {
    }

    public GetNodesResponse(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
