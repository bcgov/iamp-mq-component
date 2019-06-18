package ca.bc.gov.iamp.mq.component.properties;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class MQConfigurationProperties {
	
	private String host; // Host name or IP address
	private int port; // Listener port for your queue manager
	private String channel; // Channel name
	private String queueManager; // Queue manager name
	private String appUser; // User name that application uses to connect to MQ
	private String appPassword; // Password that the application uses to connect to MQ
	private String queueName; // Queue that the application uses to put and get messages to and from
	
}
