package ca.curtisbeattie.springbootwithwebsockets.simulation;

import ca.curtisbeattie.springbootwithwebsockets.dht.DHTPeer;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public interface Simulation {
    void start() throws IOException, InterruptedException;
    void stop();

    List<SimulationNode> getNodes();
}
