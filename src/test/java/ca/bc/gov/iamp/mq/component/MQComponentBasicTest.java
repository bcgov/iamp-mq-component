package ca.bc.gov.iamp.mq.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
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

import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;
import ca.bc.gov.iamp.mq.component.logic.MQComponentLogic;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@SpringBootConfiguration
@IntegrationComponentScan
public class MQComponentBasicTest {
	
	private String sentMessageStr = "Sample message";
	
	private String consumedMessageStr = "Sample response";
	
	@Mock
	private JMSContext context;
	
	@Mock
	private Queue destination;
	
	@Mock
	private JMSProducer producer;
	
	@Mock
	private JMSConsumer consumer;
	
	@Mock
	private TextMessage mockTextMessage;
	
	@Mock
	private Message mockMessage;
	
	@InjectMocks
	private MQComponentLogic validComponent;

	@Test
	public void testSend() {

		when(context.createTextMessage(sentMessageStr)).thenReturn(mockTextMessage);
		when(context.createProducer()).thenReturn(producer);
		TextMessage result = null;
		try {
			result = validComponent.send(sentMessageStr);
		} catch (MQConnectionException | MQCommunicationException e) {
			
		}
		verify(context).createTextMessage(sentMessageStr);
		verify(context).createProducer();
		verifyNoMoreInteractions(context);
		assertNotNull(result);
		assertEquals(mockTextMessage, result);
	}
	
	@Test(expected = MQCommunicationException.class)
	public void testSendAndThrowMQCommunicationException() throws MQConnectionException, MQCommunicationException {

		when(context.createTextMessage(sentMessageStr)).thenReturn(mockTextMessage);
		when(context.createProducer()).thenReturn(null);
		validComponent.send(sentMessageStr);
		verify(context).createTextMessage(sentMessageStr);
		verify(context).createProducer();
		verifyNoMoreInteractions(context);

	}
	
	@Test
	public void testConsume() {

		when(context.createConsumer(destination)).thenReturn(consumer);
		when(consumer.receive()).thenReturn(mockMessage);
		Message result = null;
		try {
			result = validComponent.consume();
		} catch (MQConnectionException | MQCommunicationException e) {
			
		}
		verify(context).createConsumer(destination);
		verifyNoMoreInteractions(context);
		assertNotNull(result);
		assertEquals(mockMessage, result);

	}
	
	@Test
	public void testConsumeText() {

		when(context.createConsumer(destination)).thenReturn(consumer);
		when(consumer.receiveBody(String.class, 15000)).thenReturn(consumedMessageStr);
		String result = null;
		try {
			result = validComponent.consumeText();
		} catch (MQConnectionException | MQCommunicationException e) {

		}
		verify(context).createConsumer(destination);
		verifyNoMoreInteractions(context);
		assertNotNull(result);
		assertEquals(consumedMessageStr, result);

	}

}
