package ca.curtisbeattie.springbootwithwebsockets.dht;

/**
 * Created by cbeattie on 07/06/14.
 */
public class DHTKeyNotFoundException extends Exception {
    private final String key;

    public DHTKeyNotFoundException(String key) {
        super(String.format("DHT entry for key %s not found", key));
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}