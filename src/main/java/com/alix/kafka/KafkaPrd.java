package com.alix.kafka;

import com.alix.util.PropUtil;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
/**
 * @author yuyue
 * @version 2017-11-3 0003 19:03
 */
public class KafkaPrd {
    private final Producer<String, String> producer;
    public final static String TOPIC = "kafkatopic";

    private KafkaPrd(){
        producer = new Producer<String, String>(new ProducerConfig(PropUtil.load("producer_config.properties")));
    }

    void produce() {
        int messageNo = 1;
        final int COUNT = 10;

        int messageCount = 0;
        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "Hello kafka message :" + key;
            producer.send(new KeyedMessage<String, String>(TOPIC, key ,data));
            System.out.println(data);
            messageNo ++;
            messageCount++;
        }

        System.out.println("Producer端一共产生了" + messageCount + "条消息！");
    }

    public static void main( String[] args )
    {
        new KafkaPrd().produce();
    }

}
