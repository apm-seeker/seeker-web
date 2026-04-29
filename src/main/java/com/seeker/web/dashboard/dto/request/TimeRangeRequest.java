package com.seeker.web.dashboard.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimeRangeRequest {

    private Long startTime;
    private Long endTime;

}
