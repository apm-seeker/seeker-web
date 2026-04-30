package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.metrics.AgentMetricsDto;
import com.seeker.web.dashboard.dto.query.AgentTimeRangeFilter;
import com.seeker.web.dashboard.dto.query.TimeRangeFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AgentMetricsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AgentMetricsDto getAgentMetrics(AgentTimeRangeFilter agentTimeRangeFilter) {
        String AgentMetricsSql = """
            SELECT
                count() AS totalCount,
                countIf(status_code >= 400) AS errorCount,
                countIf(status_code >= 400) / count() AS errorRate,
        
                quantile(0.99)(elapsed_time) AS p99,
                quantile(0.95)(elapsed_time) AS p95,
                quantile(0.90)(elapsed_time) AS p90
            FROM span
            WHERE agent_id = :agentId
              AND start_time
                  BETWEEN fromUnixTimestamp64Milli(:startTime)
                      AND fromUnixTimestamp64Milli(:endTime)
        """;

        return namedParameterJdbcTemplate.queryForObject(
                AgentMetricsSql,
                new BeanPropertySqlParameterSource(agentTimeRangeFilter),
                agentMetricsRowMapper
        );
    }


    public AgentMetricsDto getUserMetrics(TimeRangeFilter timeRangeFilter) {
        String AgentMetricsSql = """
            SELECT
                count() AS totalCount,
                countIf(status_code >= 400) AS errorCount,
                countIf(status_code >= 400) / count() AS errorRate,
        
                quantile(0.99)(elapsed_time) AS p99,
                quantile(0.95)(elapsed_time) AS p95,
                quantile(0.90)(elapsed_time) AS p90
            FROM trace
            WHERE start_time
                  BETWEEN fromUnixTimestamp64Milli(:startTime)
                      AND fromUnixTimestamp64Milli(:endTime)
        """;

        return namedParameterJdbcTemplate.queryForObject(
                AgentMetricsSql,
                new BeanPropertySqlParameterSource(timeRangeFilter),
                agentMetricsRowMapper
        );
    }

    private final RowMapper<AgentMetricsDto> agentMetricsRowMapper = ((rs, rowNum) -> {
        return AgentMetricsDto
                .builder()
                .totalCount(rs.getLong("totalCount"))
                .errorCount(rs.getLong("errorCount"))
                .errorRate(rs.getDouble("errorRate"))
                .p99(rs.getDouble("p99"))
                .p95(rs.getDouble("p95"))
                .p90(rs.getDouble("p90"))
                .build();
    });
}
