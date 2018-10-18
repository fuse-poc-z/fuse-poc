package org.mycompany;

import java.util.HashMap;
import java.util.Map;

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

public class ConsumerRouteTest extends CamelTestSupport{

	@Autowired
	CamelContext context;
	
	@Autowired
	private ProducerTemplate template;
	
	 @Test
	  public void testReceivedFilesWithJMSGroupIDAndSequence() throws Exception {
		 context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			 @Override
			 public void configure() throws Exception {
				 replaceFromWith("direct:start");
				 weaveByToUri("file:*").replace().inOnly("mock:outbox");
			 }
		});
		 
		 context.start();
		 
		 MockEndpoint mockOut = context.getEndpoint("mock:outbox", MockEndpoint.class);
		 mockOut.expectedMessageCount(1);
		 mockOut.expectedHeaderReceived("JMSXGroupID", "Group 1");
		 mockOut.expectedHeaderReceived("sequence_num", 4);
		 
		 Map<String, Object> headers = new HashMap<String,Object>();
		 headers.put("JMSXGroupID", "Group 1");
		 headers.put("sequence_num", "4");
		 template.sendBodyAndHeaders("direct:start","This is an XML",headers);
		 
		 mockOut.assertIsSatisfied();
		 
		 context.stop();
	  }

}
