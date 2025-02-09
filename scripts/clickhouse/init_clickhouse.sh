#!/bin/bash
echo "Initializing ClickHouse Tables..."

# Use environment variables or fallback to default values
CLICKHOUSE_USER="${CLICKHOUSE_ADMIN_USER}"
CLICKHOUSE_PASSWORD="${CLICKHOUSE_ADMIN_PASSWORD}"
CLICKHOUSE_INITSCRIPTS_DIR="${CLICKHOUSE_INITSCRIPTS_DIR}"

clickhouse-client --user="$CLICKHOUSE_USER" --password="$CLICKHOUSE_PASSWORD" < "$CLICKHOUSE_INITSCRIPTS_DIR/init_clickhouse.sql"

echo "ClickHouse tables initialized successfully!"
