package com.janle.jms.product.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.jms.support.converter.MessageConverter;

import com.janle.jms.product.model.Email;

public class EmailMessageListener implements MessageListener {
	private MessageConverter messageConverter;

	public MessageConverter getMessageConverter() {
		return messageConverter;
	}

	public void setMessageConverter(MessageConverter messageConverter) {
		this.messageConverter = messageConverter;
	}

	@Override
	public void onMessage(Message message) {

		if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage = (ObjectMessage) message;
			try {
				 /*Object obj = objMessage.getObject(); 
                Email email = (Email) obj;*/  
				Email email=(Email)messageConverter.fromMessage(objectMessage);
				System.out.println("接收到一个ObjectMessage，包含Email对象。");
				System.out.println(email);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
