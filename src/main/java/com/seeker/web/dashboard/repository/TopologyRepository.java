package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.query.TimeRangeFilter;
import com.seeker.web.dashboard.dto.topolopy.AgentId;
import com.seeker.web.dashboard.dto.topolopy.EdgeDto;
import com.seeker.web.dashboard.dto.topolopy.NodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TopologyRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<NodeDto> getTopologyNodes(TimeRangeFilter timeRangeFilter) {
        String nodeSql = """
            SELECT
                agent_id AS agentId,
                countIf(status_code >= 400) / count() AS errorRate
            FROM span
            WHERE start_time
                  BETWEEN fromUnixTimestamp64Milli(:startTime)
                      AND fromUnixTimestamp64Milli(:endTime)
            GROUP BY agent_id
        """;

        return namedParameterJdbcTemplate.query(
                nodeSql,
                new BeanPropertySqlParameterSource(timeRangeFilter),
                nodeRowMapper
        );
    }

    public List<EdgeDto> getTopologyEdges(TimeRangeFilter timeRangeFilter) {
        String edgeSql = """
            SELECT
                parent_agent_id AS fromAgentId,
                agent_id AS toAgentId,
                count() / nullIf((:endTime - :startTime) / 1000.0, 0) AS tps,
                avg(elapsed_time) AS avgLatency,
                countIf(status_code >= 400) / count() AS errorRate
            FROM span
            WHERE start_time
                  BETWEEN fromUnixTimestamp64Milli(:startTime)
                      AND fromUnixTimestamp64Milli(:endTime)
            GROUP BY agent_id, parent_agent_id
        """;

        return namedParameterJdbcTemplate.query(
                edgeSql,
                new BeanPropertySqlParameterSource(timeRangeFilter),
                edgeRowMapper
        );
    }

    private final RowMapper<NodeDto> nodeRowMapper = ((rs, rowNum) -> {
        return NodeDto
                .builder()
                .agentId(AgentId.of(rs.getString("agentId")))
                .errorRate(rs.getDouble("errorRate"))
                .build();
    });

    private final RowMapper<EdgeDto> edgeRowMapper = ((rs, rowNum) -> {
        return EdgeDto
                .builder()
                .fromAgentId(AgentId.of(rs.getString("fromAgentId")))
                .toAgentId(AgentId.of(rs.getString("toAgentId")))
                .tps(rs.getDouble("tps"))
                .avgLatency(rs.getDouble("avgLatency"))
                .errorRate(rs.getDouble("errorRate"))
                .build();
    });
}
