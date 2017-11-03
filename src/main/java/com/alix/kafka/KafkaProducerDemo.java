package com.alix.kafka;

import com.alix.util.PropUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author yuyue
 * @version 2017-11-3 0003 10:07
 */
public class KafkaProducerDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerDemo.class);


    public static void main(String[] args) throws Exception {
        new KafkaProducerDemo().send();
    }

    private int total = 3;

    public void send() {

        long start = System.currentTimeMillis();
        System.out.println("Kafka Producer send msg start,total msgs:" + total);

        // set up the producer
        Producer<String, String> producer = null;
        try {
            Properties props = PropUtil.load("producer_config.properties");
            producer = new KafkaProducer<>(props);

            for (int i = 0; i < total; i++) {
                producer.send(new ProducerRecord<>("kafkatopic", String.valueOf(i), String.format("{\"type\":\"test\", \"t\":%d, " + "\"k\":%d}", System.currentTimeMillis(), i)));

                //// every so often send to a different topic
                //if (i % 10 == 0) {
                //    producer.send(new ProducerRecord<>("kafkatopic", String.format("{\"type\":\"marker\", \"t\":%d, \"k\":%d}", System.currentTimeMillis(), i)));
                //
                //    producer.flush();
                //    System.out.println("Sent m.lo.sg number " + i);
                //}
            }

        } finally {
            producer.close();
        }

        System.out.println("Kafka Producer send msg over,cost time:" + (System.currentTimeMillis() - start) + "ms");
    }

}
