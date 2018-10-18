package org.mycompany;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;



@MockEndpoints
@UseAdviceWith
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext
public class ProducerRouteTest extends CamelTestSupport {
	
	@Autowired
    CamelContext context;
	
	@Autowired
	private ProducerTemplate template;
	

	 @Test
	  public void testSendFilesWithJMSGroupIDAndSequence() throws Exception {
		 context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			 @Override
			 public void configure() throws Exception {
				 replaceFromWith("direct:start");
				 weaveByToUri("jms:*").replace().inOnly("mock:jms");
			 }
		});
		 
		 context.start();
		 
		 MockEndpoint mockOut = context.getEndpoint("mock:jms", MockEndpoint.class);
		 mockOut.expectedMessageCount(2);
		 mockOut.expectedHeaderReceived("JMSXGroupID", "Group 1");
		 template.sendBody("direct:start",fetchSampleXML());
		 
		 mockOut.assertIsSatisfied();
		 
		 context.stop();
		 
	  }
	 
	 private static String fetchSampleXML() {
		 StringBuilder stringBuilder = new StringBuilder();
		 try(BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/data/order1.xml"))) {
			 String line;
			 while ((line = br.readLine()) != null) {
				 stringBuilder.append(line);
			 }
		 }
			 catch(IOException e) {
				 return "";
			 }
		 return stringBuilder.toString();
		 }

	 }
	
