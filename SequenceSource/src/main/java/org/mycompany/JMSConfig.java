package org.mycompany;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;

@Configuration 
@ConfigurationProperties("activemq.broker")
public class JMSConfig {
	private String url;
	private String username; 
	private String password;
	private String maxconnections;
	
	
	@Bean
    public JmsConfiguration jmsConfig(){
    	JmsConfiguration jmsConfig = new JmsConfiguration();

        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(url);
    	cf.setUserName(username);
    	cf.setPassword(password);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(cf);
        pooledConnectionFactory.setMaxConnections(Integer.parseInt(maxconnections));
    	jmsConfig.setConnectionFactory(pooledConnectionFactory);
    	return jmsConfig;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxconnections() {
		return maxconnections;
	}

	public void setMaxconnections(String maxconnections) {
		this.maxconnections = maxconnections;
	}

}


