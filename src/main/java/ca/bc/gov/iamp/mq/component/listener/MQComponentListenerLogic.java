package ca.bc.gov.iamp.mq.component.listener;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;
import ca.bc.gov.iamp.mq.component.util.MQComponentConnector;
import lombok.Getter;

@Component
@Getter
public class MQComponentListenerLogic implements MQComponentListener {

	private JMSContext context;
	private Queue destination;
	private JMSConsumer consumer;
	
	@Autowired
	MQComponentConnector mqComponentConnector;

	public void connect(String mq) throws JMSException, MQConnectionException {
		context = mqComponentConnector.createConnectionContext(mq);
		destination = mqComponentConnector.getConnectionDestination(context);
	}
	
	public void listen(MessageListener listener) throws MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQCommunicationException();
		}
		
		consumer = context.createConsumer(destination);
		consumer.setMessageListener(listener);
	}

}
