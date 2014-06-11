package ca.curtisbeattie.springbootwithwebsockets.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodeResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.GetNodesResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationRequest;
import ca.curtisbeattie.springbootwithwebsockets.web.model.StartSimulationResponse;
import ca.curtisbeattie.springbootwithwebsockets.web.services.SimulationService;

/**
 * Created by cbeattie on 07/06/14.
 */
@RestController
public class SimulationController {
    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @RequestMapping(value = "/api/simulation/start", method = RequestMethod.POST)
    public StartSimulationResponse startSimulation(StartSimulationRequest request) throws IOException, InterruptedException {
        return simulationService.start(request);
    }

    @RequestMapping(value = "/api/pastry/nodes", method = RequestMethod.GET)
    public GetNodesResponse getNodes() {
        return simulationService.getNodes();
    }

    @RequestMapping(value = "/api/pastry/nodes/{id}", method = RequestMethod.GET)
    public GetNodeResponse getNode(@PathVariable String id) {
        return simulationService.getNode(id);
    }
}
