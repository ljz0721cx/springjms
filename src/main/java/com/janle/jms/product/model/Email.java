package com.janle.jms.product.model;

import java.io.Serializable;

public class Email implements Serializable {
	/**
	 * 发送的时候就把这个对象封装成一个ObjectMessage进行发送
	 */
	private static final long serialVersionUID = 1L;

	private String receiver;
	private String title;
	private String content;

	public Email(String receiver, String title, String content) {
		super();
		this.receiver = receiver;
		this.title = title;
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Override  
    public String toString() {  
        StringBuilder builder = new StringBuilder();  
        builder.append("Email [receiver=").append(receiver).append(", title=")  
                .append(title).append(", content=").append(content).append("]");  
        return builder.toString();  
    }  
}
