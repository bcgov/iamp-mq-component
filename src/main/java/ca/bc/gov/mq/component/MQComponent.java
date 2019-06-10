package ca.bc.gov.mq.component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import ca.bc.gov.mq.component.exception.MQCommunicationException;
import ca.bc.gov.mq.component.exception.MQConnectionException;

@ComponentScan(basePackages= {"ca.bc.gov.mq.component.logic"})
@EntityScan(basePackages= {"ca.bc.gov.mq.component.model"})
@Component
public interface MQComponent {

	public void connect(String mq) throws JMSException, MQConnectionException;
	public TextMessage send(String message) throws MQConnectionException, MQCommunicationException;
	public Message consume() throws MQConnectionException, MQCommunicationException;
	public String consumeText() throws MQConnectionException, MQCommunicationException;
	
}
