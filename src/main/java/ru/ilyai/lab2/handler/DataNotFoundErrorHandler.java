package ru.ilyai.lab2.handler;

import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.helper.ResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundErrorHandler implements ExceptionMapper<DataNotFoundException> {
    @Override
    public Response toResponse(DataNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ResponseBuilder.buildErrorResponse(exception.getMessage()))
                .build();
    }
}
