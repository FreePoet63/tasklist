package by.razlivinsky.tasklist.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * DataSourceConfig class provides configuration for managing DataSource and obtaining connections.
 *
 * @author razlivinsky
 * @since 11.03.2024
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final DataSource dataSource;

    /**
     * Retrieves a connection from the configured DataSource.
     *
     * @return the database connection
     */
    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}