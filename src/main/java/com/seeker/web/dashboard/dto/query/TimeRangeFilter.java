package com.seeker.web.dashboard.dto.query;

import lombok.Getter;

@Getter
public class TimeRangeFilter {

    private final Long startTime;
    private final Long endTime;

    private TimeRangeFilter(Long startTime, Long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeRangeFilter of(Long startTime, Long endTime) {
        return new TimeRangeFilter(startTime, endTime);
    }
}
