package com.seeker.web.dashboard.dto.topolopy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeAgentDto {

    private String agentName;
    private String agentType;

}
