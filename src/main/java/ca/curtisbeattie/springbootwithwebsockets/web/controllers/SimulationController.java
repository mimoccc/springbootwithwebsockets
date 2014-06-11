package ca.curtisbeattie.springbootwithwebsockets.web.controllers;

import ca.curtisbeattie.springbootwithwebsockets.simulation.Simulation;
import ca.curtisbeattie.springbootwithwebsockets.simulation.SimulationNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cbeattie on 07/06/14.
 */
@RestController
public class SimulationController {
    private final Simulation simulation;

    @Autowired
    public SimulationController(Simulation simulation) {
        this.simulation = simulation;
    }

    public static class StartSimulationRequest {

    }

    public static class StartSimulationResponse {
        private final boolean simulationStarted;

        public StartSimulationResponse(boolean simulationStarted) {
            this.simulationStarted = simulationStarted;
        }

        public boolean isSimulationStarted() {
            return simulationStarted;
        }
    }

    @RequestMapping(value = "/api/simulation/start", method = RequestMethod.POST)
    public StartSimulationResponse startSimulation(StartSimulationRequest request) throws IOException, InterruptedException {
        simulation.start();
        return new StartSimulationResponse(true);
    }

    public static class Node {
        private String id;

        protected Node() {
        }

        public Node(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    @RequestMapping(value = "/api/pastry/nodes", method = RequestMethod.GET)
    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        for(SimulationNode simulationNode : simulation.getNodes()) {
            Node node = new Node(simulationNode.getId());
            nodes.add(node);
        }
        return nodes;
    }
}
