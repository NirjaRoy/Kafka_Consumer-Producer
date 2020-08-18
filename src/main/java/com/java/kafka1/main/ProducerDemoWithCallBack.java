package com.java.kafka1.main;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemoWithCallBack {

	public static void main(String[] args) {
		String val ="127.0.0.1:9092";
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
		// create a producer record - String,String=topic,value
		ProducerRecord<String,String> record = new ProducerRecord<String,String>( "first", "hey");
		// S3 send data - asynchronous=
		producer.send(record);	//asynchronous - runs in bg-executed-programs exit
		producer.flush(); 		//to produce data
		producer.close();

	}

}
