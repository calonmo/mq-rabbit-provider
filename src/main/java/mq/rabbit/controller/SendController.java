package mq.rabbit.controller;

import java.util.UUID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import mq.rabbit.entity.User;

@Controller
public class SendController {

	@Autowired
	private RabbitTemplate template;
	
	@Autowired
	private ObjectMapper objectMapper;
	@GetMapping("/sendDirect/{exchange}/{routingKey}")
	private @ResponseBody String sendDirect(String message,@PathVariable(value = "exchange") String exchange,@PathVariable(value = "routingKey") String routingKey) throws Exception {
		User user = new User(UUID.randomUUID().toString(), message, "123456", "sendDirect");
		template.convertAndSend(exchange, routingKey, user);
		return "OK,sendDirect:" + message;
	}
	
	@GetMapping("/sendDirect")
	private @ResponseBody String sendDirect(String message) throws Exception {
		User user = new User(UUID.randomUUID().toString(), message, "123456", "sendDirect");
		template.convertAndSend("CalonDirectExchange", "CalonDirectRouting", user);
		return "OK,sendDirect:" + message;
	}
	
	@GetMapping("/sendDirectAck")
	private @ResponseBody String sendDirect2(String message) throws Exception {
		User user = new User(UUID.randomUUID().toString(), message, "456789", "sendDirect2");
		template.convertAndSend("CalonDirectExchange", "CalonDirectQueueAckRouting", objectMapper.writeValueAsString(user));
		return "OK,sendDirect2:" + message;
	}
	
	@GetMapping("/sendTopicFirst")
	private @ResponseBody String sendTopicFirst(String message) {
		User user = new User(UUID.randomUUID().toString(), message, "123456", "sendTopicFirst");
		template.convertAndSend("topicExchange", "topic.first", user);
		return "OK,sendTopicFirst:" + message;
	}
	
	@GetMapping("/sendTopicSecond")
	private @ResponseBody String sendTopicSecond(String message) {
		User user = new User(UUID.randomUUID().toString(), message, "123456", "sendTopicSecond");
		template.convertAndSend("topicExchange", "topic.second", user);
		return "OK,sendTopicSecond:" + message;
	}
	
	@GetMapping("/sendFanout")
	private @ResponseBody String sendFanout(String message) {
		User user = new User(UUID.randomUUID().toString(), message, "123456", "sendFanout");
		template.convertAndSend("fanoutExchange", null, user);
		return "OK,sendFanout:" + message;
	}
	
}