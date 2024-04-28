package fr.uphf.formations.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.cert.CertPathBuilder;

    /*
    @LoadBalanced
    @Bean
    @Qualifier("loadBalancedWebClientBuilder")
    WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder().baseUrl("http://utilisateurs:9000/");
    }

     */

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}



