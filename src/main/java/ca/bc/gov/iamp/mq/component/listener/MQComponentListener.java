package ca.bc.gov.iamp.mq.component.listener;

import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.exception.MQCommunicationException;
import ca.bc.gov.iamp.mq.component.exception.MQConnectionException;

@ComponentScan(basePackages= {"ca.bc.gov.mq.component.logic"})
@EntityScan(basePackages= {"ca.bc.gov.mq.component.model"})
@Component
public interface MQComponentListener {

	public void connect(String mq) throws JMSException, MQConnectionException;
	public void listen(MessageListener listener) throws MQConnectionException, MQCommunicationException;
	
}
