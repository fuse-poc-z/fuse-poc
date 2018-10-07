package org.mycompany;

import java.util.concurrent.TimeUnit;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestSequencing {

	public static void main(String[] args) {
        try {
            
            CamelContext ctx = createCamelContext();
            ctx.start();
            ctx.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    /* Our direct route will take a message, and set the message to group 1 if the body is an integer,
                     * otherwise set the group to 2.
                     *
                     * This demonstrates the following concepts:
                     *  1) Header Manipulation
                     *  2) Checking the payload type of the body and using it in a choice.
                     *  3) JMS Message groups
                     */

                   /*from("direct:begin")
                    .choice()
                        .when(body().isInstanceOf(Integer.class)).setHeader("JMSXGroupID",constant("1"))
                        .otherwise().setHeader("JMSXGroupID",constant("2"))
                    .end()
                    .resequence(header("1")).stream()
                    .to("amq:queue:Message.Group.Test");*/
                   
                   
                   /*from("direct:start")
                   .choice()
                   	.when(body().isInstanceOf(Integer.class)).setHeader("JMSXGroupID",constant("1"))
                   .otherwise().setHeader("JMSXGroupID",constant("2"))
                   .end()
                   .setHeader("AMQPriority", constant("6"))
                   .to("amq:queue:Message.Group1.Test");*/
                   
                  from("direct:push")
                  .choice()
                  	.when(body().isInstanceOf(Integer.class)).setHeader("JMSXGroupID",constant("1"))
                  	.otherwise().setHeader("JMSXGroupID",constant("2"))
                  	.end()
                  	.setHeader("JMSXGroupSeq", constant("2"))
                   //Set the message as per priority
                   
                   //.to("amq:queue:DummyQ6?priority=2");
                   .to("amq:queue:DummyQ6");
                   
                    /* These two are competing consumers */
                 from("amq:queue:DummyQ6").routeId("Route A").log("Received: ${body}");
                  //from("amq:queue:Message.Group.Test").routeId("Route B").log("Received: ${body}");
                }
            });

           // sendMessages(ctx.createProducerTemplate());
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
           // stopBroker();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private static CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

       // ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://localhost/");
        
        /*JmsConnectionFactory jmsConnectionFactory = 
        		new JmsConnectionFactory("tcp://localhost:61616");*/
        
        /*camelContext.addComponent("amqp",
                JmsComponent.jmsComponentAutoAcknowledge(jmsConnectionFactory));*/
        
        
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory("tcp://localhost:61616");
        pooledConnectionFactory.setMaxConnections(8);
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(500);

        ActiveMQComponent activeMQComponent = ActiveMQComponent.activeMQComponent();
        activeMQComponent.setUsePooledConnection(true);
        activeMQComponent.setConnectionFactory(pooledConnectionFactory);
        camelContext.addComponent("amq", activeMQComponent);

        return camelContext;
    }
    
    private static void sendMessages(ProducerTemplate pt) throws Exception {
        

       /* for (int i = 4; i <= 4; i++) {
            pt.sendBody("direct:push", "Testing");
        }*/
        
        for (int i = 1; i <= 1; i++) {
            pt.sendBody("direct:push", Integer.valueOf(i));
           // pt.sendBodyAndProperty("direct:push", Integer.valueOf(i), "JMSXGroupSeq", 1);        
            }
        /*for (int i = 0; i < 10; i++) {
            pt.sendBody("direct:begin", "next group");
        }*/

        /*pt.sendBody("direct:begin", Integer.valueOf(1));
        pt.sendBody("direct:begin", "foo");
        pt.sendBody("direct:begin", Integer.valueOf(2));*/
    }
}
