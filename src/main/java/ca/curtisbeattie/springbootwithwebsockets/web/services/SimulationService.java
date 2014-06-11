package ca.curtisbeattie.springbootwithwebsockets.web.services;

import java.io.IOException;

import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodeResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodesResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationRequest;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationResponse;

public interface SimulationService {
    StartSimulationResponse start(StartSimulationRequest request) throws IOException, InterruptedException;

    GetNodesResponse getNodes();

    GetNodeResponse getNode(String id);
}
