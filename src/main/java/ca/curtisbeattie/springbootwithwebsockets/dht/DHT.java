package ca.curtisbeattie.springbootwithwebsockets.dht;

/**
 * Created by cbeattie on 07/06/14.
 */
public interface DHT {
    byte[] get(String key) throws DHTKeyNotFoundException;
    void put(String key, byte[] data);
}
