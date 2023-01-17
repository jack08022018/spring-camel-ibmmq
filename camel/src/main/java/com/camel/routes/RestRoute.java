package com.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);

		rest().path("/rest")
				.consumes("application/json")
				.produces("application/json")
				.get()
					.to("direct:hello")

				.post().type(User.class).outType(User.class)
					.to("bean:serviceBean");

		from("direct:hello").transform().constant("Hello World direct");
//		from("timer:timer1?period={{timer.period}}")
//			.to("direct:hello")
//			.log("${body}");

	}

}
