package ca.bc.gov.iamp.mq.component.exception;

public class MQCommunicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MQCommunicationException(){
		super("Unable to send or consume message from MQ or MQ Service");
	}
	
}
