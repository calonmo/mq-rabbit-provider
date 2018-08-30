

package mq.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Topic Exchange类型交换机
 * @author calon
 *
 */
@Configuration
public class TopicRabbitConfig {
 
    public final static String first = "topic.first";
    public final static String second = "topic.second";
 
    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.first);
    }
 
    @Bean
    public Queue secondQueue() {
        return new Queue(TopicRabbitConfig.second);
    }
 
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }
 
    //绑定topic.first队列到routingKey为topic.first，只有topic.first的routingKey才到此队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(first);
    }
 
    //绑定topic.second队列到topic.#，凡是topic.开头的routingKey都进到此队列
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }
    
}
