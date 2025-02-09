package com.flink.dev.operators;

import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import com.flink.dev.models.RideEvent;
import com.flink.dev.models.RideEventAggregation;

public class RideEventWindowFunction implements AllWindowFunction<
        RideEvent,             
        RideEventAggregation,   
        TimeWindow> {          

    @Override
    public void apply(            
            TimeWindow window,        
            Iterable<RideEvent> events,  
            Collector<RideEventAggregation> out) {  

        long rideCount = 0;
        double totalFare = 0;
        double totalDistance = 0;
        double totalDuration = 0;
        double totalDriverRating = 0;
        double totalUserRating = 0;
        int totalPassengers = 0;
        long cancelledCount = 0;
        long completedCount = 0;
        long promoUsageCount = 0;
        double promoDiscountTotal = 0;
        long cashPaymentCount = 0;
        long cardPaymentCount = 0;
        long walletPaymentCount = 0;
        
        double maxFare = 0;
        double maxDistance = 0;
        double maxDuration = 0;
        double maxDriverRating = 0;
        double maxUserRating = 0;
        int maxPassengerCount = 0;
        
        for (RideEvent event : events) {
            rideCount++;
            totalFare += event.getFare();
            totalDistance += event.getDistanceKm();
            totalDuration += event.getDurationMin();
            totalDriverRating += event.getDriverRating();
            totalUserRating += event.getUserRating();
            totalPassengers += event.getPassengerCount();
            
            if ("cancelled".equalsIgnoreCase(event.getStatus())) {
                cancelledCount++;
            }
            if ("completed".equalsIgnoreCase(event.getStatus())) {
                completedCount++;
            }
            if (event.getPromoCode() != null && !event.getPromoCode().isEmpty()) {
                promoUsageCount++;
                promoDiscountTotal += event.getFare() * 0.1; // Example 10% discount assumption
            }
            if ("cash".equalsIgnoreCase(event.getPaymentMethod())) {
                cashPaymentCount++;
            } else if ("credit_card".equalsIgnoreCase(event.getPaymentMethod()) || "debit_card".equalsIgnoreCase(event.getPaymentMethod())) {
                cardPaymentCount++;
            } else if ("wallet".equalsIgnoreCase(event.getPaymentMethod())) {
                walletPaymentCount++;
            }
            
            maxFare = Math.max(maxFare, event.getFare());
            maxDistance = Math.max(maxDistance, event.getDistanceKm());
            maxDuration = Math.max(maxDuration, event.getDurationMin());
            maxDriverRating = Math.max(maxDriverRating, event.getDriverRating());
            maxUserRating = Math.max(maxUserRating, event.getUserRating());
            maxPassengerCount = Math.max(maxPassengerCount, event.getPassengerCount());
        }

        double avgFare = rideCount > 0 ? totalFare / rideCount : 0;
        double avgDistance = rideCount > 0 ? totalDistance / rideCount : 0;
        double avgDuration = rideCount > 0 ? totalDuration / rideCount : 0;
        double avgDriverRating = rideCount > 0 ? totalDriverRating / rideCount : 0;
        double avgUserRating = rideCount > 0 ? totalUserRating / rideCount : 0;
        double avgPassengerCount = rideCount > 0 ? (double) totalPassengers / rideCount : 0;

        RideEventAggregation aggregation = new RideEventAggregation(
                window.getStart(),
                window.getEnd(),
                rideCount, 
                totalFare, 
                totalDistance, 
                avgDuration, 
                avgFare,
                avgDistance, 
                avgDriverRating, 
                avgUserRating, 
                cancelledCount, 
                completedCount,
                maxFare, 
                maxDistance, 
                maxDriverRating, 
                maxUserRating, 
                totalDuration, 
                maxDuration, 
                avgPassengerCount, 
                maxPassengerCount, 
                promoUsageCount, 
                promoDiscountTotal, 
                cashPaymentCount, 
                cardPaymentCount, 
                walletPaymentCount
        );

        out.collect(aggregation);
    }
}
