package ca.curtisbeattie.springbootwithwebsockets.simulation;

import ca.curtisbeattie.springbootwithwebsockets.dht.DHTPeer;
import ca.curtisbeattie.springbootwithwebsockets.dht.DefaultSimulationNodeDHTPeer;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by cbeattie on 07/06/14.
 */
public class TomP2PSimulationNode implements SimulationNode {
    private static final Logger logger = LoggerFactory.getLogger(TomP2PSimulationNode.class);
    private Peer peer;
    private final DHTPeer dhtPeer;

    public TomP2PSimulationNode() {
        dhtPeer = new DefaultSimulationNodeDHTPeer(this);
    }

    public Peer getPeer() {
        return peer;
    }

    public PeerAddress getPeerAddress() {
        return peer.getPeerAddress();
    }

    public DHTPeer getDHTPeer() {
        return dhtPeer;
    }

    @Override
    public void start(int id) throws IOException {
        int port = 4000 + id;
        peer = new PeerMaker(Number160.createHash(id)).setPorts(port).makeAndListen();
        logger.info("Starting simulation node {} on port {}", id, port);
    }

    @Override
    public void start(int id, List<? extends SimulationNode> peers) throws IOException {
        start(id);
        for(SimulationNode node : peers) {
            TomP2PSimulationNode defaultNode = (TomP2PSimulationNode)node;
            PeerAddress peerAddress = defaultNode.getPeerAddress();
            logger.info("Trying to bootstrap with {}", peerAddress);
            FutureBootstrap bootstrapFuture = peer.bootstrap().setPeerAddress(peerAddress).start();
            bootstrapFuture.awaitUninterruptibly();
            if(bootstrapFuture.getBootstrapTo() != null) {
                logger.info("Bootstrapping complete");
                break;
            }
        }
    }

    @Override
    public void stop() {
        peer.shutdown();
    }

    @Override
    public String getId() {
        return null;
    }
}
