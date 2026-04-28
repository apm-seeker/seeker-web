package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.requset.DashboardRequest;
import com.seeker.web.dashboard.dto.topolopy.EdgeDto;
import com.seeker.web.dashboard.dto.topolopy.NodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<NodeDto> getTopologyNodes(DashboardRequest request) {
        String nodeSql = """
            SELECT
                agent_id AS agentId,
                countIf(status >= 400) / count() AS errorRate
            FROM span
            WHERE start_time BETWEEN :startTime AND :endTime
            GROUP BY agent_id
        """;

         return namedParameterJdbcTemplate.query(
                nodeSql,
                new BeanPropertySqlParameterSource(request),
                nodeRowMapper
        );
    }

    public List<EdgeDto> getTopologyEdges(DashboardRequest request) {
        String edgeSql = """
            SELECT
                parent_agent_id AS fromAgentId,
                agent_id AS toAgentId,
                count(*) / nullIf(dateDiff('second', :startTime, :endTime), 0) AS tps,
                avg(elapsed_time) AS avgLatency,
                countIf(status >= 400) / count() AS errorRate
            FROM span
            WHERE start_time BETWEEN :startTime AND :endTime
            GROUP BY agent_id, parent_agent_id
        """;

        return namedParameterJdbcTemplate.query(
                edgeSql,
                new BeanPropertySqlParameterSource(request),
                edgeRowMapper
        );
    }

    private final RowMapper<NodeDto> nodeRowMapper = ((rs, rowNum) -> {
        return NodeDto
                .builder()
                .agentId(rs.getString("agentId"))
                .errorRate(rs.getDouble("errorRate"))
                .build();
    });

    private final RowMapper<EdgeDto> edgeRowMapper = ((rs, rowNum) -> {
        return EdgeDto
                .builder()
                .fromAgentId(rs.getString("fromAgentId"))
                .toAgentId(rs.getString("toAgentId"))
                .tps(rs.getDouble("tps"))
                .avgLatency(rs.getDouble("avgLatency"))
                .errorRate(rs.getDouble("errorRate"))
                .build();
    });

}
