package ca.bc.gov.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomingMessageHeader {

	private String CPICVer;
	private String AgencyId;
	private String UserId;
	private String Role;
	private String DeviceId;
	
	private MQMessageHeaderOrigin Origin;
	private MQMessageHeaderRouting Routing;
	
	private String UDF;
	private int Priority;
	
	@Getter
	@Setter
	public class MQMessageHeaderOrigin {
		private String QMgrName;
		private String QName;
	}
	
	@Getter
	@Setter
	public class MQMessageHeaderRouting {
		private String QMgrName;
		private String QName;
	}
}
