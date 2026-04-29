package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.topolopy.AgentId;
import com.seeker.web.dashboard.dto.topolopy.NodeAgentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AgentRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<AgentId, NodeAgentDto> getNodeAgentInfoMap(List<AgentId> agentIds) {
        if (agentIds.isEmpty()) {
            return Map.of();
        }

        String sql = """
            SELECT
                agent_id AS agentId,
                agent_name AS agentName,
                agent_type AS agentType
            FROM agent
            WHERE agent_id IN (:agentIds)
        """;

        List<String> agentIdValues = agentIds.stream()
                .map(AgentId::getValue)
                .toList();
        SqlParameterSource params = new MapSqlParameterSource("agentIds", agentIdValues);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
                Map.entry(
                        AgentId.of(rs.getString("agentId")),
                        mapNodeAgent(rs)
                )
            ).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
        );
    }

    private NodeAgentDto mapNodeAgent(ResultSet rs) throws SQLException {
        return NodeAgentDto.builder()
                .agentName(rs.getString("agentName"))
                .agentType(rs.getString("agentType"))
                .build();
    }
}
