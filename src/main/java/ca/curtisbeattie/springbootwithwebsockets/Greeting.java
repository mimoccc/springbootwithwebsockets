package ca.curtisbeattie.springbootwithwebsockets;

/**
 * Created by cbeattie on 31/05/14.
 */
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
