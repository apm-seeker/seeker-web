package com.seeker.web.dashboard.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopologyRequest {

    private Long startTime;
    private Long endTime;

}
