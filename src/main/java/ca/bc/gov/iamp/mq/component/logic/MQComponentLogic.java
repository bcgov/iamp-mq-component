package ca.bc.gov.iamp.mq.component.logic;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.MQComponent;
import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;
import ca.bc.gov.iamp.mq.component.util.MQComponentConnector;
import lombok.Getter;

@Component
@Getter
public class MQComponentLogic implements MQComponent {

	private ApplicationContext appContext;
	private JMSContext context;
	private Queue destination;
	private JMSConsumer consumer;
	
	@Autowired
	MQComponentConnector mqComponentConnector;
	
	public void connect(String mq) throws JMSException, MQConnectionException {
		context = mqComponentConnector.createConnectionContext(mq);
		destination = mqComponentConnector.getConnectionDestination(context);
	}

	public TextMessage send(String message) throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQConnectionException();
		}
		
		TextMessage textMessage = context.createTextMessage(message);
		JMSProducer producer = context.createProducer();
		
		if (textMessage == null || producer == null) {
			throw new MQCommunicationException();
		}
		
		producer.send(destination, textMessage);
		return textMessage;
	}
	
	public Message consume() throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		consumer = context.createConsumer(destination);
		return consumer.receive();
	}

	public String consumeText() throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		consumer = context.createConsumer(destination);
		return consumer.receiveBody(String.class, 15000);
	}

}
