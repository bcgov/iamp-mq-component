package ca.bc.gov.iamp.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingMessage {

	private IncomingMessageHeader header;
	private String body;
	
}
