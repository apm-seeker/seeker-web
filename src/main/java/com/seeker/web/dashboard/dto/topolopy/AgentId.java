package com.seeker.web.dashboard.dto.topolopy;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class AgentId {

    public static final AgentId USER = AgentId.of("-1");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }

}
