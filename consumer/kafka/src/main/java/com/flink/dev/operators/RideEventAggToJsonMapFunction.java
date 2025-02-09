package com.flink.dev.operators;

import com.flink.dev.models.RideEventAggregation;
import com.flink.dev.utils.RideEventMapper;
import org.apache.flink.api.common.functions.MapFunction;

public class RideEventAggToJsonMapFunction implements MapFunction<RideEventAggregation, String> {

    @Override
    public String map(RideEventAggregation event) {
        RideEventMapper mapper = new RideEventMapper();
        return mapper.mapRideEventAggToJson(event);
    }
}

