package com.wallet

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation._
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.web.client.RestTemplate

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoRepositories
@EnableSpringDataWebSupport
class WalletAppConfiguration {

  @Bean def restTemplate():RestTemplate =  new RestTemplate()
}
