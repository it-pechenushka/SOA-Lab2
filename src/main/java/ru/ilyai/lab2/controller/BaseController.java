package ru.ilyai.lab2.controller;

import lombok.SneakyThrows;
import ru.ilyai.lab2.exception.ConvertException;
import ru.ilyai.lab2.helper.ResponseBuilder;
import ru.ilyai.lab2.model.FormOfEducation;
import ru.ilyai.lab2.service.EduService;
import ru.ilyai.lab2.service.ExpelService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/{group-id}")
public class BaseController {
    @PathParam("group-id")
    private long groupId;
    private EduService eduService;
    private ExpelService expelService;

    public BaseController(){
        eduService = new EduService();
        expelService = new ExpelService();
    }

    @POST
    @Path("/expel-all")
    @Produces("text/xml;charset=utf-8")
    public Response expelAll() {
        expelService.expelStudents(groupId);
        return Response.ok(ResponseBuilder.buildTextResponse("Students successfully expelled!")).build();
    }

    @SneakyThrows
    @PUT
    @Path("/change-edu-form/{new-form}")
    @Produces("text/xml;charset=utf-8")
    public Response changeEducationForm(@PathParam("new-form") String form) {
        try {
            FormOfEducation formOfEducation = FormOfEducation.valueOf(form);
            eduService.changeEducationForm(groupId, formOfEducation);
        } catch (IllegalArgumentException e) {
            throw new ConvertException("Unexpected FormOfEducation value - " + form);
        }

        return Response.ok(ResponseBuilder.buildTextResponse("From of education has been successfully changed!")).build();
    }
}