package com.java.kafka1.main;

import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

public class ProducerDemo {

	public static void main(String[] args) {
		String val ="127.0.0.1:9092";
		final org.slf4j.Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
		// S1 create producer properties
		Properties properties = new Properties();
		//from https://kafka.apache.org/documentation/#producerconfigs
		//BOOTSTRAP_SERVERS_CONFIG : A list of host/port pairs to use for establishing the initial connection to the Kafka cluster.FORMAT -host1:port1,host2:port2
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,val);
		//key & value serializer : help the producer to know we are sending to kafka and how kafka client will serialize (to bytes(0,1)) whatever we are sending to kafka
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName()); 
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
		
		
		// S2 create the producer - String,String=key,value
		KafkaProducer<String,String> producer = new KafkaProducer<String,String>(properties);
		// create a producer record - String,String=topic,value //New Topic Created here
		//for(int i=0;i<10;i++) {
		ProducerRecord<String,String> record = new ProducerRecord<String,String>( "Topic2","id_1", "aa1");
		ProducerRecord<String,String> record1 = new ProducerRecord<String,String>( "Topic2","id_2", "aa2");
		ProducerRecord<String,String> record2 = new ProducerRecord<String,String>( "Topic2", "id_1","aa3");
		ProducerRecord<String,String> record3 = new ProducerRecord<String,String>( "Topic2","id_2", "aa4");
		
		// S3 send data - asynchronous=
		producer.send(record,new Callback(){
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if(exception == null) {
					logger.info("Received new matadata\n" + "Topic:" + metadata.topic()+ "\n Partition:" + metadata.partition()+ "\n Offset:" + metadata.offset()+ "\n TimeStamp:" + metadata.timestamp() );
				}else {
					logger.error("Error while producing",exception);
				}
			};});
		producer.send(record1,new Callback(){
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if(exception == null) {
					logger.info("Received new matadata\n" + "Topic:" + metadata.topic()+ "\n Partition:" + metadata.partition()+ "\n Offset:" + metadata.offset()+ "\n TimeStamp:" + metadata.timestamp() );
				}else {
					logger.error("Error while producing",exception);
				}
			};});
		producer.send(record2,new Callback(){
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if(exception == null) {
					logger.info("Received new matadata\n" + "Topic:" + metadata.topic()+ "\n Partition:" + metadata.partition()+ "\n Offset:" + metadata.offset()+ "\n TimeStamp:" + metadata.timestamp() );
				}else {
					logger.error("Error while producing",exception);
				}
			};});
		producer.send(record3,new Callback(){
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				// TODO Auto-generated method stub
				if(exception == null) {
					logger.info("Received new matadata\n" + "Topic:" + metadata.topic()+ "\n Partition:" + metadata.partition()+ "\n Offset:" + metadata.offset()+ "\n TimeStamp:" + metadata.timestamp() );
				}else {
					logger.error("Error while producing",exception);
				}
			};});
		//producer.send(record);	//asynchronous - runs in bg-executed-programs exit
		 		
		//}
		producer.flush(); //to produce data
		producer.close();
	}

}
