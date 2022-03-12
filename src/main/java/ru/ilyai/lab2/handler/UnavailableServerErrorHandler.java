package ru.ilyai.lab2.handler;

import ru.ilyai.lab2.exception.UnavailableServerException;
import ru.ilyai.lab2.helper.ResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.UndeclaredThrowableException;

@Provider
public class UnavailableServerErrorHandler implements ExceptionMapper<UnavailableServerException> {
    @Override
    public Response toResponse(UnavailableServerException exception) {
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(ResponseBuilder.buildErrorResponse(exception.getMessage()))
                .build();
    }

}
