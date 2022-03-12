package ru.ilyai.lab2.handler;

import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.exception.InvalidParamsException;
import ru.ilyai.lab2.exception.UnavailableServerException;
import ru.ilyai.lab2.helper.ResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.UndeclaredThrowableException;

@Provider
public class UndeclaredThrowableErrorHandler implements ExceptionMapper<UndeclaredThrowableException> {
    @Override
    public Response toResponse(UndeclaredThrowableException exception) {
        Throwable undeclaredThrowable = exception.getUndeclaredThrowable();

        switch (undeclaredThrowable.getClass().getName()){
            case "ru.ilyai.lab2.exception.DataNotFoundException":
                return new DataNotFoundErrorHandler().toResponse((DataNotFoundException) undeclaredThrowable);
            case "ru.ilyai.lab2.exception.InvalidParamsException":
            case "ru.ilyai.lab2.exception.ConvertException":
                return new InvalidParamsErrorHandler().toResponse((InvalidParamsException) undeclaredThrowable);
            case "ru.ilyai.lab2.exception.UnavailableServerException":
                return new UnavailableServerErrorHandler().toResponse((UnavailableServerException) undeclaredThrowable);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ResponseBuilder.buildErrorResponse("Internal Server Error"))
            .build();
    }
}
