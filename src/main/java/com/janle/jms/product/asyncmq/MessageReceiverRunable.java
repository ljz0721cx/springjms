package com.janle.jms.product.asyncmq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class MessageReceiverRunable implements Runnable {

	@Autowired
	private JmsTemplate jmsTemplate;

	private String destinationName;

	@Override
	public void run() {
		while (true) {
			// 收到的jms消息
			Message message = jmsTemplate.receive(destinationName);
			// TODO 这里应该有个适配器
			try {
				if (message instanceof TextMessage) {
					TextMessage receiveMessage = (TextMessage) message;
					System.out.println("我是Receiver,收到消息如下: \r\n" + receiveMessage.getText());
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
