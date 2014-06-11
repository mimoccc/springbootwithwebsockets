package ca.curtisbeattie.springbootwithwebsockets.dht;

import ca.curtisbeattie.springbootwithwebsockets.simulation.TomP2PSimulationNode;
import net.tomp2p.futures.FutureDHT;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;

/**
 * Created by cbeattie on 07/06/14.
 */
public class DefaultSimulationNodeDHTPeer implements DHTPeer {
    private final TomP2PSimulationNode simulationNode;

    public DefaultSimulationNodeDHTPeer(TomP2PSimulationNode simulationNode) {
        this.simulationNode = simulationNode;
    }

    @Override
    public byte[] get(String key) throws DHTKeyNotFoundException {
        FutureDHT futureDHT = simulationNode.getPeer().get(Number160.createHash(key)).start();
        futureDHT.awaitUninterruptibly();

        if(futureDHT.isSuccess()) {
            return futureDHT.getData().getData();
        }
        else {
            throw new DHTKeyNotFoundException(key);
        }
    }

    @Override
    public void put(String key, byte[] data) {
        simulationNode.getPeer().put(Number160.createHash(key)).setData(new Data(data)).start().awaitUninterruptibly();
    }
}
