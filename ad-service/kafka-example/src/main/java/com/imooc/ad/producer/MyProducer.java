package com.imooc.ad.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Seven
 * @date 2019/10/30 23:42
 * @description 消息生产者
 */
public class MyProducer {

    private static KafkaProducer<String, String> producer;

    static {
        final Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.169.129:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);
    }

    public static void sendMessageForgetResult(String value) {
        final ProducerRecord<String, String> record = new ProducerRecord<>(
                "kafka-example", "name", value + "ForGetResult");
        producer.send(record);
        producer.close();
    }

    /**
     * 同步发送消息
     * @param value
     * @throws Exception
     */
    private static void sendMessageSync(String value) throws Exception {
        final ProducerRecord<String, String> record = new ProducerRecord<>(
                "kafka-example", "name", value + "sync");
        final RecordMetadata result = producer.send(record).get();
        System.out.println(result.topic() + "******************");
        System.out.println(result.partition() + "******************");
        System.out.println(result.offset() + "******************");
        producer.close();
    }

    /**
     * 异步发送消息
     * @param value
     */
    private static void sendMessageCallback(String value) {
        final ProducerRecord<String, String> record = new ProducerRecord<>(
                "kafka-example", "name", value + "callback");
        producer.send(record, new MyProducerCallback());
        producer.close();
    }

    public static void main(String[] args) throws Exception {
//        sendMessageForgetResult("test");
//        sendMessageSync("test");
        sendMessageCallback("test");
    }

    /**
     * 回调类
     */
    private static class MyProducerCallback implements Callback {

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e !=null) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(recordMetadata.topic() + "******************");
            System.out.println(recordMetadata.partition() + "******************");
            System.out.println(recordMetadata.offset() + "******************");
            System.out.println("回调马上返回...");
        }

    }

}
