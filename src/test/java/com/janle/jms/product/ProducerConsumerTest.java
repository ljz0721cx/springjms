package com.janle.jms.product;

import javax.jms.Destination;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.janle.jms.product.model.Email;
import com.janle.jms.product.service.ProducerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-jms.xml" })
public class ProducerConsumerTest {
	@Autowired
	private ProducerService producerService;
	@Autowired
	@Qualifier("queueDestination")
	private Destination destination;
	
	@Autowired
	@Qualifier("sessionAwareQueue")
	private Destination sessionAwareQueue;
	
	
	@Autowired
	@Qualifier("adapterQueue")
	private Destination adapterQueue;
	//@Ignore
	@Test
	public void testSend() {
		for (int i = 0; i < 2; i++) {
			producerService.sendMessage(destination, "你好，生产者！这是消息：" + (i + 1));
		}
	}

	@Ignore
	@Test
	public void testSessionAwareMessageListener() {
		producerService.sendMessage(sessionAwareQueue, "测试SessionAwareMessageListener");
	}
	
	@Ignore
	@Test
	public void testMessageListenerAdapter() {
		producerService.sendMessage(adapterQueue, "测试MessageListenerAdapter");
	}
	
	/**
	 * 自定义 添加了MessageConverter的测试
	 */
	@Test
	public void testObjectMessage() {
		Email email = new Email("zhangsan@xxx.com", "主题", "内容");  
		producerService.sendMessage(adapterQueue,email);
	}
}
