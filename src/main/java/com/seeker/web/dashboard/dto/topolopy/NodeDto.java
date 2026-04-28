package com.seeker.web.dashboard.dto.topolopy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeDto {

    private String agentId;
    private String agentName;
    private String agentType;
    private double errorRate;

    public void setNodeAgent(NodeAgentDto nodeAgent) {
        this.agentName = nodeAgent.getAgentName();
        this.agentType = nodeAgent.getAgentType();
    }

}
