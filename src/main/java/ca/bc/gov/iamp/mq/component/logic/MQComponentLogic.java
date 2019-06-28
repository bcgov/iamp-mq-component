package ca.bc.gov.iamp.mq.component.logic;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.MQComponent;
import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;
import ca.bc.gov.iamp.mq.component.util.MQComponentConnector;
import lombok.Getter;

@Component
@Getter
public class MQComponentLogic implements MQComponent {

	private JMSContext context;
	private Queue destination;
	private JMSConsumer consumer;
	
	@Autowired
	MQComponentConnector mqComponentConnector;
	
	public void connect(String mq) throws JMSException, MQConnectionException {
		context = mqComponentConnector.createConnectionContext(mq);
		destination = mqComponentConnector.getConnectionDestination(context);
	}

	public TextMessage send(String message) throws MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		TextMessage textMessage = context.createTextMessage(message);
		JMSProducer producer = context.createProducer();
		
		if (textMessage == null || producer == null) {
			throw new MQCommunicationException();
		}
		
		producer.send(destination, textMessage);
		return textMessage;
	}
	
	public Message consume() throws MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		consumer = context.createConsumer(destination);
		return consumer.receive();
	}

	public String consumeText() throws MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		consumer = context.createConsumer(destination);
		return consumer.receiveBody(String.class, 15000);
	}

}
