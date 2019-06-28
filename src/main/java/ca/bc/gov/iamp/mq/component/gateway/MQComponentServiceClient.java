package ca.bc.gov.iamp.mq.component.gateway;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import ca.bc.gov.iamp.mq.component.model.TestUser;

@ComponentScan(basePackages= {"ca.bc.gov.iamp.mq.component.logic"})
@EntityScan(basePackages= {"ca.bc.gov.mq.iamp.component.model"})
@Component
@FeignClient(name = "MQComponentService", url = "https://jsonplaceholder.typicode.com", decode404 = true)
public interface MQComponentServiceClient {

//	public void setup(String mqServiceId) throws MQConnectionException;
	
	@GetMapping(value = "/comments/1", consumes = "application/json")
	public ResponseEntity<TestUser> testClient();
	
//	@GetMapping(value = "/sendToService", consumes = "application/json")
//	public ResponseEntity<MQServiceResponse> sendToService();
//	
//	@GetMapping(value = "/consumeFromService", consumes = "application/json")
//	public ResponseEntity<MQServiceResponse> consumeFromService();
	
//	@GetMapping(value = "/consumeTextFromService", consumes = "application/json")
//	public ResponseEntity<MQServiceResponse> consumeTextFromService();
	
}
