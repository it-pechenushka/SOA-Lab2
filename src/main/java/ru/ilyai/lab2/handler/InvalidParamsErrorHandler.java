package ru.ilyai.lab2.handler;

import ru.ilyai.lab2.exception.ConvertException;
import ru.ilyai.lab2.exception.InvalidParamsException;
import ru.ilyai.lab2.helper.ResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidParamsErrorHandler implements ExceptionMapper<InvalidParamsException> {
    @Override
    public Response toResponse(InvalidParamsException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseBuilder.buildErrorResponse(exception.getMessage()))
                .build();
    }
}
