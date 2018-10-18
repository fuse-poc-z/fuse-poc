package org.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.routeId("ISONE.POC.CONSUMER_ROUTE1")
			.log("Step 1 - Comsumer 1 Received messages and Routing to JAXB bean")
			.log(LoggingLevel.INFO,"*** UNMARSHAL ${body} ***").id("LOG_PAYLOAD")
		//.process(new ParseXMLtoObject())
		.log(LoggingLevel.INFO,"Step 2 - Received messages from JAXB bean using resequence")
		.log(LoggingLevel.INFO,"*** BODY AFTER UNMARSHAL ${body} ***").id("LOG_PAYLOAD")
		.resequence(header("sequence_num"))
			.batch()
				.size(4)
				.timeout(6000)
		.log(LoggingLevel.INFO,"Step 3 - sending messages to folder /outbox")
		.to("file:/home/ec2-user/SequenceMessage/outbox1")
		.log(LoggingLevel.INFO,"**************Camel route Completed*************");
		
		from("jms:RH.POC.SOURCE.SEQUENCE.REQ")
		.routeId("ISONE.POC.CONSUMER_ROUTE2")
		.log(LoggingLevel.INFO,"Step 1 - Comsumer 2 Received messages and Routing to JAXB bean")
		//.process(new ParseXMLtoObject())
		.log(LoggingLevel.INFO,"Received in messages in thread 2 and Routing to folder")
		.resequence(header("sequence_num"))
			.batch()
				.size(4)
				.timeout(6000)
		.to("file:/home/ec2-user/SequenceMessage/outbox2")
		.log(LoggingLevel.INFO,"**************Camel route Completed*************");
		
	}
}
