package com.java.kafka1.main;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {

	public static void main(String[] args) {
	
	}
	//Construcor
	public ConsumerDemo(){
		
	}
	public void run(){
		final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
	String topic = "Topic2";
	
	//logger.info("Creating consumer thread");
	//Runnable myConsumerThread= 
	Runnable myConsumerThread=	new ConsumerThread (topic);

		};
	
public class ConsumerThread implements Runnable{
		private CountDownLatch latch = new CountDownLatch(1);
		private KafkaConsumer<String,String> consumer;
		final org.slf4j.Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
		
		//Constructor
		public ConsumerThread(String topic) {
			String val ="127.0.0.1:9092";
				//Create consumer config/properties
			Properties properties = new Properties();		
				//Site -from https://kafka.ap’ache.org/documentation/#consumerconfigs
				//BOOTSTRAP_SERVERS_CONFIG : A list of host/port pairs to use for establishing the initial connection to the Kafka cluster.FORMAT -host1:port1,host2:port2
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,val);
			     //key & value deserializer : kafka sends bytes back to Consumer & creates string from it properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName()); properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName()); properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"My_group1");
				// AUTO_OFFSET_RESET_CONFIG = earliest/latest/none - read from ? of topic.
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
				//***Create the consumer*** - String,String=key,value
			consumer = new KafkaConsumer<String,String>(properties);
			 	//***Subscribe consumer to our topics*** - Keep adding topic names
			consumer.subscribe(Arrays.asList("Topic2"));
		}
		
		public void run() {
			try {
			//***Ask/poll for new data***
			while(true) {
				//Earlier Timeout Arg=long(Eg=100), Now KAfka 2.0.0- Arg=Duration.of (Eg-This)
				ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(100)); 
				for(ConsumerRecord<String,String> record : records) {
					logger.info("key:"+record.key()+ "   Value:" + record.value());
					logger.info("Partition:"+record.partition()+ "   Offset:" + record.offset());
				}
			}} catch(WakeupException e) {
				logger.info("Recieved shutdown signal");
			}	finally {
				consumer.close();
				//Tell main code that we are done with consumer
				latch.countDown();
			}
		}
		//This will go in catch()
		public void shutdown() {
			//wakeup() - Interrupts consumer.poll() with exception WakeUpException
			consumer.wakeup();
		}	
	}}


