package com.learning.config.database.multi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Setter @Getter
@ConfigurationProperties(prefix = "datasource")
public class DatasourceProperties {
    private DBConfig mssql;
    private DBConfig mariadb;

    @Setter @Getter
    public static class DBConfig {
        private String url;
        private String username;
        private String password;
        private String dialect;
    }

    @PostConstruct
    public void init() {

    }
}
