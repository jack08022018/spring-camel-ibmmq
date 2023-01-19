package com.camel.routes;

import com.camel.dto.User;
import com.camel.process.MyProcessor;
import com.camel.service.ApiService;
import com.camel.service.ServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestRoute extends RouteBuilder {
	final MyProcessor myProcessor;
	final ServiceBean serviceBean;
	final ApiService apiService;

	@Override
	public void configure() throws Exception {
//		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);
		restConfiguration().component("netty-http")
				.apiProperty("api.title", "Camel REST API")
				.apiProperty("api.version", "1.0")
				.apiProperty("cors", "true")
				.contextPath("/camel-rest")
				.port(9290)
				.bindingMode(RestBindingMode.json);
		rest("/api")
				.get("/hello").type(User.class)
				.to("direct:hello")

				.post("/getUser").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=getUser")

				.post("/toUpper").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=toUpper")

				.post("/handleTransactional").type(User.class)//.outType(String.class)
				.to("direct:handleTransactional")

				.post("/importExcel")
//				.consumes("multipart/form-data")
//				.param().name("file")
//					.type(RestParamType.header)
////					.defaultValue("false")
////					.description("Verbose order details")
//				.endParam()
				.to("direct:importExcel");

//				.get("/search?country={country}")
//				.to("bean:searchBean?method=byCountry(${header.country})");

//		from("ibmmq:queue:DEV.QUEUE.1?selector=ADAPTER='CAMEL'").log("CAMEL: ${body}");
		from("ibmmq:queue:DEV.QUEUE.1")
				.filter(header("ADAPTER").isEqualTo("CAMEL"))
				.log("CAMEL: ${body}");

	}

}
