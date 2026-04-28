package com.seeker.web.dashboard.dto.requset;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardRequest {

    private String startTime;
    private String endTime;
    private String agentId;

}
