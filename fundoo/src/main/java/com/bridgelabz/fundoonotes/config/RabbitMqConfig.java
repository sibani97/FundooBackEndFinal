package com.bridgelabz.fundoonotes.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;

import com.bridgelabz.fundoonotes.user.model.Email;


public class RabbitMqConfig {

	@Value("{$queueName}")
	private String queueName;
	
	@Value("{$exchange}")
	private String exchange;
	
	@Value("{$routingKey}")
	private String routingKey;
	
	private String elasticQueue="elasticQueue";
	
	private String elasticroutingKey="routingKey";
	
	@Bean
	Queue queue()
	{
		return new Queue(queueName);
		
	}
	
	@Bean
	Queue elasticQueue()
	{
		return new  Queue(elasticQueue); 
		
	}
	
	@Bean
	DirectExchange exchange()
	{
		return new DirectExchange(exchange);
	}
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	Binding elasticBinding(Queue elasticQueue, DirectExchange exchange) {
		return BindingBuilder.bind(elasticQueue).to(exchange).with(elasticroutingKey);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
	
	
}
