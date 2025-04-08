package com.shipment.notificacion.application.services;

import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.domain.model.Condition;
import com.shipment.notificacion.domain.model.Forecast;
import com.shipment.notificacion.domain.model.Forecastday;
import com.shipment.notificacion.domain.model.Hour;
import com.shipment.notificacion.domain.model.Location;
import com.shipment.notificacion.domain.model.WeatherStatus;
import com.shipment.notificacion.domain.repository.UserRequest;
import com.shipment.notificacion.domain.repository.WeatherStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
class WeatherStatusServiceTest {

    @Mock
    private WeatherStatusRepository weatherStatusRepository;

    @InjectMocks
    private WeatherStatusService weatherStatusService;

    @Test
    void getShipmentStatuEmailReturnsBadRequestTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes()gmail.com", "10.0", "20.0");

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST &&
                        throwable.getMessage().contains("Email con formato incorrecto"))
                .verify();
    }

    @Test
    void getShipmentStatusLatitudeReturnsBadRequestTest() {
        UserRequest userRequest = new UserRequest("dfer.cortes@gmail.com", "100.0", "20.0");

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST &&
                        throwable.getMessage().contains("Latitud no valida. Debe ser un número entre -90 y 90."))
                .verify();
    }

    @Test
    void getShipmentStatusLongitudeReturnsBadRequestTest() {
        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "190.0");

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST &&
                        throwable.getMessage().contains("Longitud no valida. Debe ser un número entre -180 y 180."))
                .verify();
    }

    @Test
    void getShipmentStatusWeatherStatusNotFoundTest() {
        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "-75.6076758");
        when(weatherStatusRepository.check(any(UserRequest.class))).thenReturn(Mono.empty());

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void getShipmentStatusForecastDayNotFoundTest() {
        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "-75.6076758");
        WeatherStatus weatherStatus = new WeatherStatus(
                new Location(
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        6.1543519,
                        -75.6076758,
                        "txz_id",
                        456587954,
                        "localtme"),
                new Forecast(Collections.emptyList())
        );
        when(weatherStatusRepository.check(any(UserRequest.class))).thenReturn(Mono.just(weatherStatus));

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void getShipmentStatusForecastHourNotFoundTest() {
        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "-75.6076758");
        Forecastday forecastday = new Forecastday(null, "456587954", Collections.emptyList());
        WeatherStatus weatherStatus = new WeatherStatus(
                new Location(
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        6.1543519,
                        -75.6076758,
                        "txz_id",
                        456587954,
                        "localtme"),
                new Forecast(List.of(new Forecastday("2025-04-08", "456587954" ,Collections.emptyList()), forecastday))
        );
        when(weatherStatusRepository.check(any(UserRequest.class))).thenReturn(Mono.just(weatherStatus));

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void getShipmentStatusIsBuyerNotificationFalseTest() {

        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "-75.6076758");

        WeatherStatus weatherStatus = new WeatherStatus(
                new Location(
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        6.1543519,
                        -75.6076758,
                        "txz_id",
                        456587954,
                        "localtme"),
                new Forecast(List.of(
                        new Forecastday(
                                "2025-04-08",
                                "456587954",
                                Collections.emptyList()),
                        new Forecastday(
                                "2025-04-09",
                                "456587954",
                                List.of(new Hour("00:00", new Condition("...", "...", 0)),
                                        new Hour("01:00", new Condition("...", "...", 0)),
                                        new Hour("02:00", new Condition("...", "...", 0)),
                                        new Hour("03:00", new Condition("...", "...", 0)),
                                        new Hour("04:00", new Condition("...", "...", 0)),
                                        new Hour("05:00", new Condition("...", "...", 0)),
                                        new Hour("06:00", new Condition("...", "...", 0)),
                                        new Hour("07:00", new Condition("...", "...", 0)),
                                        new Hour(
                                                "2025-04-07 08:00",
                                                new Condition(
                                                        "Cielo cubierto",
                                                        "//cdn.weatherapi.com/weather/64x64/night/122.png",
                                                        1009)
                                        )
                                )
                        )
                ))
        );

        when(weatherStatusRepository.check(any(UserRequest.class))).thenReturn(Mono.just(weatherStatus));

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                                dto.getName().equals("Cali") &&
                                dto.getRegion().equals("Valle del Cauca") &&
                                dto.getCountry().equals("Colombia") &&
                                dto.getForecastDescription().equals("Cielo cubierto") &&
                                !dto.isBuyerNotification()
                )
                .verifyComplete();
    }

    @Test
    void getShipmentStatusIsBuyerNotificationTrueTest() {

        UserRequest userRequest = new UserRequest("test@example.com", "6.1543519", "-75.6076758");
        WeatherStatus weatherStatus = new WeatherStatus(
                new Location(
                        "Cali",
                        "Valle del Cauca",
                        "Colombia",
                        6.1543519,
                        -75.6076758,
                        "txz_id",
                        456587954,
                        "localtme"),
                new Forecast(List.of(
                        new Forecastday(
                                "2025-04-08",
                                "456587954",
                                Collections.emptyList()),
                        new Forecastday(
                                "2025-04-09",
                                "456587954",
                                List.of(new Hour("00:00", new Condition("...", "...", 0)),
                                        new Hour("01:00", new Condition("...", "...", 0)),
                                        new Hour("02:00", new Condition("...", "...", 0)),
                                        new Hour("03:00", new Condition("...", "...", 0)),
                                        new Hour("04:00", new Condition("...", "...", 0)),
                                        new Hour("05:00", new Condition("...", "...", 0)),
                                        new Hour("06:00", new Condition("...", "...", 0)),
                                        new Hour("07:00", new Condition("...", "...", 0)),
                                        new Hour(
                                        "2025-04-07 00:00",
                                        new Condition(
                                                "Lluvia muy fuerte",
                                                "//cdn.weatherapi.com/weather/64x64/night/122.png",
                                                123)
                                ))
                        )
                ))
        );

        when(weatherStatusRepository.check(any(UserRequest.class))).thenReturn(Mono.just(weatherStatus));

        Mono<NotificationResponseDTO> result = weatherStatusService.getShipmentStatus(userRequest);

        StepVerifier.create(result)
                .expectNextMatches(dto ->
                                dto.getName().equals("Cali") &&
                                dto.getRegion().equals("Valle del Cauca") &&
                                dto.getCountry().equals("Colombia") &&
                                dto.getForecastDescription().equals("Lluvia muy fuerte") &&
                                dto.isBuyerNotification()
                )
                .verifyComplete();
    }
}
