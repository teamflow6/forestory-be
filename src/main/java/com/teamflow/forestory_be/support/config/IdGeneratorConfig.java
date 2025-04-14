package com.teamflow.forestory_be.support.config;

import com.github.f4b6a3.tsid.TsidFactory;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Value("${server.node:0}")
    private int node;

    @Bean
    public TsidFactory tsidFactory() {
        TsidFactory factory = TsidFactory.builder()
            .withNode(node)
            .build();
        TsidGenerator.initialize(factory);
        return factory;
    }
}
