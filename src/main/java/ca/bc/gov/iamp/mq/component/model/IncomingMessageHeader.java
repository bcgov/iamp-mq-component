package ca.bc.gov.iamp.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingMessageHeader {

	private String cpicVer;
	private String agencyId;
	private String userId;
	private String role;
	private String deviceId;
	
	private MQMessageHeaderOrigin origin;
	private MQMessageHeaderRouting routing;
	
	private String udf;
	private int priority;
	
	@Getter
	@Setter
	public class MQMessageHeaderOrigin {
		private String qMgrName;
		private String qName;
	}
	
	@Getter
	@Setter
	public class MQMessageHeaderRouting {
		private String qMgrName;
		private String qName;
	}
}
