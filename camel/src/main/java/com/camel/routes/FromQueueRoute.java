package com.camel.routes;

import com.camel.process.ToQueueErrorProcess;
import com.camel.process.ToQueueProcess;
import com.camel.service.ActorService;
import com.camel.service.ApiService;
import com.camel.service.CityService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FromQueueRoute extends RouteBuilder {
	final ServiceBean serviceBean;
	final ApiService apiService;
	final ToQueueErrorProcess toQueueErrorProcess;
	final ToQueueProcess toQueueProcess;
	final CityService cityService;
	final ActorService actorService;

	@Value("${queues.queueDev1}")
	private String queueDev1;

	@Value("${queues.queueDev2}")
	private String queueDev2;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.maximumRedeliveries(2)
				.process(toQueueErrorProcess)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		String postfix = " 7";
//		from("ibmmq:queue:DEV.QUEUE.1?selector=ADAPTER='CAMEL'").log("CAMEL: ${body}");
		from("ibmmq:queue:" + queueDev1)
				.transacted()
				.filter(header("ADAPTER").isEqualTo("CAMEL"))
				.process(exchange -> {
					cityService.saveCity("Ziguinchor" + postfix);
				})
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
//					int a = 1/0;
					exchange.getIn().setBody("success");
				})
				.process(toQueueProcess)
				.log("CAMEL: ${body}");

	}

}
