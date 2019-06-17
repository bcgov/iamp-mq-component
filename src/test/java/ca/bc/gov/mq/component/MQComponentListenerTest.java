package ca.bc.gov.mq.component;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.annotation.IntegrationComponentScan;

import ca.bc.gov.mq.component.exception.MQCommunicationException;
import ca.bc.gov.mq.component.exception.MQConnectionException;
import ca.bc.gov.mq.component.logic.MQComponentLogic;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@SpringBootConfiguration
@IntegrationComponentScan
public class MQComponentListenerTest {
	
	private String message = "Sample message";
	
	@Mock
	private JMSContext context;
	
	@Mock
	private Queue destination;
	
	@Mock
	private JMSProducer producer;
	
	@Mock
	private TextMessage textMessage;
	
	@InjectMocks
	private MQComponentLogic validComponent;

	@Test
	public void testSend() throws MQConnectionException, MQCommunicationException {

		//when(context.createTextMessage(message)).thenReturn(mqComponent.getContext().createTextMessage(message));
		when(context.createTextMessage(message)).thenReturn(textMessage);
		when(context.createProducer()).thenReturn(producer);
		//when(producer.send(destination, textMessage))
		validComponent.send(message);
		//verify(validComponent).send(message);

	}
	
//	@Test
//	public void testConsume() throws MQConnectionException, MQCommunicationException {
//
//		when(mqComponent.send(sampleMessage)).thenReturn(mqComponent.getContext().createTextMessage(sampleMessage));
//		mqComponent.send(sampleMessage);
//		verify(mqComponent).consume();
//
//	}

}
