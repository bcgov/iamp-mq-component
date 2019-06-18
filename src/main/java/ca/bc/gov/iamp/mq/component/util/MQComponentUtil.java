package ca.bc.gov.iamp.mq.component.util;

import javax.jms.JMSException;
import javax.jms.Message;

import ca.bc.gov.iamp.mq.component.model.IncomingMessage;
import ca.bc.gov.iamp.mq.component.model.IncomingMessageHeader;

public class MQComponentUtil {

	@SuppressWarnings("unused")
	private IncomingMessage fromJMSMessage(Message JMSMessage) throws JMSException {
		IncomingMessageHeader header = new IncomingMessageHeader();
		header.setPriority(JMSMessage.getJMSPriority());
		
		IncomingMessage message = new IncomingMessage();
		message.setHeader(header);
		message.setBody(JMSMessage.getBody(String.class));
		return message;
	}
	
}
