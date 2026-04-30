package com.seeker.web.dashboard.controller;

import com.seeker.web.dashboard.dto.request.TimeRangeRequest;
import com.seeker.web.dashboard.dto.topolopy.TopologyDto;
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

    @GetMapping("/topology")
    public TopologyDto getTopology(
            @ModelAttribute TimeRangeRequest timeRangeRequest
    ) {
        return topologyService.getTopology(timeRangeRequest);
    }

}
