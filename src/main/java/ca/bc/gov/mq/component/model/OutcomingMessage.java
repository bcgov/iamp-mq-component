package ca.bc.gov.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutcomingMessage {

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
		private String PANMessageID;
		private String NANMessageID;
		private String ERRMessageID;
		private String timeStamp;
		private String UDF;
	}
}
