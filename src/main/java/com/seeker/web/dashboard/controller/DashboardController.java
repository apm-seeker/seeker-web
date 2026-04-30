package com.seeker.web.dashboard.controller;

import com.seeker.web.dashboard.dto.metrics.AgentMetricsDto;
import com.seeker.web.dashboard.dto.request.AgentTimeRangeRequest;
import com.seeker.web.dashboard.dto.request.TimeRangeRequest;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.service.AgentMetricsService;
import com.seeker.web.dashboard.service.TopologyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final TopologyService topologyService;
    private final AgentMetricsService agentMetricsService;

    @GetMapping("/topology")
    public TopologyDto getTopology(
            @ModelAttribute TimeRangeRequest timeRangeRequest
    ) {
        return topologyService.getTopology(timeRangeRequest);
    }

    @GetMapping("/metrics")
    public AgentMetricsDto getTopology(
            @ModelAttribute AgentTimeRangeRequest agentTimeRangeRequest
    ) {
        return agentMetricsService.getAgentMetrics(agentTimeRangeRequest);
    }

}
