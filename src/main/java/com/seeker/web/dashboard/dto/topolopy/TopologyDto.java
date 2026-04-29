package com.seeker.web.dashboard.dto.topolopy;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TopologyDto {

    private List<NodeDto> nodes;
    private List<EdgeDto> edges;

}
