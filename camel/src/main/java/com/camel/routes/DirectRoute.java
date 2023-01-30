package com.camel.routes;

import com.camel.dto.User;
import com.camel.entity.ActorEntity;
import com.camel.process.MyProcessor;
import com.camel.repository.RentalNewRepository;
import com.camel.service.ApiService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DirectRoute extends RouteBuilder {
	final MyProcessor myProcessor;
	final ServiceBean serviceBean;
	final ApiService apiService;

	@Override
	public void configure() throws Exception {
//		df.setModuleClassNames("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule");
		JacksonDataFormat jsonDf = new JacksonDataFormat(ActorEntity.class);
		jsonDf.setPrettyPrint(true);

		from("direct:start").log("START CAMEL!!!");
		from("direct:hello")
//				.process(myProcessor)
//				.process(this::processResponse)
				.transform(simple("Random number ${random(0,100)}"))
//				.transform().constant("Hello World direct")
				.to("ibmmq:queue:DEV.QUEUE.1");

		from("direct:getActor")
				.bean(apiService, "getActor");
//				.unmarshal().json(Object.class);
//				.marshal(jsonDf);

		from("direct:handleTransactional")
				.transacted()
				.bean(apiService, "handleTransactional")
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
//		int a = 1/0;
		exchange.getIn().setBody("abcd");
	}

}
