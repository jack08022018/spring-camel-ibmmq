package com.camel.routes;

import com.camel.config.ExceptionHandler;
import com.camel.process.CityProcessor;
import com.camel.process.MyProcessor;
import com.camel.service.ActorService;
import com.camel.service.ApiService;
import com.camel.service.CityService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectRoute extends RouteBuilder {
	final MyProcessor myProcessor;
	final ServiceBean serviceBean;
	final ApiService apiService;
	final ExceptionHandler exceptionHandler;
	final CityProcessor cityProcessor;
	final CityService cityService;
	final ActorService actorService;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
//		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
//		jsonDf.setPrettyPrint(true);

		onException(Exception.class)
//				.handled(true)
//				.maximumRedeliveries(2)
				.process(exceptionHandler)
				.handled(true)
				.markRollbackOnlyLast()
				.end();

		from("direct:start").log("START CAMEL!!!");
		from("direct:hello")
//				.process(myProcessor)
				.process(exchange -> {
//					int a = 1/0;
					exchange.getIn().setBody("Hello!");
				});
//				.transform(simple("Random number ${random(0,100)}"))
//				.transform().constant("Hello World direct")
//				.to("ibmmq:queue:DEV.QUEUE.1");

		from("direct:getActor")
				.bean(apiService, "getActor");
//				.unmarshal().json(Object.class);
//				.marshal(jsonDf);

		String postfix = " 8";
		from("direct:handleTransactional")
				.transacted()
//				.bean(apiService, "handleTransactional")
				.process(exchange -> {
					cityService.saveCity("Ziguinchor" + postfix);
				})
				.process(exchange -> {
					actorService.saveActor("THORA" + postfix);
				})
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
					int a = 1/0;
					exchange.getIn().setBody("success");
				});
//				.bean(apiService, "findAllRental");

		from("direct:importExcel")
//				.unmarshal().mimeMultipart()
//				.setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data"))
				.transacted()
				.bean(apiService, "importExcel")
				.process(exchange -> {
					System.out.println("aaa: " + exchange.getIn().getBody().toString());
//					int a = 1/0;
					exchange.getIn().setBody("success");
				});

//		from("timer:timer1?period={{timer.period}}")
//				.process(myProcessor)
////			.to("direct:hello")
//			.log("${body}");

//		from("ibmmq:queue:DEV.QUEUE.1?selector=ADAPTER='CAMEL'").log("CAMEL: ${body}");
		from("ibmmq:queue:DEV.QUEUE.1")
				.filter(header("ADAPTER").isEqualTo("CAMEL"))
//				.process(this::processResponse)
				.log("CAMEL: ${body}");

	}

	void processResponse(final Exchange exchange) {
		System.out.println("message: " + exchange.getIn().getBody().toString());
		int a = 1/0;
		exchange.getIn().setBody("abcd");
	}

}
