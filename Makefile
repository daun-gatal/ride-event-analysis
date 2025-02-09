DC = docker-compose

.PHONY: up down cleanup

up:
	@echo "Starting Docker Compose services..."
	mkdir -p .clickhouse
	chmod +x .clickhouse
	$(DC) up -d
	@echo "Services are up and running!"

down:
	@echo "Stopping and removing Docker Compose services..."
	$(DC) down -v
	rm -rf .clickhouse
	@echo "Cleanup complete: Services stopped, volumes removed, and .clickhouse directory deleted."

cleanup:
	@echo "Checking if Docker Compose is running..."
	@if [ -n "$$($(DC) ps -q)" ]; then \
		echo "Docker Compose is running, stopping it first..."; \
		$(MAKE) down; \
	else \
		echo "No running Docker Compose services found."; \
	fi
	@echo "Removing all Docker Compose-related images..."
	$(DC) down -v --rmi all
	@echo "Cleanup finished: All Docker Compose images removed."
