# iamp-mq-component

The **mq-component** is a reusable library (.jar) responsible for bridging applications and queues (IBM MQ).

All the communications between applications and the MQ/s should occur through this component; it provides a simple interface of communication to read, write and listen from a queue.

# Configuration

Create an XML file named **mq-component.xml** in your classpath (i.e. /src/main/resources), and copy the following example:

    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xsi:schemaLocation="http://www.springframework.org/schema/beans
               http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
    
    	<bean id="MQ1"
    	class="ca.bc.gov.iamp.mq.component.properties.MQConfigurationProperties">
    		<property name="host" value="localhost" />
    		<property name="port" value="1414" />
    		<property name="channel" value="DEV.APP.SVRCONN" />
    		<property name="queueManager" value="QM2" />
    		<property name="appUser" value="APP" />
    		<property name="appPassword" value="app" />
    		<property name="queueName" value="DEV.QUEUE.1" />
    	</bean>
    
    	<bean id="MQ2"
    	class="ca.bc.gov.iamp.mq.component.properties.MQConfigurationProperties">
    		<property name="host" value="localhost" />
    		<property name="port" value="1414" />
    		<property name="channel" value="DEV.APP.SVRCONN" />
    		<property name="queueManager" value="QM3" />
    		<property name="appUser" value="APP" />
    		<property name="appPassword" value="app" />
    		<property name="queueName" value="DEV.QUEUE.1" />
    	</bean>
    
    </beans>

Each new MQ added to the configuration is a new `<bean id=”MQx” />` added to the XML file; each MQ must contain all properties:

| property name | description |
|--|--|
| host | MQ server hostname or IP |
| port | MQ server port number |
| channel | MQ message channel for desired queue |
| queueManager | name of queue manager in which the queue is allocated |
| appUser | username of user with access to the MQ installation |
| appPassword | password of user with access to the MQ installation |
| queueName | name of queue |

# Usage examples

## Reading from a queue

### Steps:

 1. Declare and autowire an MQComponent object;
 2. Use the *connect(String mq)* method, passing the bean id from *mq-component.xml* as input string;
 3. Use the *consumeText()* method to read the next queued message in the queue.

### Code:

    @Autowired
    private MQComponent mq1;
    
    @PostConstruct
    public void init() throws JMSException, MQConnectionException {
    	mq1.connect("MQ1");
    }
    
    public String consume() {
    String receivedMessage = null;
    	try {
    		receivedMessage = mq2.consumeText();
    	} catch (MQConnectionException | MQCommunicationException e) {
    		/* log error here */
    	}
    	return receivedMessage;
    }

## Writing to a queue

### Steps:

 1. Declare and autowire an MQComponent object.
 2. Use the *connect(String mq)* method, passing the bean id from *mq-component.xml* as input string.
 3. Use the *send(String message)* method to send a string message to the queue.

### Code:

    @Autowired
    private MQComponent mq2;
    
    @PostConstruct
    public void init() throws JMSException, MQConnectionException {
    	mq2.connect("MQ2");
    }
    
    public String send() {
    String message = "Your message";
    	TextMessage sampleMessage = null;
    	try {
    		sampleMessage = mq2.send(message);
    	} catch (MQConnectionException | MQCommunicationException e) {
    		/* log error here */
    	}
    	return sampleMessage.toString();
    }


## Listening to a queue

### Steps:

1. Create a new class implementing the class MessageListener from JMS library;
2. Declare and autowire an MQComponentListener object;
3. Use the *connect(String mq)* method, passing the bean id from *mq-component.xml* as input string;
4. Use the *listen()* method passing *this* as input.
5. Process the messages in the *onMessage(Message message)* method.

### Code:

    public class CustomListener implements MessageListener {
    	
    	@Autowired
    	MQComponentListener mqComponentListener;
    	
    	@PostConstruct
    	public void init() {
    		try {
    			mqComponentListener.connect("MQ1");
    			mqComponentListener.listen(this);
    		} catch (MQConnectionException | MQCommunicationException e | JMSException e) {
    			/* log error here */
    		}
    	}
    
    	@Override
    	public void onMessage(Message message) {
    		/* process message here */
    	}
    
    }
