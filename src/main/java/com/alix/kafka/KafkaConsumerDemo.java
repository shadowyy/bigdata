package com.alix.kafka;

import com.alix.util.PropUtil;
import com.google.gson.JsonParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author yuyue
 * @version 2017-11-3 0003 11:05
 */
public class KafkaConsumerDemo {

    public static void main(String[] args) throws Exception {
        new KafkaConsumerDemo().consume();
    }


    public void consume() {

        JsonParser jsonParser = new JsonParser();

        // and the consumer
        KafkaConsumer<String, String> consumer = null;
        try {
            Properties props = PropUtil.load("consumer_config.properties");
            consumer = new KafkaConsumer<>(props);

            //subscribe topics
            consumer.subscribe(Arrays.asList("kafkatopic"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset -> %d, key -> %s, value -> %s", record.offset(), record.key(), record.value());

                    //switch (record.topic()) {
                    //    case "hello":
                    //
                    //        JsonObject jObj = (JsonObject)jsonParser.parse(record.value());
                    //        switch (jObj.get("type").getAsString()) {
                    //            case "test":
                    //
                    //                long latency = System.currentTimeMillis() - jObj.get("t").getAsLong();
                    //                System.out.println(latency);
                    //
                    //                break;
                    //            case "marker":
                    //
                    //                break;
                    //            default:
                    //                break;
                    //        }
                    //        break;
                    //    case "test":
                    //
                    //        break;
                    //    default:
                    //        throw new IllegalStateException("Shouldn't be possible to get message on topic " + record.topic());
                    //}
                }
            }
        } finally {
            if (consumer != null) {
                consumer.close();
            }
        }
    }
}
