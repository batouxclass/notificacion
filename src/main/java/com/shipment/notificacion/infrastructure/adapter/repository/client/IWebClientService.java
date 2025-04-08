package com.shipment.notificacion.infrastructure.adapter.repository.client;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * Interface to create a WebClient instance.
 *
 * @author Diego Cortes
 */
public interface IWebClientService {

    /**
     * Returns the Instance of a web client.
     *
     * @param url the URL of the endpoint.
     * @return web client the instance type.
     */
    WebClient getWebClient(String url);
}
