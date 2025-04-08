package com.shipment.notificacion.infrastructure.adapter.controller;

import com.shipment.notificacion.application.services.dto.NotificationOperationDTO;
import com.shipment.notificacion.application.services.dto.NotificationResponseDTO;
import com.shipment.notificacion.application.usecase.GetNotificationOperationUseCase;
import com.shipment.notificacion.application.usecase.GetShipmentStatusUseCase;
import com.shipment.notificacion.domain.repository.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Expose the Api Rest Resource to be call a Restful web service.
 *
 * @author Diego Cortes
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "Notificador de retrasos de envio de paqueteria por mal tiempo",
        description = "Consulta estado del tiempo en la localizacion del comprador, notifica al comprador de posibles retrasos en la entrega del paquete y guarda el registro de notificacion.")

public class ApiRestController {

    private final GetShipmentStatusUseCase getShipmentStatusUseCase;
    private final GetNotificationOperationUseCase getNotificationOperationUseCase;

    public ApiRestController(
            GetShipmentStatusUseCase getShipmentStatusUseCase,
            GetNotificationOperationUseCase getNotificationOperationUseCase) {
        this.getShipmentStatusUseCase = getShipmentStatusUseCase;
        this.getNotificationOperationUseCase = getNotificationOperationUseCase;
    }

    @PostMapping(path = "/forecast")
    @Operation(summary = "Obtiene el pronóstico del tiempo de mañana en la ubicación de entraga del paquete e indica si el comprador debe ser notificado.",
            description = "Este endpoint recibe una solicitud de ubicación del comprador y devuelve el pronóstico del tiempo. Si debe ser notificado por retraso en la entrega del paquete etonces se envia correo electronico al email suministrado y guarda registro en base de datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email y datos de la ubicación del usuario", required = true,
                    content = @Content(schema = @Schema(implementation = UserRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pronóstico obtenido exitosamente",
                            content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    public Mono<NotificationResponseDTO> getForecast(@RequestBody UserRequest userRequest) {
        return getShipmentStatusUseCase.execute(userRequest);
    }

    @GetMapping(path = "/notification/{email}")
    @Operation(summary = "Obtiene el historico de notificaciones de un comprador dado su correo electrónico",
            description = "Este endpoint devuelve las operaciones de notificación asociadas a un correo electrónico específico.",
            parameters = {
                    @Parameter(name = "email", in = ParameterIn.PATH, description = "Correo electrónico del comprador", required = true,
                            schema = @Schema(type = "string", example = "dfer.cortes@gmail.com"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operaciones de notificación encontradas",
                            content = @Content(schema = @Schema(implementation = NotificationOperationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No se encontraron notificaciones para este correo electrónico"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            })
    public Flux<NotificationOperationDTO> getNotification(@PathVariable("email") String email) {
        return getNotificationOperationUseCase.execute(email);
    }

}

