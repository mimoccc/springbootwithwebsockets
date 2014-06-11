package ca.curtisbeattie.springbootwithwebsockets.simulation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import rice.pastry.socket.SocketPastryNodeFactory;

/**
 * Created by cbeattie on 07/06/14.
 */
@Configuration
public class SimulationConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    public Simulation simulation(TaskExecutor taskExecutor,
                                 SocketPastryNodeFactory pastryNodeFactory,
                                 SimpMessagingTemplate messagingTemplate) {
        return new PastrySimulation(taskExecutor, pastryNodeFactory, messagingTemplate);
    }
}
