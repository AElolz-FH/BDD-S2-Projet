package fr.uphf.utilisateurs.ressources;

import jakarta.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
    public class CarteApiService {

        @Autowired
        private WebClient.Builder webClient;

    @Builder
    @Getter
    @Setter
    public static class CartesItemListDTO {
        private String numero;
        private LocalDateTime dateExpiration;
    }

        public CartesItemListDTO[] listerCartes()
        {
            return webClient.baseUrl("http://cartes/")
                    .build()
                    .get()
                    .uri("/cartes")
                    .accept(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_JSON))
                    .retrieve()
                    .bodyToMono(CartesItemListDTO[].class)
                    .block();
        }


    }


