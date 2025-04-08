package com.shipment.notificacion.infrastructure.adapter.repository.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implementation of webclient service to build a webclient object.
 *
 * @author Diego Cortes
 */
@Service
public class WebClientService implements IWebClientService {

    private final WebClient.Builder webClientBuilder;

    /**
     * Instantiates a WebClient Service.
     */
    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public WebClient getWebClient(String url) {
        return webClientBuilder.baseUrl(url).build();
    }
}