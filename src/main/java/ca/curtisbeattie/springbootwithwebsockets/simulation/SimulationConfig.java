package ca.curtisbeattie.springbootwithwebsockets.simulation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rice.pastry.socket.SocketPastryNodeFactory;

/**
 * Created by cbeattie on 07/06/14.
 */
@Configuration
public class SimulationConfig {
    @Bean
    public Simulation simulation(SocketPastryNodeFactory pastryNodeFactory) {
        return new PastrySimulation(pastryNodeFactory);
    }
}
