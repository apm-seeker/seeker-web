package com.seeker.web.dashboard.service;

import com.seeker.web.dashboard.dto.query.TimeRangeFilter;
import com.seeker.web.dashboard.dto.request.TopologyRequest;
import com.seeker.web.dashboard.dto.topolopy.AgentId;
import com.seeker.web.dashboard.dto.topolopy.EdgeDto;
import com.seeker.web.dashboard.dto.topolopy.NodeAgentDto;
import com.seeker.web.dashboard.dto.topolopy.NodeDto;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.repository.AgentRepository;
import com.seeker.web.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;
    private final AgentRepository agentRepository;

    public TopologyDto getTopology(TopologyRequest topologyRequest) {

        TimeRangeFilter timeRangeFilter = TimeRangeFilter.of(
                topologyRequest.getStartTime(), topologyRequest.getEndTime()
        );

        List<NodeDto> nodes = dashboardRepository.getTopologyNodes(timeRangeFilter);
        List<EdgeDto> edges = dashboardRepository.getTopologyEdges(timeRangeFilter);

        enrichNodesWithAgentInfo(nodes);
        nodes.add(NodeDto.userNode());

        return TopologyDto
                .builder()
                .nodes(nodes)
                .edges(edges)
                .build();
    }

    private void enrichNodesWithAgentInfo(List<NodeDto> nodes) {
        List<AgentId> agentIds = nodes.stream()
                .map(NodeDto::getAgentId)
                .toList();
        Map<AgentId, NodeAgentDto> agentInfoMap = agentRepository.getNodeAgentInfoMap(agentIds);

        for (NodeDto node : nodes) {
            NodeAgentDto nodeAgent = agentInfoMap.get(node.getAgentId());
            if (nodeAgent != null) {
                node.setNodeAgent(nodeAgent);
            }
        }
    }

}
