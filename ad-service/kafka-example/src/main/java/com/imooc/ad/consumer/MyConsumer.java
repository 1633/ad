package com.imooc.ad.consumer;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author Seven
 * @date 2019/10/31 22:38
 * @description 消息消费方
 */
public class MyConsumer {
    private static KafkaConsumer<String, String> consumer;
    final static Properties properties = new Properties();

    static {
        properties.put("bootstrap.servers", "192.168.169.129:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "KafkaStudy");
    }

    /**
     * 自动提交消息位移
     */
    private static void autoCommit() {
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("kafka-example"));

        try {
            while (true) {
                boolean flag = true;
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));
                    if ("done".equals(record.value())) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 同步提交消息位移
     */
    private static void syncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("kafka-example"));

        while (true) {
            boolean flag = true;
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic=%s, partition=%s, key=%s, value=%s",
                        record.topic(), record.partition(), record.key(), record.value()));
                if ("done".equals(record.value())) {
                    flag = false;
                }
            }
            try {
                consumer.commitSync();
            } catch (CommitFailedException ex) {
                System.out.println(ex.getMessage());
            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 异步提交 不会处理异常 不会重试 且位移可能出现覆盖即重复消费消息
     */
    private static void asyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("kafka-example"));

        while (true) {
            boolean flag = true;
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic=%s, partition=%s, key=%s, value=%s",
                        record.topic(), record.partition(), record.key(), record.value()));
                if ("done".equals(record.value())) {
                    flag = false;
                }
            }

            consumer.commitAsync();

            if (!flag) {
                break;
            }
        }
    }

    /**
     * 手动异步提交 并用回调函数处理结果
     */
    private static void asyncCommitWithCallback() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("kafka-example"));

        while (true) {
            boolean flag = true;
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));

            for (ConsumerRecord<String, String> record : records) {
                System.out.println(String.format("topic=%s, partition=%s, key=%s, value=%s",
                        record.topic(), record.partition(), record.key(), record.value()));
                if ("done".equals(record.value())) {
                    flag = false;
                }
            }

            consumer.commitAsync((map, e) -> {
                if (e != null) {
                    System.out.println("commit failed for offsets: " + e.getMessage());
                }
            });

            if (!flag) {
                break;
            }
        }
    }

    /**
     * 混合同步与异步提交消息位移 在finally中最大可能的处理正确
     */
    private static void  mixSyncAndAsyncCommit() {
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton("kafka-example"));

        try {
            while (true) {
                boolean flag = true;
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100L));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic=%s, partition=%s, key=%s, value=%s",
                            record.topic(), record.partition(), record.key(), record.value()));
                    if ("done".equals(record.value())) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
                consumer.commitAsync();
            }
        } catch (Exception e) {
            System.out.println("commit async error: " + e.getMessage());
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }

    }

    public static void main(String[] args) {
//        autoCommit();
//        syncCommit();
//        asyncCommit()
//        asyncCommitWithCallback();
        mixSyncAndAsyncCommit();
    }

}
