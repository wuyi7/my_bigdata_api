package com.wuyi.kafkatest;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @Author: WuYi at 2023-04-06 14:40
 * @Description:
 * @Version:1.0
 */
public class AdminTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties props = new Properties();
        //kafka集群地址
        props.put("bootstrap.servers", "hadoop102:9092");//node1:9092,node2:9092,node3:9092
        //消费者组名称(如果不指定,会自动生成一个,但一个都指定,方便管理)
        props.put("group.id", "myGroup");
        //是否自动提交offset,true表示自动提交
        props.put("enable.auto.commit", "true");
        //自动提交偏移量时的时间间隔ms值
        props.put("auto.commit.interval.ms", "1000");
        //配置offset重置位置
        //如果有offset记录就从记录的位置开始消费
        //如果没有记录offset,earliest表示从最开始的数据,latest表示从最新的数据,none报错
        props.put("auto.offset.reset", "earliest");

        List<ConsumerRecord> list = new ArrayList<>();
        KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(props);
        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = consumer.offsetsForTimes();


        AdminClient adminClient = AdminClient.create(props);
        ArrayList<String> topicList = new ArrayList<>();
        topicList.add("first");
        DescribeConsumerGroupsResult describeConsumerGroupsResult = adminClient.describeConsumerGroups(topicList);
        Map<String, KafkaFuture<ConsumerGroupDescription>> stringKafkaFutureMap = describeConsumerGroupsResult.describedGroups();
//        for (String s : stringKafkaFutureMap.keySet()) {
//            System.out.println(s);
//            System.out.println(stringKafkaFutureMap.get(s));
//        }
        ListConsumerGroupsResult listConsumerGroupsResult = adminClient.listConsumerGroups();
        KafkaFuture<Collection<ConsumerGroupListing>> all = listConsumerGroupsResult.all();
        Collection<ConsumerGroupListing> consumerGroupListings = all.get();
        for (ConsumerGroupListing consumerGroupListing: consumerGroupListings) {
            System.out.println(consumerGroupListing.toString());
        }
    }
}
