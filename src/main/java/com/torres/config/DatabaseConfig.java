package com.torres.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseConfig {

    // DataSource Config
    // - application.yml : spring.datasource:
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return (DataSource) DataSourceBuilder
            .create()
            .type(DataSource.class)  // DBCP - org.apache.tomcat.jdbc.pool.DataSource
            .build();
    }


    // JDBC Config
    // - usage : @Autowired  @Qualifier("basicJdbcTemplate") private JdbcTemplate jdbcTemplate;
//    @Bean
//    public JdbcTemplate basicJdbcTemplate(
//        @Qualifier("dataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

}