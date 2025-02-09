from datetime import datetime, timedelta
import json
import time
import random
from confluent_kafka import Producer  # type: ignore

# Kafka configuration
KAFKA_BROKER = "kafka:29092"
KAFKA_TOPIC = "flink-source"

# Configure the producer
producer_conf = {"bootstrap.servers": KAFKA_BROKER}
producer = Producer(producer_conf)


def delivery_report(err, msg):
    if err is not None:
        print(f"Message delivery failed: {err}")
    else:
        print(f"Message delivered to {msg.topic()} [{msg.partition()}]")


# Function to generate enriched dummy booking data
def generate_dummy_data():
    status_list = ["requested", "accepted", "ongoing", "completed", "cancelled"]
    status = random.choice(status_list)
    cancellation_reason = (
        random.choice(["user_cancelled", "driver_cancelled", "system_cancelled", None])
        if status == "cancelled"
        else None
    )

    return {
        "booking_id": random.randint(100000, 999999),
        "user_id": random.randint(1, 10000),
        "driver_id": random.randint(1, 5000),
        "pickup_location": {
            "latitude": round(random.uniform(-90, 90), 6),
            "longitude": round(random.uniform(-180, 180), 6),
            "address": f"{random.randint(1, 9999)} Main St, City {random.randint(1, 100)}",
        },
        "dropoff_location": {
            "latitude": round(random.uniform(-90, 90), 6),
            "longitude": round(random.uniform(-180, 180), 6),
            "address": f"{random.randint(1, 9999)} Elm St, City {random.randint(1, 100)}",
        },
        "fare": round(random.uniform(5, 100), 2),
        "distance_km": round(random.uniform(1, 50), 2),
        "duration_min": random.randint(5, 120),
        "status": status,
        "payment_method": random.choice(
            ["credit_card", "debit_card", "cash", "wallet"]
        ),
        "timestamp_ns": time.time_ns(),
        "scheduled_time_ns": int(
            (datetime.now() + timedelta(minutes=random.randint(5, 1440))).timestamp()
            * 1e9
        ),
        "vehicle_type": random.choice(
            ["Sedan", "SUV", "Hatchback", "Motorbike", "Van"]
        ),
        "ride_type": random.choice(["standard", "premium", "pool", "business"]),
        "passenger_count": random.randint(1, 4),
        "driver_rating": round(random.uniform(3.5, 5.0), 1),
        "user_rating": round(random.uniform(3.5, 5.0), 1),
        "promo_code": random.choice(["DISCOUNT10", "FREERIDE", "SUMMER50", None]),
        "cancellation_reason": cancellation_reason,
    }


# Send messages in a loop
if __name__ == "__main__":
    try:
        while True:
            data = generate_dummy_data()
            json_data = json.dumps(data)
            producer.produce(
                KAFKA_TOPIC,
                key=str(data["booking_id"]),
                value=json_data,
                callback=delivery_report,
            )
            producer.flush()
    except KeyboardInterrupt:
        print("Producer stopped.")
