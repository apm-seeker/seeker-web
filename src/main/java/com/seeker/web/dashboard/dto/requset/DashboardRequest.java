package com.seeker.web.dashboard.dto.requset;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardRequest {

    private Long startTime;
    private Long endTime;
    private String agentId;

}
