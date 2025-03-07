package com.flink.dev.utils;

import com.flink.dev.models.RideEvent;
import com.flink.dev.models.RideEventAggregation;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RideEventMapper {
    private final Gson gson;

    // Constructor initializes Gson instance
    public RideEventMapper() {
        this.gson = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setPrettyPrinting()
        .create();
    }

    // Method to convert JSON string to RideEvent object
    public RideEvent mapJsonToRideEvent(String json) {
        return gson.fromJson(json, RideEvent.class);
    }

    // Convert RideEventAggregation object to JSON string (Serialization)
    public String mapRideEventAggToJson(RideEventAggregation rideEvent) {
        return gson.toJson(rideEvent);
    }
}

