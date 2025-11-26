package org.jpurquilla.framework.adapters.exceptions;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import jakarta.annotation.Priority;
import org.jpurquilla.domain.exceptions.PersonaValidationException;
import org.jpurquilla.domain.exceptions.PersonaNotFoundException;


import java.util.List;
import java.util.stream.Collectors;

@Provider
@Priority(1)
public class GlobalExceptionHandler  {
    /**
     * MANEJADOR 1: PersonaValidationException (400 Bad Request)
     *
     * Se lanza cuando falla validación de dominio.
     * Ejemplo: persona.validar() lanza esta excepción
     */
    @Provider
    public static class PersonaValidationExceptionMapper implements ExceptionMapper<PersonaValidationException> {

        /**
         * Convierte PersonaValidationException → ErrorResponseDTO (400)
         *
         * @param exception la excepción de validación
         * @return Response HTTP 400 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(PersonaValidationException exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    400,
                    "Validation Failed",
                    exception.getMessage(),
                    "/personas"
            );

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
    }

    /**
     * MANEJADOR 2: PersonaNotFoundException (404 Not Found)
     *
     * Se lanza cuando no se encuentra una persona.
     * Ejemplo: repository.findById() → orElseThrow(PersonaNotFoundException)
     */
    @Provider
    public static class PersonaNotFoundExceptionMapper implements ExceptionMapper<PersonaNotFoundException> {

        /**
         * Convierte PersonaNotFoundException → ErrorResponseDTO (404)
         *
         * @param exception la excepción de no encontrado
         * @return Response HTTP 404 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(PersonaNotFoundException exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    404,
                    "Not Found",
                    exception.getMessage(),
                    "/api/personas"
            );

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(error)
                    .build();
        }
    }

    /**
     * MANEJADOR 3: ConstraintViolationException (400 Bad Request)
     *
     * Se lanza cuando falla validación de DTOs.
     * Ejemplo: @NotBlank, @NotNull, @Min, @Max fallan
     *
     * ¿QUÉ ES ConstraintViolation?
     * - Es la anotación de validación que falló (@NotBlank, @Min, etc.)
     * - El mensaje describe qué validación falló
     */
    @Provider
    public static class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        /**
         * Convierte ConstraintViolationException → ErrorResponseDTO (400)
         *
         * @param exception la excepción de violación de restricción
         * @return Response HTTP 400 con ErrorResponseDTO y detalles
         */
        @Override
        public Response toResponse(ConstraintViolationException exception) {
            // Extrae todos los detalles de las violaciones
            List<String> detalles = exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            ErrorResponseDTO error = new ErrorResponseDTO(
                    400,
                    "Validation Failed",
                    "Los datos enviados no cumplen con las validaciones requeridas",
                    "/api/personas",
                    detalles
            );

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
    }

    /**
     * MANEJADOR 4: Exception genérica (500 Internal Server Error)
     *
     * Captura TODAS las excepciones no manejadas.
     * Es la última red de seguridad.
     */
    @Provider
    public static class GenericExceptionMapper implements ExceptionMapper<Exception> {

        /**
         * Convierte Exception → ErrorResponseDTO (500)
         *
         * @param exception la excepción no manejada
         * @return Response HTTP 500 con ErrorResponseDTO
         */
        @Override
        public Response toResponse(Exception exception) {
            ErrorResponseDTO error = new ErrorResponseDTO(
                    500,
                    "Internal Server Error",
                    "Error interno del servidor: " + exception.getMessage(),
                    "/api/personas"
            );

            // Log para debugging
            exception.printStackTrace();

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(error)
                    .build();
        }
    }
}
