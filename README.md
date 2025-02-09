# Real-Time Ride Event Processing Pipeline

## Overview
This project demonstrates a real-time data processing pipeline for ride events using modern data tools. It simulates ride event data ingestion, processes it in real-time, and visualizes key metrics.

## Architecture
The pipeline consists of the following components:

1. **Data Ingestor (Python):** Simulates ride events and publishes them to Kafka.
2. **Event Storage (Kafka):** Acts as a message broker to store ride event streams.
3. **Real-Time Processor (Apache Flink):** Consumes ride events from Kafka, processes them in real-time, and writes the results back to Kafka.
4. **Kafka to ClickHouse Integration:** A Kafka table engine in ClickHouse is used to ingest processed data from Kafka into ClickHouse.
5. **Real-Time Data Warehouse (ClickHouse):** Stores processed ride event data for fast querying.
6. **Visualization (Grafana):** Connects to ClickHouse to visualize ride metrics in real-time.

## Data Flow
1. The Python ingestor generates ride event data and publishes it to a Kafka topic.
2. Flink reads from Kafka, processes the events (e.g., aggregations, filtering), and writes the results back to Kafka.
3. ClickHouse consumes processed ride event data from Kafka using the Kafka table engine.
4. Grafana queries ClickHouse to visualize real-time ride analytics.

## Setup
### Prerequisites
- Docker & Docker Compose
- Python >= 3.9
- Apache Kafka
- Apache Flink
- ClickHouse
- Grafana

### Steps to Run
1. **Start Services:**
   ```sh
   make up
   ```
2. **Stop and Clean Services:**
   ```sh
   make down
   ```
3. **Full Cleanup (Remove All Docker Images):**
   ```sh
   make cleanup
   ```

## Contributing
Feel free to open issues or submit pull requests to improve the project!

