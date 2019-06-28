package ca.bc.gov.iamp.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MQServiceOutputMessage {

	private OutputMessageHeader header;
	private String body;

	@Getter
	@Setter
	public class OutputMessageHeader {
		private String direction;
		private String receiverID;
		private String senderID;
		private String correlationID;
		private String responseMessageID;
		private String panMessageID;
		private String nanMessageID;
		private String errMessageID;
		private String timeStamp;
		private String udf;
	}
}
