package com.roy.sqwaimai.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.roy.sqwaimai.dao")
public class DaoConfiguration {
}
