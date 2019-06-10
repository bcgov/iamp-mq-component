package ca.bc.gov.mq.component.logic;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import ca.bc.gov.mq.component.MQComponent;
import ca.bc.gov.mq.component.exception.MQCommunicationException;
import ca.bc.gov.mq.component.exception.MQConnectionException;
import ca.bc.gov.mq.component.properties.MQConfigurationProperties;
import lombok.Getter;

@Component
@Getter
public class MQComponentLogic implements MQComponent {

	private ApplicationContext appContext;
	private JMSContext context;
	private Queue destination;
	private JMSProducer producer;
	private JMSConsumer consumer;
	
	private final String MQ_COMPONENT_CONTEXT_XML = "mq-component.xml";
	
	@Autowired
	MQConfigurationProperties configurationProperties;
	
	private void loadApplicationContext() {
		appContext = new ClassPathXmlApplicationContext(MQ_COMPONENT_CONTEXT_XML);
	}
	
	private void loadConfigurationProperties(String mq) {
		configurationProperties = (MQConfigurationProperties) appContext.getBean(mq);
	}
	
	public void connect(String mq) throws JMSException, MQConnectionException {
		this.loadApplicationContext();
		this.loadConfigurationProperties(mq);
		
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
		JmsConnectionFactory cf = ff.createConnectionFactory();

		cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, configurationProperties.getHost());
		cf.setIntProperty(WMQConstants.WMQ_PORT, configurationProperties.getPort());
		cf.setStringProperty(WMQConstants.WMQ_CHANNEL, configurationProperties.getChannel());
		cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
		cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, configurationProperties.getQueueManager());
		cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
		cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		cf.setStringProperty(WMQConstants.USERID, configurationProperties.getAppUser());
		cf.setStringProperty(WMQConstants.PASSWORD, configurationProperties.getAppPassword());

		context = cf.createContext();
		
		if (context == null) {
			throw new MQConnectionException();
		}
		
		destination = context.createQueue("queue:///" + configurationProperties.getQueueName());
		
		if (destination == null) {
			throw new MQConnectionException();
		}
			
	}

	public TextMessage send(String message) throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQConnectionException();
		}
		
		TextMessage textMessage = context.createTextMessage(message);
		producer = context.createProducer();
		
		if (producer == null) {
			throw new MQCommunicationException();
		}
		
		producer.send(destination, textMessage);
		return textMessage;
	}
	
	public Message consume() throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQConnectionException();
		}
		
		consumer = context.createConsumer(destination);
		
		if (consumer == null) {
			throw new MQCommunicationException();
		}
		
		Message receivedMessage = consumer.receive();
		return receivedMessage;
	}

	public String consumeText() throws MQConnectionException, MQCommunicationException {
		if (context == null || destination == null) {
			throw new MQConnectionException();
		}
		
		consumer = context.createConsumer(destination);
		
		if (consumer == null) {
			throw new MQCommunicationException();
		}
		
		String receivedMessage = consumer.receiveBody(String.class, 15000);
		return receivedMessage;
	}

}
