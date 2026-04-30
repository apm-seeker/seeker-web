package com.seeker.web.dashboard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeRangeRequest {

    private Long startTime;
    private Long endTime;

}
