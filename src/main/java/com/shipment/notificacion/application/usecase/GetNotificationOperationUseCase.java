package com.shipment.notificacion.application.usecase;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.application.services.NotificationOperationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Servicio que recupera de la base de datos la operacion de notificación de un envio
 * de paquete a un comprador si este fue notificado con una posible demora de entrega por mal
 * tiempo.
 *
 * @author Diego Cortes
 */
@Service
public class GetNotificationOperationUseCase {

    private final NotificationOperationService service;

    public GetNotificationOperationUseCase(NotificationOperationService service) {
        this.service = service;
    }

    public Flux<NotificationOperationDTO> execute(String email) {
        return service.getOperation(email);
    }
}
