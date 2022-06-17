package com.api.apirickandmorty.client;

import com.api.apirickandmorty.response.CharacterResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RickAndMortyClient {

    private final WebClient webClient;
    private String baseUrl = "https://rickandmortyapi.com/api";

    public RickAndMortyClient(WebClient.Builder builder) {
        webClient = builder.baseUrl(baseUrl).build();
    }

    public Mono<CharacterResponse> findAndCharacterById(String id) {
        log.info("Buscando dados do personagem com id [{}]", id);

        return webClient
                .get()
                .uri( "/character/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os parametros Informados")))
                .bodyToMono(CharacterResponse.class);



    }
}
