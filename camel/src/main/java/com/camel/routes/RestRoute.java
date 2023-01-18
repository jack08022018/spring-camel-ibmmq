package com.camel.routes;

import com.camel.dto.User;
import com.camel.process.MyProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.http.NettyHttpMessage;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestRoute extends RouteBuilder {
	final MyProcessor myProcessor;
	final ServiceBean serviceBean;

	@Override
	public void configure() throws Exception {
//		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);
		restConfiguration().component("netty-http")
//				.contextPath("/camel-rest")
				.port(9290)
				.bindingMode(RestBindingMode.json);
		rest("/api")
				.get("/hello").type(User.class)
				.to("direct:hello")

				.post("/getUser").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=getUser")

				.post("/toUpper").type(User.class)//.outType(String.class)
				.to("bean:serviceBean?method=toUpper");

//				.get("/search?country={country}")
//				.to("bean:searchBean?method=byCountry(${header.country})");

//		from("direct:hello").transform().constant("Hello World direct");
//		from("direct:hello").process(this::processResponse);

		from("direct:hello")
				.process(myProcessor);
//				.to("mq:queue:DEV.QUEUE.1");

		from("ibmmq:queue:DEV.QUEUE.1?selector=adapter='T24'").log("T24: ${body}");
		from("ibmmq:queue:DEV.QUEUE.1?selector=adapter='EPAY'").log("EPAY: ${body}");

//		from("timer:timer1?period={{timer.period}}")
//				.process(myProcessor)
////			.to("direct:hello")
//			.log("${body}");

	}

	void processResponse(final Exchange exchange) {
		System.out.println("xxx: " + exchange.getIn().getBody().toString());
		exchange.getIn().setBody("abcd");
	}

}
