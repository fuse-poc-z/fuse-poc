package org.mycompany;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("file://./src/main/resources/data/?fileName=order4.xml&noop=true").startupOrder(10)
			.routeId("producer1")
			.log("Received in route ${body} and Headers are ${headers}. Routing to jms:ISO.NE.TEST.IN.")
			.process(new ParseXMLtoObject())
			.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("4"))
			.to("jms:ISO.NE.TEST.IN");

		from("file://./src/main/resources/data/?fileName=order1.xml&noop=true").startupOrder(20)
			.routeId("producer2")
			.log("Received in route ${body} and Headers are ${headers}. Routing to jms:ISO.NE.TEST.IN.")
			.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("1"))
			.to("jms:ISO.NE.TEST.IN");
		
		from("file://./src/main/resources/data/?fileName=order3.xml&noop=true").startupOrder(30)
			.routeId("producer3")
			.log("Received in route ${body} and Headers are ${headers}. Routing to jms:ISO.NE.TEST.IN.")
			.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("3"))
			.to("jms:ISO.NE.TEST.IN");
		
		from("file://./src/main/resources/data/?fileName=order2.xml&noop=true").startupOrder(40)
			.routeId("producer4")
			.log("Received in route ${body} and Headers are ${headers}. Routing to jms:ISO.NE.TEST.IN.")
			.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("2"))
			.to("jms:ISO.NE.TEST.IN");
		
		
		
	}
	
}
