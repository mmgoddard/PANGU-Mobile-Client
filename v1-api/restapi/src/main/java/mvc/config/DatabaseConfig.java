package mvc.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 * Created by Mark on 01/04/15.
 */
@Component
@ComponentScan(basePackages = "com.springapp.mvc")
@EnableWebMvc
@PropertySource("/WEB-INF/db.properties")
public class DatabaseConfig {
    @Value("${db.driverClassName}")private String driverClassName;
    @Value("${db.databaseUrl}")private String databaseUrl;
    @Value("${db.databaseUsername}")private String databaseUsername;
    @Value("${db.databasePassword}")private String databasePassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.info(databaseUrl);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}
