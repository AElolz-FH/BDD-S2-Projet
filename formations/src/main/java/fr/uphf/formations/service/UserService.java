package fr.uphf.formations.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uphf.formations.service.api.UtilisateurFromAPIDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
@Service
public class UserService {

    private final WebClient.Builder loadBalancedWebClientBuilder;

    @Autowired
    public UserService(@Qualifier("loadBalancedWebClientBuilder") WebClient.Builder loadBalancedWebClientBuilder) {
        this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder.baseUrl("http://localhost:9000/utilisateurs/");
    }

    public UtilisateurFromAPIDTO getUserData(Integer userId) throws JsonProcessingException {
        String response = loadBalancedWebClientBuilder.build()
                .get()
                .uri("/users/"+userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        UtilisateurFromAPIDTO utilisateur = objectMapper.readValue(response, UtilisateurFromAPIDTO.class);
        return utilisateur;
    }
}

 */