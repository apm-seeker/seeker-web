package com.seeker.web.dashboard.service;

import com.seeker.web.dashboard.dto.metrics.AgentMetricsDto;
import com.seeker.web.dashboard.dto.query.AgentTimeRangeFilter;
import com.seeker.web.dashboard.dto.query.TimeRangeFilter;
import com.seeker.web.dashboard.dto.request.AgentTimeRangeRequest;
import com.seeker.web.dashboard.dto.topolopy.AgentId;
import com.seeker.web.dashboard.repository.AgentMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentMetricsService {

    private final AgentMetricsRepository agentMetricsRepository;

    public AgentMetricsDto getAgentMetrics(AgentTimeRangeRequest agentTimeRangeRequest) {

        AgentId agentId = AgentId.of(agentTimeRangeRequest.getAgentId());

        if (AgentId.USER.equals(agentId)) {
            TimeRangeFilter timeRangeFilter = TimeRangeFilter.of(
                    agentTimeRangeRequest.getStartTime(),
                    agentTimeRangeRequest.getEndTime()
            );
            return agentMetricsRepository.getUserMetrics(timeRangeFilter);
        }

        AgentTimeRangeFilter agentTimeRangeFilter = AgentTimeRangeFilter.of(
                agentTimeRangeRequest.getStartTime(),
                agentTimeRangeRequest.getEndTime(),
                agentTimeRangeRequest.getAgentId()
        );
        return agentMetricsRepository.getAgentMetrics(agentTimeRangeFilter);

    }

}
