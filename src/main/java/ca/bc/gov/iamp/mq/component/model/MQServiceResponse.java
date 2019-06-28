package ca.bc.gov.iamp.mq.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MQServiceResponse {

	private MQServiceResponseHeader header;
	private MQServiceResponseBody body;
	
	@Getter
	@Setter
	public class MQServiceResponseHeader {

		private String cpicVer;
		private String udf;
		private int priority;
	}
	
	@Getter
	@Setter
	public class MQServiceResponseBody {

		private String msgFFmt;

	}
}
