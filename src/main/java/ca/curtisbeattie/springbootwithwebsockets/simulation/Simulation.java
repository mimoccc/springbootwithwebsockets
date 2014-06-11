package ca.curtisbeattie.springbootwithwebsockets.simulation;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public interface Simulation {
    boolean isStarted();
    void start() throws IOException, InterruptedException;
    void stop();

    List<SimulationNode> getNodes();
}
