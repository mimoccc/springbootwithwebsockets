package ca.curtisbeattie.springbootwithwebsockets.simulation;

import rice.pastry.NodeHandle;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by cbeattie on 08/06/14.
 */
public class PastrySimulationNode implements SimulationNode {
    private final SocketPastryNodeFactory pastryNodeFactory;
    private PastryNode pastryNode;

    public PastrySimulationNode(SocketPastryNodeFactory pastryNodeFactory) {
        this.pastryNodeFactory = pastryNodeFactory;
    }

    public PastryNode getPastryNode() {
        return pastryNode;
    }

    @Override
    public void start(int id) throws IOException {
        NodeHandle nodeHandle = pastryNodeFactory.generateNodeHandle(new InetSocketAddress(5000 + id));
        pastryNode = pastryNodeFactory.newNode(nodeHandle);
    }

    @Override
    public void start(int id, List<? extends SimulationNode> peers) throws IOException, InterruptedException {
        if(peers.isEmpty()) {
            start(id);
        }
        else {
            SimulationNode firstPeer = peers.get(0);
            NodeHandle nodeHandle = ((PastrySimulationNode)firstPeer).getPastryNode().getLocalHandle();
            pastryNode = pastryNodeFactory.newNode(nodeHandle);
            for(int i = 0; i < 5; ++i) {
                synchronized (pastryNode) {
                    if (pastryNode.isReady() && !pastryNode.joinFailed()) {
                        break;
                    }
                    pastryNode.wait(100);
                }
            }
            if(!pastryNode.isReady() || pastryNode.joinFailed()) {
                throw new IOException("Unable to join pastry ring");
            }
        }
    }

    @Override
    public void stop() {
        pastryNode.destroy();
    }

    @Override
    public String getId() {
        return getPastryNode().getId().toString();
    }
}
