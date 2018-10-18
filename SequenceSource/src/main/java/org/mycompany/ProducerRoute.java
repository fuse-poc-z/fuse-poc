package org.mycompany;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		//from("file:src/main/resources/data").routeDescription("Producer Route Sequencing POC")
		from("file:/home/ec2-user/SequenceMessage/inbox?delay=1000").routeDescription("ISO NE Producer Route Sequencing POC")
			.routeId("ISONE.POC.PRODUCER_ROUTE").setHeader("service", simple("producerRoute"))
			.log(LoggingLevel.INFO,"Step 1 - Received in route and routing to JAXB bean")
			.log(LoggingLevel.INFO,"*** UNMARSHAL ${body} ***").id("LOG_PAYLOAD")
			.process(new ParseXMLtoObject()).id("transform")
			.log(LoggingLevel.INFO,"*** BODY AFTER UNMARSHAL ${body} ***").id("LOG_PAYLOAD")
			.log(LoggingLevel.INFO, "Step 2 - Received cim messages from JAXB bean")
			.setHeader("JMSXGroupID", constant("Group 1"))
			.process(new RandomSequenceProcessor("sequence_num"))
			.log(LoggingLevel.INFO,"Step 3 - Sending cim messages to queue")
			.multicast().parallelProcessing()
			.to("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.to("jms:topic:RH.POC.SOURCE.SEQUENCE.REQ")
			.log(LoggingLevel.INFO,"**************Camel route Completed*************");

	}
	//here in this POC to randomly generate a numeric int (sequence number) out of order
	public class RandomSequenceProcessor implements Processor {
	    private String headerName;

	    public RandomSequenceProcessor(String headerName){
	        this.headerName = headerName;
	    }

	    @Override
	    public void process(Exchange exchange) throws Exception {
	    	int randomNum = 1 + (int)(Math.random() * 10);
	        exchange.getIn().setHeader(headerName, (randomNum));
	        exchange.getIn().setHeader("CamelFileName", "order" + (randomNum) +".xml");
	      
	    }
	}	
	
}




