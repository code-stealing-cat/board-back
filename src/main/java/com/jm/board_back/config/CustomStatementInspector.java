package com.jm.board_back.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

/**
 * 쿼리 로그에 쌓는 statement_inspector
 */
@Slf4j
public class CustomStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        log.info("SQL: {}", sql);
        return sql;
    }
}