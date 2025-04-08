package com.shipment.notificacion.infrastructure.adapter.repository.client;

import com.shipment.notificacion.common.exception.NotificationException;
import com.shipment.notificacion.common.response.WeatherStatusResult;
import com.shipment.notificacion.domain.model.WeatherStatus;
import com.shipment.notificacion.domain.repository.UserRequest;
import com.shipment.notificacion.domain.repository.WeatherStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

/**
 * Client implementation to call the API for getting Weather status.
 *
 * @author Diego Cortes
 */
@Service
public class WeatherStatusRepositoryImpl implements WeatherStatusRepository {

    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(WeatherStatusRepositoryImpl.class);

    /**
     * The Constant CONNECTION_SERVER_ERROR.
     */
    protected static final String CONNECTION_SERVER_ERROR =
            "An error occurred on connecting to the service server: %s";

    /**
     * The Constant RESULT_IS_NOT_EXPECTED.
     */
    private static final String RESULT_IS_NOT_EXPECTED = "The value we received isn't what we expected. : %s";

    /**
     * The weather server.
     */
    @Value("${weather.key}")
    private String weatherKey;

    /**
     * The weather server.
     */
    @Value("${weather.server}")
    private String weatherServer;

    /**
     * The weather url.
     */
    @Value("${weather.uri}")
    private String weatherUri;

    /**
     * The web client service of WebClient type.
     */
    private final IWebClientService webClientService;

    /**
     * Instantiates a WeatherStatusRepositoryImpl service.
     *
     * @param webClientService the web client service of WebClient type.
     */
    public WeatherStatusRepositoryImpl(IWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @Override
    public Mono<WeatherStatus> check(UserRequest location) {
        WebClient client = webClientService.getWebClient(weatherServer);
        String uri = String.format(weatherUri, weatherKey, location.latitude(), location.longitude());

        return client
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WeatherStatus.class)
                .onErrorResume(WebClientRequestException.class, wrex -> {
                    String errorMessage = String.format(CONNECTION_SERVER_ERROR, wrex.getMessage());
                    logger.error(errorMessage, wrex);
                    return Mono.error(new NotificationException(errorMessage));
                });
    }
}
