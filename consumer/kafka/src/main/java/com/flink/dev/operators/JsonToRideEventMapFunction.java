package com.flink.dev.operators;

import com.flink.dev.models.RideEvent;
import com.flink.dev.utils.RideEventMapper;
import org.apache.flink.api.common.functions.MapFunction;

public class JsonToRideEventMapFunction implements MapFunction<String, RideEvent> {
    
    @Override
    public RideEvent map(String json) {
        RideEventMapper mapper = new RideEventMapper();
        System.out.println("test:" + mapper.mapJsonToRideEvent(json));
        return mapper.mapJsonToRideEvent(json);
    }
}

