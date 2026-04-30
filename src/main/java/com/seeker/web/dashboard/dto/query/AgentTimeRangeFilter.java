package com.seeker.web.dashboard.dto.query;

import lombok.Getter;

@Getter
public class AgentTimeRangeFilter {

    private final Long startTime;
    private final Long endTime;
    private final String agentId;

    private AgentTimeRangeFilter(Long startTime, Long endTime, String agentId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.agentId = agentId;
    }

    public static AgentTimeRangeFilter of(Long startTime, Long endTime, String agentId) {
        return new AgentTimeRangeFilter(startTime, endTime, agentId);
    }

}
