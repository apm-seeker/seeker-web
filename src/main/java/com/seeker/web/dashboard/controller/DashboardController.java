package com.seeker.web.dashboard.controller;

import com.seeker.web.dashboard.dto.requset.DashboardRequest;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
import com.seeker.web.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/topology")
    public TopologyDto getTopology(DashboardRequest request) {
        return dashboardService.getTopology(request);
    }

}
