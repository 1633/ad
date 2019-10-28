package com.imooc.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.imooc.ad.mysql.vo.MySqlRowData;
import com.imooc.ad.sender.ISender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Seven
 * @date 2019/10/28 23:52
 * @description 具体的投递实现类 这里用的是kafka组件(MQ也是可以的)
 */
@Component(value = "kafkaSender")
public class KafkaSender implements ISender {

    @Value("{adconf.kafka.topic}")
    private String topic;
    private final KafkaTemplate kafkaTemplate;
    @Autowired
    public KafkaSender(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    /**
     * 测试监听方法
     */
    @KafkaListener(topics = {"adconf.kafka.topic"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        final Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            final Object message = kafkaMessage.get();
            final MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka procssMysqlRowData: " + JSON.toJSONString(rowData));
        }
    }

}
