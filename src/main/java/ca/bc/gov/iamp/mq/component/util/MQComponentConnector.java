package ca.bc.gov.iamp.mq.component.util;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;
import ca.bc.gov.iamp.mq.component.properties.MQConfigurationFile;
import ca.bc.gov.iamp.mq.component.properties.MQConfigurationProperties;

@Component
public class MQComponentConnector {

	private ApplicationContext appContext;

	@Autowired
	MQConfigurationFile configurationFile;

	@Autowired
	MQConfigurationProperties configurationProperties;

	private void loadApplicationContext() {
		appContext = new ClassPathXmlApplicationContext(configurationFile.getFilename());
	}

	private MQConfigurationProperties loadConfigurationProperties(String mq) {
		return (MQConfigurationProperties) appContext.getBean(mq);
	}

	public JMSContext createConnectionContext(String mq) throws JMSException {
		this.loadApplicationContext();
		configurationProperties = loadConfigurationProperties(mq);

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

		return cf.createContext();
	}

	public Queue getConnectionDestination(JMSContext context) throws MQConnectionException {
		Queue destination = context.createQueue("queue:///" + configurationProperties.getQueueName());
			
		if (destination == null) {
			throw new MQConnectionException();
		}
		
		return destination;
	}
}
