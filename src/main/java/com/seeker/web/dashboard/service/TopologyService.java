package com.seeker.web.dashboard.service;

import com.seeker.web.dashboard.dto.query.TimeRangeFilter;
import com.seeker.web.dashboard.dto.request.TimeRangeRequest;
import com.seeker.web.dashboard.dto.topolopy.AgentId;
import com.seeker.web.dashboard.dto.topolopy.EdgeDto;
import com.seeker.web.dashboard.dto.topolopy.NodeAgentDto;
import com.seeker.web.dashboard.dto.topolopy.NodeDto;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.repository.AgentRepository;
import com.seeker.web.dashboard.repository.TopologyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopologyService {

    private final TopologyRepository topologyRepository;
    private final AgentRepository agentRepository;

    public TopologyDto getTopology(TimeRangeRequest timeRangeRequest) {

        TimeRangeFilter timeRangeFilter = TimeRangeFilter.of(
                timeRangeRequest.getStartTime(), timeRangeRequest.getEndTime()
        );

        List<NodeDto> nodes = topologyRepository.getTopologyNodes(timeRangeFilter);
        List<EdgeDto> edges = topologyRepository.getTopologyEdges(timeRangeFilter);

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
