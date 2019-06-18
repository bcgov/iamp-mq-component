package ca.bc.gov.iamp.mq.component.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class MQConfigurationFile {
	
	@Value("${mq.component.conf.file:mq-component.xml}")
	public String filename; // Configuration XML filename
	
}
