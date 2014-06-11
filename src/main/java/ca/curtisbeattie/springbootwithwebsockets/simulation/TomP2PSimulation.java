package ca.curtisbeattie.springbootwithwebsockets.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public class TomP2PSimulation implements Simulation {
    private static final Logger logger = LoggerFactory.getLogger(TomP2PSimulation.class);
    private List<SimulationNode> simulationNodes = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TomP2PSimulation(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void start() throws IOException {
        logger.info("Starting Simulation");
        for(int i = 0; i < 5; ++i) {
            TomP2PSimulationNode simulationNode = new TomP2PSimulationNode();
            simulationNode.start(i, simulationNodes);
            simulationNodes.add(simulationNode);
        }
        messagingTemplate.convertAndSend("/topic/simulation/started", "STARTED");
    }

    @Override
    public void stop() {
        logger.info("Stopping Simulation");
    }

    @Override
    public List<SimulationNode> getNodes() {
        return null;
    }
}
