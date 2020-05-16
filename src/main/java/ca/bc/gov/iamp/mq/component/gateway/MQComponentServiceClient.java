package ca.bc.gov.iamp.mq.component.gateway;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import ca.bc.gov.iamp.mq.component.model.MQServiceResponse;

@ComponentScan(basePackages= {"ca.bc.gov.iamp.mq.component.logic"})
@EntityScan(basePackages= {"ca.bc.gov.mq.iamp.component.model"})
@Component
@FeignClient(name = "${mq.service.name}", url = "${mq.service.url}", decode404 = true)
public interface MQComponentServiceClient {

	@GetMapping(value = "${mq.service.send.path}", consumes = "application/json")
	public ResponseEntity<MQServiceResponse> sendToService();
	
	@GetMapping(value = "${mq.service.consume.path}", consumes = "application/json")
	public ResponseEntity<MQServiceResponse> consumeFromService();
	
}
