package com.flink.dev.models;

import java.io.Serializable;

public class RideEventAggregation implements Serializable {
    private long windowStart;
    private long windowEnd;
    private long rideCount;
    private double totalFare;
    private double totalDistance;
    private double avgDuration;
    private double avgFare;
    private double avgDistance;
    private double avgDriverRating;
    private double avgUserRating;
    private long cancelledCount;
    private long completedCount;
    private double maxFare;
    private double maxDistance;
    private double maxDriverRating;
    private double maxUserRating;
    private double totalDuration;
    private double maxDuration;
    private double avgPassengerCount;
    private int maxPassengerCount;
    private long promoUsageCount;
    private double promoDiscountTotal;
    private long cashPaymentCount;
    private long cardPaymentCount;
    private long walletPaymentCount;

    public RideEventAggregation() {}

    public RideEventAggregation(long windowStart, long windowEnd,long rideCount, double totalFare, double totalDistance, double avgDuration, double avgFare, 
                       double avgDistance, double avgDriverRating, double avgUserRating, long cancelledCount, long completedCount,
                       double maxFare, double maxDistance, 
                       double maxDriverRating, double maxUserRating, 
                       double totalDuration, double maxDuration, double avgPassengerCount, int maxPassengerCount, 
                       long promoUsageCount, double promoDiscountTotal, long cashPaymentCount, long cardPaymentCount, long walletPaymentCount) {
        this.windowStart = windowStart;
        this.windowEnd = windowEnd;
        this.rideCount = rideCount;
        this.totalFare = totalFare;
        this.totalDistance = totalDistance;
        this.avgDuration = avgDuration;
        this.avgFare = avgFare;
        this.avgDistance = avgDistance;
        this.avgDriverRating = avgDriverRating;
        this.avgUserRating = avgUserRating;
        this.cancelledCount = cancelledCount;
        this.completedCount = completedCount;
        this.maxFare = maxFare;
        this.maxDistance = maxDistance;
        this.maxDriverRating = maxDriverRating;
        this.maxUserRating = maxUserRating;
        this.totalDuration = totalDuration;
        this.maxDuration = maxDuration;
        this.avgPassengerCount = avgPassengerCount;
        this.maxPassengerCount = maxPassengerCount;
        this.promoUsageCount = promoUsageCount;
        this.promoDiscountTotal = promoDiscountTotal;
        this.cashPaymentCount = cashPaymentCount;
        this.cardPaymentCount = cardPaymentCount;
        this.walletPaymentCount = walletPaymentCount;
    }

    // Getters and Setters for all fields
    @Override
    public String toString() {
        return "RideEventAggregation{" +
                "windowStart=" + windowStart +
                "windowEnd=" + windowEnd +
                "rideCount=" + rideCount +
                ", totalFare=" + totalFare +
                ", totalDistance=" + totalDistance +
                ", avgDuration=" + avgDuration +
                ", avgFare=" + avgFare +
                ", avgDistance=" + avgDistance +
                ", avgDriverRating=" + avgDriverRating +
                ", avgUserRating=" + avgUserRating +
                ", cancelledCount=" + cancelledCount +
                ", completedCount=" + completedCount +
                ", maxFare=" + maxFare +
                ", maxDistance=" + maxDistance +
                ", maxDriverRating=" + maxDriverRating +
                ", maxUserRating=" + maxUserRating +
                ", totalDuration=" + totalDuration +
                ", maxDuration=" + maxDuration +
                ", avgPassengerCount=" + avgPassengerCount +
                ", maxPassengerCount=" + maxPassengerCount +
                ", promoUsageCount=" + promoUsageCount +
                ", promoDiscountTotal=" + promoDiscountTotal +
                ", cashPaymentCount=" + cashPaymentCount +
                ", cardPaymentCount=" + cardPaymentCount +
                ", walletPaymentCount=" + walletPaymentCount +
                '}';
    }
}
