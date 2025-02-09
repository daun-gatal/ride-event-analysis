-- Kafka Table (Staging)
CREATE TABLE IF NOT EXISTS flink_sink_stream (
    window_start DateTime64(3),      
    window_end DateTime64(3),        
    ride_count UInt64,               -- Total rides
    total_fare Float64,              -- Sum of fares
    total_distance Float64,          -- Sum of distances
    total_duration Float64,          -- Sum of durations
    avg_fare Float64,                -- Average fare
    avg_distance Float64,            -- Average distance
    avg_duration Float64,            -- Average duration
    avg_driver_rating Float64,       -- Average driver rating
    avg_user_rating Float64,         -- Average user rating
    cancelled_count UInt64,          -- Number of canceled rides
    completed_count UInt64,          -- Number of completed rides
    max_fare Float64,                -- Maximum fare
    max_distance Float64,            -- Maximum distance
    max_driver_rating Float64,       -- Maximum driver rating
    max_user_rating Float64,         -- Maximum user rating
    max_duration Float64,            -- Maximum duration
    avg_passenger_count Float64,     -- Average number of passengers
    max_passenger_count UInt8,       -- Maximum passenger count
    promo_usage_count UInt64,        -- Promo code usage count
    promo_discount_total Float64,    -- Total discount from promo codes
    cash_payment_count UInt64,       -- Count of cash payments
    card_payment_count UInt64,       -- Count of card payments
    wallet_payment_count UInt64      -- Count of wallet payments
) ENGINE = Kafka()
SETTINGS
    kafka_broker_list = 'kafka:29092',
    kafka_topic_list = 'flink-sink',
    kafka_group_name = 'clickhouse',
    kafka_format = 'JSONEachRow';

-- MergeTree Table (Final Storage)
CREATE TABLE IF NOT EXISTS flink_sink (
    window_start DateTime64(3),
    window_end DateTime64(3),
    ride_count UInt64,
    total_fare Float64,
    total_distance Float64,
    total_duration Float64,
    avg_fare Float64,
    avg_distance Float64,
    avg_duration Float64,
    avg_driver_rating Float64,
    avg_user_rating Float64,
    cancelled_count UInt64,
    completed_count UInt64,
    max_fare Float64,
    max_distance Float64,
    max_driver_rating Float64,
    max_user_rating Float64,
    max_duration Float64,
    avg_passenger_count Float64,
    max_passenger_count UInt8,
    promo_usage_count UInt64,
    promo_discount_total Float64,
    cash_payment_count UInt64,
    card_payment_count UInt64,
    wallet_payment_count UInt64
) ENGINE = MergeTree()
ORDER BY (window_start);

-- Materialized View to Transfer Kafka Data to MergeTree
CREATE MATERIALIZED VIEW IF NOT EXISTS flink_sink_stream_v 
TO flink_sink AS
SELECT * FROM flink_sink_stream;
