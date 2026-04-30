package com.seeker.web.dashboard.dto.topolopy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeDto {

    private static final AgentId USER_AGENT_ID = AgentId.of("-1");
    private static final String USER_AGENT_NAME = "USER";
    private static final String USER_AGENT_TYPE = "USER";

    private AgentId agentId;
    private String agentName;
    private String agentType;
    private double errorRate;

    public static NodeDto userNode() {
        return NodeDto.builder()
                .agentId(USER_AGENT_ID)
                .agentName(USER_AGENT_NAME)
                .agentType(USER_AGENT_TYPE)
                .build();
    }

    public void setNodeAgent(NodeAgentDto nodeAgent) {
        this.agentName = nodeAgent.getAgentName();
        this.agentType = nodeAgent.getAgentType();
    }

}
