package ca.bc.gov.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingMessage {

	private IncomingMessageHeader header;
	private String body;
	
}
