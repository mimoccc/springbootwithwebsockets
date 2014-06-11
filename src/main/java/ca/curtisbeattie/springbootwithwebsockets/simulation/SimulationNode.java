package ca.curtisbeattie.springbootwithwebsockets.simulation;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public interface SimulationNode {

    void start(int id) throws IOException, InterruptedException;
    void start(int id, List<? extends SimulationNode> peers) throws IOException, InterruptedException;
    void stop();

    String getId();
    List<String> getNeighborIds();
}
