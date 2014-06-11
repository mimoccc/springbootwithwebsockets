package ca.curtisbeattie.springbootwithwebsockets.web.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.curtisbeattie.springbootwithwebsockets.simulation.Simulation;
import ca.curtisbeattie.springbootwithwebsockets.simulation.SimulationNode;
import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodeResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodesResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.Node;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationRequest;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.services.SimulationService;

@Service
public class SimulationServiceImpl implements SimulationService {
    private final Simulation simulation;

    @Autowired
    public SimulationServiceImpl(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public StartSimulationResponse start(StartSimulationRequest request) throws IOException, InterruptedException {
        simulation.start();
        return new StartSimulationResponse();
    }

    @Override
    public GetNodesResponse getNodes() {
        List<Node> nodes = new ArrayList<>();
        synchronized (simulation) {
            for(SimulationNode simulationNode : simulation.getNodes()) {
                nodes.add(create(simulationNode));
            }
        }
        return new GetNodesResponse(nodes);
    }

    @Override
    public GetNodeResponse getNode(String id) {
        Node node = null;
        synchronized (simulation) {
            for(SimulationNode simulationNode : simulation.getNodes()) {
                if(simulationNode.getId().equals(id)) {
                    node = create(simulationNode);
                }
            }
        }
        return new GetNodeResponse(node);
    }

    private static Node create(SimulationNode simulationNode) {
        return new Node(simulationNode.getId(), simulationNode.getNeighborIds());
    }
}
