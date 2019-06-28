package ca.bc.gov.iamp.mq.component.properties;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class MQConfigurationServiceProperties {
	
	private String url; // Host name or IP address of MQ service
	private int port; // Listener port for your MQ service
	private String sendPath; // Send service path
	private String consumePath; // Consume service name
	
}
