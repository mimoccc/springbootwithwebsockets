package ca.curtisbeattie.springbootwithwebsockets.simulation;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import rice.pastry.Id;
import rice.pastry.NodeHandle;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;

/**
 * Created by cbeattie on 08/06/14.
 */
public class PastrySimulationNode implements SimulationNode {
    private final SocketPastryNodeFactory pastryNodeFactory;
    private final SimpMessagingTemplate messagingTemplate;
    private PastryNode pastryNode;

    public PastrySimulationNode(SocketPastryNodeFactory pastryNodeFactory, SimpMessagingTemplate messagingTemplate) {
        this.pastryNodeFactory = pastryNodeFactory;
        this.messagingTemplate = messagingTemplate;
    }

    public PastryNode getPastryNode() {
        return pastryNode;
    }

    @Override
    public String getId() {
        return getPastryNode().getId().toStringFull();
    }

    @Override
    public List<String> getNeighborIds() {
        List<String> neighborIds = new ArrayList<>();
        for(int i = 0; i < pastryNode.getLeafSet().size(); ++i) {
            NodeHandle nodeHandle = pastryNode.getLeafSet().get(i);
            if(nodeHandle != null) {
                neighborIds.add(nodeHandle.getId().toStringFull());
            }
        }
        return neighborIds;
    }

    @Override
    public void start(int id) throws IOException, InterruptedException {
        start(id, new LinkedList<SimulationNode>());
    }

    @Override
    public void start(int id, List<? extends SimulationNode> peers) throws IOException, InterruptedException {
        if(peers.isEmpty()) {
            NodeHandle nodeHandle = pastryNodeFactory.generateNodeHandle(new InetSocketAddress(5000 + id));
            pastryNode = pastryNodeFactory.newNode(nodeHandle);
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
        sendNodeStartedMessage(pastryNode.getNodeId());
    }

    @Override
    public void stop() {
        pastryNode.destroy();
    }

    private void sendNodeStartedMessage(Id nodeId) {
        messagingTemplate.convertAndSend("/topic/simulation/node/started", nodeId.toStringFull());
    }
}
