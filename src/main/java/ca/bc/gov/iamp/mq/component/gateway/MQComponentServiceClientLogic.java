package ca.bc.gov.iamp.mq.component.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ca.bc.gov.iamp.mq.component.model.TestUser;

@Component
public class MQComponentServiceClientLogic implements MQComponentServiceClient {

	@Override
	public ResponseEntity<TestUser> testClient() {
		return null;
	}

}
