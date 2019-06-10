package ca.bc.gov.mq.component;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.bc.gov.mq.component.exception.MQCommunicationException;
import ca.bc.gov.mq.component.exception.MQConnectionException;
import ca.bc.gov.mq.component.logic.MQComponentLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class MQComponentTest {
	
	@Autowired
	private MQComponentLogic mqComponent;
	
	private String sampleMessage = "Sample message";

	@Test
	public void testSend() throws MQConnectionException, MQCommunicationException {

		//when(mqComponent.send(sampleMessage)).thenReturn(mqComponent.getContext().createTextMessage(sampleMessage));
		verify(mqComponent).send(sampleMessage);

	}
	
	@Test
	public void testConsume() throws MQConnectionException, MQCommunicationException {

		when(mqComponent.send(sampleMessage)).thenReturn(mqComponent.getContext().createTextMessage(sampleMessage));
		mqComponent.send(sampleMessage);
		verify(mqComponent).consume();

	}

}
