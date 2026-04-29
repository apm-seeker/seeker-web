package com.seeker.web.dashboard.dto.topolopy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EdgeDto {

    private AgentId fromAgentId;
    private AgentId toAgentId;
    private double tps;
    private double avgLatency;
    private double errorRate;

}
