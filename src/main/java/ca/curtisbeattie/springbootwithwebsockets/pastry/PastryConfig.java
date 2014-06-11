package ca.curtisbeattie.springbootwithwebsockets.pastry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rice.environment.Environment;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNodeFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import java.io.IOException;

/**
 * Created by cbeattie on 08/06/14.
 */
@Configuration
public class PastryConfig {
    @Bean
    public Environment environment() {
        Environment environment = new Environment();
        environment.getParameters().setString("nat_search_policy", "never");
        return environment;
    }

    @Bean
    public NodeIdFactory nodeIdFactory(Environment env) {
        return new RandomNodeIdFactory(env);
    }

    @Bean
    public SocketPastryNodeFactory pastryNodeFactory(Environment env) throws IOException {
        return new SocketPastryNodeFactory(nodeIdFactory(env), 5000, env);
    }
}