package com.alix.service;

import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author shadowyy
 * @version 2017/11/5 0:45
 */
public class SparkStreamingFromFlumeToHBaseExample {
    private static final Pattern SPACE = Pattern.compile(" ");

    ///opt/spark/bin/spark-submit --class com.alix.service.SparkStreamingFromFlumeToHBaseExample /tmp/bigdata-1.0-SNAPSHOT-all.jar 100
    public static void main(String[] args) {
        //if (args.length == 0) {
        //    System.err.println("Usage: SparkStreamingFromFlumeToHBaseWindowingExample {master} {host} {port} {table} {columnFamily} {windowInSeconds} {slideInSeconds");
        //    System.exit(1);
        //}

        // String master = args[0];
        // String host = args[1];
        // int port = Integer.parseInt(args[2]);
        String tableName = "test";// args[3];
        String columnFamily = "f";// args[4];
        // int windowInSeconds = 3;// Integer.parseInt(args[5]);
        // int slideInSeconds = 1;// Integer.parseInt(args[5]);

        String zkQuorum = "localhost";
        String group = "test-consumer-group";
        String topicss = "xxx";
        int numThread=2;

        Duration batchInterval = new Duration(5000);
        // Duration windowInterval = new Duration(windowInSeconds * 1000);
        // Duration slideInterval = new Duration(slideInSeconds * 1000);

        SparkConf sparkConf = new SparkConf().setAppName("JavaKafkaWordCount");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));

        final Broadcast<String> broadcastTableName = jssc.sparkContext().broadcast(tableName);
        final Broadcast<String> broadcastColumnFamily = jssc.sparkContext().broadcast(columnFamily);

        // JavaDStream<SparkFlumeEvent> flumeStream = sc.flumeStream(host, port);

        Map<String, Integer> topicMap = new HashMap<>();
        String[] topics = topicss.split(",");
        for (String topic : topics) {
            topicMap.put(topic, numThread);
        }

        JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, zkQuorum, group, topicMap);

        //JavaDStream<String> lines = messages.map((Function<Tuple2<String, String>, String>) tuple2 -> tuple2._2());
        //JavaDStream<String> words = lines.flatMap((FlatMapFunction<String, String>) x -> Lists.newArrayList(SPACE.split(x)));

        JavaPairDStream<String, Integer> lastCounts;
        lastCounts = messages.map((Function<Tuple2<String, String>, String>) Tuple2::_2).flatMap((FlatMapFunction<String, String>) x -> Lists
                .newArrayList(SPACE.split(x))).mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1)).reduceByKey(
                        (Function2<Integer, Integer, Integer>) (x, y) -> x + y);

        lastCounts.foreach((Function2<JavaPairRDD<String, Integer>, Time, Void>) (values, time) -> {
            values.foreach((VoidFunction<Tuple2<String, Integer>>) tuple -> {
                HBaseCounterIncrementor incrementor = HBaseCounterIncrementor.getInstance(broadcastTableName.value(), broadcastColumnFamily.value());
                incrementor.incerment("Counter", tuple._1(), tuple._2());
                System.out.println("Counter:" + tuple._1() + "," + tuple._2());

            });
            return null;
        });

        jssc.start();

    }
}
