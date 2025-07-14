package org.thisway.vehicle.triplog.interfaces;

import lombok.Value;
import org.springframework.data.domain.Page;
import org.thisway.vehicle.triplog.domain.TripLog;
import org.thisway.vehicle.triplog.domain.TripLogInfo;

import java.time.LocalDateTime;
import java.util.List;

public class TripLogDto {

    @Value
    public static class TripLogResponse {
        List<TripLogInfo.TripLogBriefInfo> tripLogs;
        Integer totalPages;
        Long totalElements;
        Integer currentPage;
        Integer size;

        public TripLogResponse(Page<TripLog> tripLogs) {
            this.tripLogs = tripLogs.getContent().stream()
                    .map(TripLogInfo.TripLogBriefInfo::new)
                    .toList();
            this.totalPages = tripLogs.getTotalPages();
            this.totalElements = tripLogs.getTotalElements();
            this.currentPage = tripLogs.getNumber();
            this.size = tripLogs.getSize();
        }
    }

//    @Value
//    public static class TripLogDetailResponse {
//        String carNumber;
//        LocalDateTime startTime;
//        LocalDateTime endTime;
//        Integer tripMeter;
//        Double avgSpeed;
//        String onAddress;
//        String offAddress;
//
//        public TripLogDetailResponse()
//    }

}
