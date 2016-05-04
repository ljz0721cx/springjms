package com.janle.jms.product.service;

import java.io.Serializable;

import javax.jms.Destination;

public interface ProducerService {

	public void sendMessage(Destination destination, final Serializable message);
	
	
	public void sendMessage(Destination destination, final String message);
}
