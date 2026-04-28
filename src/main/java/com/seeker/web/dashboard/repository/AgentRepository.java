package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.topolopy.NodeAgentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AgentRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<String, NodeAgentDto> getNodeAgentInfoMap(List<String> agentIds) {
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

        SqlParameterSource params = new MapSqlParameterSource("agentIds", agentIds);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> Map.entry(
                rs.getString("agentId"),
                NodeAgentDto.builder()
                        .agentName(rs.getString("agentName"))
                        .agentType(rs.getString("agentType"))
                        .build()
        )).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
