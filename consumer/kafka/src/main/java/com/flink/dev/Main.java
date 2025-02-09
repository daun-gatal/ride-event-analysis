package com.flink.dev;

import java.time.Duration;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;

import com.flink.dev.models.RideEvent;
import com.flink.dev.models.RideEventAggregation;
import com.flink.dev.operators.JsonToRideEventMapFunction;
import com.flink.dev.operators.RideEventAggToJsonMapFunction;
import com.flink.dev.operators.RideEventWindowFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

public class Main {

    public static void main(String[] args) throws Exception {
        // Set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create the FlinkKafkaConsumer
        KafkaSource<String> source = KafkaSource.<String>builder()
        .setBootstrapServers("kafka:29092")
        .setTopics("flink-source")
        .setGroupId("kafka-group")
        .setStartingOffsets(OffsetsInitializer.earliest())
        .setValueOnlyDeserializer(new SimpleStringSchema())
        .build();

        // Add the Kafka source to the data stream
        DataStream<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "flink-consumer");

        DataStream<RideEvent> rideEvent = stream
        .map(new JsonToRideEventMapFunction())
        .assignTimestampsAndWatermarks(
        WatermarkStrategy.<RideEvent>forBoundedOutOfOrderness(Duration.ofSeconds(5)) // Allow 5s lateness
            .withTimestampAssigner((event, timestamp) -> event.getTimestampNs() / 1_000_000) // Convert ns → ms
        );

        DataStream<RideEventAggregation> aggregatedStream = rideEvent
        .windowAll(TumblingEventTimeWindows.of(Duration.ofSeconds(10))) 
        .apply(new RideEventWindowFunction());

        DataStream<String> eventSink = aggregatedStream
        .map(new RideEventAggToJsonMapFunction());

        KafkaSink<String> sink = KafkaSink.<String>builder()
        .setBootstrapServers("kafka:29092")
        .setRecordSerializer(KafkaRecordSerializationSchema.builder()
            .setTopic("flink-sink")
            .setValueSerializationSchema(new SimpleStringSchema())
            .build()
        )
        .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
        .build();

        eventSink.sinkTo(sink);

        // Execute the Flink job
        env.execute("Flink Kafka Consumer");
    }
}
