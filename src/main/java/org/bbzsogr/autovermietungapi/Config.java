package org.bbzsogr.autovermietungapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class Config implements WebMvcConfigurer {
    public static Dotenv dotenv = Dotenv.configure().load();

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(dotenv.get("DB_URL"))
                .username(dotenv.get("DB_USERNAME"))
                .password(dotenv.get("DB_PASSWORD"))
                .build();
    }
}
