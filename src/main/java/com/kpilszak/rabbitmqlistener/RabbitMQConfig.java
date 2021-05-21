package com.kpilszak.rabbitmqlistener;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	private static final String QUEUE = "Queue";
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	@Bean
	Exchange exchange() {
		return ExchangeBuilder.topicExchange("TopicExchange")
		                      .durable(true)
		                      .build();
	}
	
	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue())
		                     .to(exchange())
		                     .with("topic")
		                     .noargs();
	}
	
	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername("guest");
		cachingConnectionFactory.setPassword("guest");
		return cachingConnectionFactory;
	}
	
	@Bean
	MessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setQueues(queue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());
		return simpleMessageListenerContainer;
	}
}
