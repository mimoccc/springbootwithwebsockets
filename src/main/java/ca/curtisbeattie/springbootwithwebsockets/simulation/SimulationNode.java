package ca.curtisbeattie.springbootwithwebsockets.simulation;

import ca.curtisbeattie.springbootwithwebsockets.dht.DHTPeer;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public interface SimulationNode {

    void start(int id) throws IOException;
    void start(int id, List<? extends SimulationNode> peers) throws IOException, InterruptedException;
    void stop();

    String getId();
}
