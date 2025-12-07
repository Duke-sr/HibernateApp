package com.duke.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConfig {

    @Bean
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils,
                                                         @Value("${spring.application.name}") String appName,
                                                         @Value("${server.port}") int port) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        config.setAppname(appName);
        config.setHostname("127.0.0.1");
        config.setIpAddress("127.0.0.1");
        config.setPreferIpAddress(true);
        config.setNonSecurePort(port);
        return config;
    }
}