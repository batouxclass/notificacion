package com.shipment.notificacion.infrastructure.adapter.repository.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebClientServiceTest {

    @Mock
    private WebClient.Builder webClientBuilderMock;

    @Mock
    private WebClient webClientMock;

    @InjectMocks
    private WebClientService webClientService;

    @Test
    void getWebClient_shouldReturnWebClientWithCorrectBaseUrl() {
        String testUrl = "http://example.com";
        when(webClientBuilderMock.baseUrl(testUrl)).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);

        WebClient result = webClientService.getWebClient(testUrl);

        assertEquals(webClientMock, result);
    }
}