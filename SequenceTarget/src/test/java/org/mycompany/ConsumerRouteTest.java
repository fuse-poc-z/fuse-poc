package org.mycompany;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@EnableAutoConfiguration
@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class)
@MockEndpoints
public class ConsumerRouteTest extends CamelTestSupport{
	@Autowired
	private ProducerTemplate template;
	
	@Autowired
    CamelContext context;
	
	private static BrokerService brokerSvc = new BrokerService();
	
//    @BeforeClass
//    //Sets up a embedded broker.
//    public static void setUpBroker() throws Exception {
//        brokerSvc.setBrokerName("TestBroker");
//        brokerSvc.addConnector("tcp://localhost:61616");
//        brokerSvc.setPersistent(false);
//        brokerSvc.setUseJmx(false);
//        brokerSvc.start();
//    }
//	
	 @Test
	  public void test() throws InterruptedException {

	  }

}
