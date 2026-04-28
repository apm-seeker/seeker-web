package com.seeker.web.dashboard.repository;

import com.seeker.web.dashboard.dto.topolopy.NodeAgentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AgentRepository {

    private final JdbcTemplate jdbcTemplate;

    public NodeAgentDto getNodeAgentInfo(String agentId) {

        String nodeAgentSql = """
            SELECT
                agent_name AS agentName,
                agent_type AS agentType
            FROM agent
            WHERE agent_id = ?
        """;

        return jdbcTemplate.queryForObject(
                nodeAgentSql,
                nodeAgentDtoRowMapper,
                agentId
        );

    }

    private final RowMapper<NodeAgentDto> nodeAgentDtoRowMapper = ((rs, rowNum) -> {
        return NodeAgentDto
                .builder()
                .agentName(rs.getString("agentName"))
                .agentType(rs.getString("agentType"))
                .build();
    });

}
