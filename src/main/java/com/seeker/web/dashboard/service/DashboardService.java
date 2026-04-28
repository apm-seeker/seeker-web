package com.seeker.web.dashboard.service;

import com.seeker.web.dashboard.dto.requset.DashboardRequest;
import com.seeker.web.dashboard.dto.topolopy.EdgeDto;
import com.seeker.web.dashboard.dto.topolopy.NodeDto;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public TopologyDto getTopology(DashboardRequest request) {
        List<NodeDto> nodes = dashboardRepository.getTopologyNodes(request);
        List<EdgeDto> edges = dashboardRepository.getTopologyEdges(request);

        return TopologyDto
                .builder()
                .nodes(nodes)
                .edges(edges)
                .build();
    }

}
