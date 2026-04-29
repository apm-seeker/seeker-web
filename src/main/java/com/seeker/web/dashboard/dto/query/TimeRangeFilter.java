package com.seeker.web.dashboard.dto.query;

import com.seeker.web.dashboard.dto.request.TimeRangeRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TimeRangeFilter {

    private Long startTime;
    private Long endTime;

    public static TimeRangeFilter from(TimeRangeRequest timeRangeRequest) {
        return TimeRangeFilter
                .builder()
                .startTime(timeRangeRequest.getStartTime())
                .endTime(timeRangeRequest.getEndTime())
                .build();
    }
}
