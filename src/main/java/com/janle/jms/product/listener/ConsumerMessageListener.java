package com.janle.jms.product.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.janle.jms.product.dao.ProductDao;

/**
 * 定义处理消息的MessageListener
 *
 * 这里使用JTA时候只有在抛出RuntimeException时候才会回滚不知道为什么
 * @author ljz07
 *
 */
public class ConsumerMessageListener implements MessageListener {
	@Autowired
	private ProductDao productDao;
	private int count = 0;

	@Override
	public void onMessage(Message message) {
		// 这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换
		TextMessage textMsg = (TextMessage) message;
		System.out.println("ConsumerMessageListener-------接收到一个纯文本消息。");
		String text;
		try {
			text = textMsg.getText();
			System.out.println("消息内容是：" + text + ",当前count的值是：" + count);
			productDao.insert(text + count);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 验证事物回滚到挂起的列表中
		if (count==0) {
			count++;
			//不知道为什么只有在抛出RuntimeException时候才会回滚，所以这里注意消息丢失的情况
			throw new RuntimeException("Error! 出错啦！");
		}
		// throw new RuntimeException("Error");
	}

}
