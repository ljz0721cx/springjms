package com.janle.jms.product.asyncmq;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageSenderRunable implements Runnable {
	@Autowired
	private JmsTemplate jmsTemplate;
	private final String destinationName;

	public MessageSenderRunable(String destinationName) {
		this.destinationName = destinationName;
	}

	@Override
	public void run() {
		int messageCount = 0;
		while (true) {
			jmsTemplate.send(destinationName, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage(new Date() + "现在发送是第" + messageCount + "条消息");
					return textMessage;
				}
			});
		}
	}

}
