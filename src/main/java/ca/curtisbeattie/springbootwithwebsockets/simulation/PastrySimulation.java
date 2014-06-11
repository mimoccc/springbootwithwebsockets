package ca.curtisbeattie.springbootwithwebsockets.simulation;

import com.google.common.collect.ImmutableList;
import rice.pastry.NodeHandle;
import rice.pastry.PastryNodeFactory;
import rice.pastry.leafset.LeafSet;
import rice.pastry.socket.SocketPastryNodeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbeattie on 08/06/14.
 */
public class PastrySimulation implements Simulation {
    private static final int NODE_COUNT = 5;
    private final List<PastrySimulationNode> nodeList = new ArrayList<>();
    private final SocketPastryNodeFactory pastryNodeFactory;

    public PastrySimulation(SocketPastryNodeFactory pastryNodeFactory) {
        this.pastryNodeFactory = pastryNodeFactory;
    }

    @Override
    public void start() throws IOException, InterruptedException {
        for(int i = 0; i < NODE_COUNT; ++i) {
            PastrySimulationNode node = new PastrySimulationNode(pastryNodeFactory);
            node.start(i, nodeList);
            nodeList.add(node);
        }

        LeafSet leafSet = nodeList.get(0).getPastryNode().getLeafSet();
        for(int i = 0; i < leafSet.size(); ++i) {
            NodeHandle nodeHandle = leafSet.get(i);
            System.out.println("Leaf: " + nodeHandle);
        }
    }

    @Override
    public void stop() {
        for(PastrySimulationNode node : nodeList) {
            node.stop();
        }
        nodeList.clear();
    }

    @Override
    public List<SimulationNode> getNodes() {
        return ImmutableList.<SimulationNode>copyOf(nodeList);
    }
}
