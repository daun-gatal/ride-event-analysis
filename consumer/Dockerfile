FROM maven:3.9.8-eclipse-temurin-8 AS builder

COPY kafka /opt/kafka
WORKDIR /opt/kafka

RUN mvn clean package

FROM flink:1.20.0-scala_2.12-java8
WORKDIR /opt/flink/lib
COPY --from=builder --chown=flink:flink /opt/kafka/target/kafka-1.0-SNAPSHOT.jar /opt/flink/lib/kafka-1.0-SNAPSHOT.jar