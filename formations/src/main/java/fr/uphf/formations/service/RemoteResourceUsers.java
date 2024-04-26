package fr.uphf.formations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RemoteResourceUsers {

    private final WebClient webClient;

    @Autowired
    public RemoteResourceUsers(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9000").build();
    }

    public Mono<String> fetchRemoteResource() {
        return webClient.get()
                .uri("/utilisateurs")
                .retrieve()
                .bodyToMono(String.class);
    }
}
