package com.salary.management.inventory_service.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.salary.management.inventory_service")
public class MongoDbConfiguration extends AbstractReactiveMongoConfiguration {

    private final String dbPort;
    private final String dbName;

    public MongoDbConfiguration(@Value("${spring.data.mongodb.port}") String dbPort,
                                @Value("${spring.data.mongodb.database}") String dbName) {
        this.dbPort = dbPort;
        this.dbName = dbName;
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
