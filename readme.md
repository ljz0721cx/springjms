JMS的全称是Java Message Service，JMS只是一个标准
下面我们使用spring-jms作为研究

ConnectionFactory：是用于产生到JMS服务器的链接的工厂，
pooledConnectionFactory：我们可以为工厂出来的连接建立一个pool
JmsTemplate：Spring为我们提供的JmsTemplate类来实现消息的接收和发送。也是最核心的
```
<!-- Spring提供的JMS工具类，它可以进行消息发送、接收等 -->  
<bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">  
    <!-- 这个connectionFactory对应的是我们定义的Spring提供的那个ConnectionFactory对象 -->  
    <property name="connectionFactory" ref="connectionFactory"/>  
</bean>  
```
Destination：消息发送的目的地，ActiveMQ中实现了两种类型的Destination，一个是点对点的ActiveMQQueue，另一个就是支持订阅/发布模式的ActiveMQTopic
```
<!--这个是队列目的地，点对点的-->  
<bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">  
    <constructor-arg>  
	<value>queueDestination</value>  
    </constructor-arg>  
</bean>  
<!--这个是主题目的地，一对多的-->  
<bean id="topicDestination" class="org.apache.activemq.command.ActiveMQTopic">  
    <constructor-arg value="topic"/>  
</bean> 
```
MessageListenerContainer：消息监听容器，必须要有以下几个属性
	一个是表示从哪里监听的ConnectionFactory；一个是表示监听什么的Destination；一个是接收到消息以后进行消息处理的MessageListener。
	Spring一共为我们提供了两种类型的：DefaultMessageListenerContainer和SimpleMessageListenerContainer
		SimpleMessageListenerContainer会在一开始的时候就创建一个会话session和消费者Consumer，一般不兼容Java EE的JMS限制
		DefaultMessageListenerContainer会动态的适应运行时需要，并且能够参与外部的事务管理。它很好的平衡了对JMS提供者要求低、先进功能如事务参与和兼容Java EE环境。
```	
<!-- 消息监听器 -->  
<bean id="consumerMessageListener" class="com.tiantian.springintejms.listener.ConsumerMessageListener"/>      

<!-- 消息监听容器 -->  
<bean id="consumerMessageListenerContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">  
    <property name="connectionFactory" ref="connectionFactory" />  
    <property name="destination" ref="queueDestination" />  
    <property name="messageListener" ref="consumerMessageListener" />  
    <!-- 这里可以设置事务啥的 -->  
</bean>  
```
如果我们发出了一个名字为queueDestination的消息，我们的监听器就是监听了发送到这个目的地的消息。这样监听的容器就可以监听到相关的信息。


首先要安装ActiveMq,启动mq,访问mq地址
http://localhost:8161/admin/queues.jsp


注意事项：

1.消息监听时候会出现消息出现异常不能回滚，是由于你处理了异常或者是你没有抛出RuntimeException，只有在抛出是运行时异常时候才会回滚消息  
2.回滚消息可以在Number Of Pending Messages可以看到挂起的消息队列。有兴趣的可以自己测试一下  
3.使用JTA做的数据库的事务管理，可以使用分布式的消息监听   

- http://www.jakubkorab.net/2011/08/configuring-activemq-transactions-in-spring.html
- http://www.open-open.com/lib/view/open1400126457817.html


