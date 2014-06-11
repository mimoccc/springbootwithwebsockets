package ca.curtisbeattie.springbootwithwebsockets.simulation;

import com.google.common.collect.ImmutableList;

import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rice.pastry.socket.SocketPastryNodeFactory;

/**
 * Created by cbeattie on 08/06/14.
 */
public class PastrySimulation implements Simulation {
    private static final int NODE_COUNT = 60;
    private final List<PastrySimulationNode> nodeList = new ArrayList<>();
    private final TaskExecutor taskExecutor;
    private final SocketPastryNodeFactory pastryNodeFactory;
    private final SimpMessagingTemplate messagingTemplate;
    private boolean started;

    public PastrySimulation(TaskExecutor taskExecutor,
                            SocketPastryNodeFactory pastryNodeFactory,
                            SimpMessagingTemplate messagingTemplate) {
        this.taskExecutor = taskExecutor;
        this.pastryNodeFactory = pastryNodeFactory;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public synchronized void start() throws IOException, InterruptedException {
        if(started) {
            return;
        }

        started = true;
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < NODE_COUNT; ++i) {
                    PastrySimulationNode node = new PastrySimulationNode(pastryNodeFactory, messagingTemplate);
                    try {
                        node.start(i, nodeList);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    synchronized (nodeList) {
                        nodeList.add(node);
                    }
                }
            }
        });
    }

    @Override
    public synchronized void stop() {
        if(!started) {
            return;
        }

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (nodeList) {
                    for(PastrySimulationNode node : nodeList) {
                        node.stop();
                    }
                    nodeList.clear();
                }
            }
        });

        started = false;
    }

    @Override
    public List<SimulationNode> getNodes() {
        synchronized (nodeList) {
            return ImmutableList.<SimulationNode>copyOf(nodeList);
        }
    }
}
