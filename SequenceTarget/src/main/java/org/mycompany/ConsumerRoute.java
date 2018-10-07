package org.mycompany;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:ISO.NE.TEST.IN")
		.routeId("consumer-resequence")
		.log("Received in route ${body} and Headers are ${headers}. Routing to jms:ISO.NE.TEST.OUT.")
		.resequence(header("sequence_num"))
		.batch().timeout(3000)
		.to("file://target/outbox/");
		
	}

}
