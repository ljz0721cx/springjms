package com.janle.jms.product.service.impl;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.janle.jms.product.service.ProducerService;

@Service(value = "producerService")
public class ProducerServiceImpl implements ProducerService {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	@Qualifier("responseQueue")
	private Destination responseQueue;

	@Autowired
	@Qualifier("defaultResponseQueue")
	private Destination defaultResponseQueue;

	/**
	 * 未使用MessageConverter的情况
	 * 
	 */
	@Override
	public void sendMessage(Destination destination, String message) {
		System.out.println("---------------ProducerServiceImpl 生产者发了一个消息：" + message);
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message.toString());
				// textMessage.setJMSReplyTo(responseQueue);//设置响应的消息
				//textMessage.setJMSReplyTo(defaultResponseQueue);// 默认的消息响应
				return session.createTextMessage(message);//返回textMessage
				//return textMessage;
			}
		});
	}

	/**
	 * 使用自定义MessageConverter的情况
	 */
	@Override
	public void sendMessage(Destination destination, Serializable message) {
		System.out.println("---------------ProducerServiceImpl 生产者发了一个MessageConverter消息：" + message);
		jmsTemplate.convertAndSend(destination, message);
	}

}
