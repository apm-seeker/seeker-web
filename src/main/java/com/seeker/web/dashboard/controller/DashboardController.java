package com.seeker.web.dashboard.controller;

import com.seeker.web.dashboard.dto.request.TopologyRequest;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/topology")
    public TopologyDto getTopology(
            @ModelAttribute TopologyRequest topologyRequest
    ) {
        return dashboardService.getTopology(topologyRequest);
    }

}
