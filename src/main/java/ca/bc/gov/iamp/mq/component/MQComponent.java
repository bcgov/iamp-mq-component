package ca.bc.gov.iamp.mq.component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;

@ComponentScan(basePackages= {"ca.bc.gov.iamp.mq.component.logic"})
@EntityScan(basePackages= {"ca.bc.gov.mq.iamp.component.model"})
@Component
public interface MQComponent {

	public void connect(String mq) throws JMSException, MQConnectionException;
	public TextMessage send(String message) throws MQCommunicationException;
	public Message consume() throws MQCommunicationException;
	public String consumeText() throws MQCommunicationException;
	
}
