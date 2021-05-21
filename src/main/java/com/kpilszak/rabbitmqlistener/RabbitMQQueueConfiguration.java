package com.kpilszak.rabbitmqlistener;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfiguration {
	
	@Bean
	Queue exampleQueue() {
		return new Queue("ExampleQueue", false);
	}
	
	@Bean
	Queue exampleSecondQueue() {
		return QueueBuilder.durable("ExampleSecondQueue")
		                   .autoDelete()
		                   .exclusive()
		                   .build();
	}
}
