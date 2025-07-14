package org.thisway.vehicle.triplog.domain;

public interface TripLogStore {
    void storeTripLog(Boolean status, TripLogCommand.TripLogSaveInput tripLogSaveInput);

    void updateTripLog(TripLog tripLog, TripLogCommand.TripLogSaveInput tripLogSaveInput);
}
