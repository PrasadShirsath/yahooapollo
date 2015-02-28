package com.apollo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoRepositories
@EnableSpringDataWebSupport
public class ApolloConfiguration {
}
