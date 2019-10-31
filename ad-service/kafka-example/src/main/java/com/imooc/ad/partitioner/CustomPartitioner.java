package com.imooc.ad.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @author Seven
 * @date 2019/10/31 22:14
 * @description kafka消息分区器
 */
public class CustomPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        final List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        final int numPartitions = partitionInfos.size();
        // 判断key
        if (null == keyBytes || !(key instanceof String)) {
            throw new InvalidRecordException("kafka message must have key");
        }
        // 只有一个分区则分返回第一个
        if (numPartitions == 1) {
            return 0;
        }
        if (value.equals(key)) {
            return numPartitions - 1;
        }

        return Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }

}
