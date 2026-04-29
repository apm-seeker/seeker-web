package com.seeker.web.dashboard.controller;

import com.seeker.web.dashboard.dto.request.TopologyRequest;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/topology")
    public TopologyDto getTopology(
            @RequestParam TopologyRequest topologyRequest
    ) {
        return dashboardService.getTopology(topologyRequest);
    }

}
