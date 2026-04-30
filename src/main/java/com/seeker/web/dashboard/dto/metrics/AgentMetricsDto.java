package com.seeker.web.dashboard.dto.metrics;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgentMetricsDto {

    private Long totalCount;

    private Long errorCount;
    private Double errorRate;

    private Double p99;
    private Double p95;
    private Double p90;

}
