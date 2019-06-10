package ca.bc.gov.mq.component.exception;

public class MQConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MQConnectionException(){
		super("Unable to connect to MQ");
	}

}
