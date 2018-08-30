package mq.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

	@Bean
    public Queue CalonDirectQueue() {
        return new Queue("CalonDirectQueue",true);
    }
	@Bean
	public Queue CalonDirectQueueAck() {
		return new Queue("CalonDirectQueueAck",true);
	}
	
	@Bean
    DirectExchange CalonDirectExchange() {
        return new DirectExchange("CalonDirectExchange");
    }
	
	@Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(CalonDirectQueue()).to(CalonDirectExchange()).with("CalonDirectRouting");
    }
	@Bean
    Binding bindingDirectAck() {
        return BindingBuilder.bind(CalonDirectQueueAck()).to(CalonDirectExchange()).with("CalonDirectQueueAckRouting");
    }

}
