package no.gainmaster.user.persistence.configuration;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

//@Configuration
//public class InMemoryDatabaseConfiguration implements EnvironmentAware {
//
//    private Environment environment;
//
//    private PropertyResolver propertyResolver;
//
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
//    }
//
//    @Bean
//    public DataSource embeddedDataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder.setType(EmbeddedDatabaseType.H2).build();
//    }
//
//}
