package com.janle.jms.converter;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class EmailMessageConverter implements MessageConverter {

	/**
	 * 把一个Java对象转换成对应的JMS Message
	 */
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		return session.createObjectMessage((Serializable) object);
	}

	/**
	 * 把一个JMS Message转换成对应的Java对象
	 */
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		ObjectMessage objectMessage = (ObjectMessage) message;
		return objectMessage.getObject();
	}

}
