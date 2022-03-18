package ru.ilyai.lab2.service;

import lombok.SneakyThrows;
import ru.ilyai.lab2.exception.ConvertException;
import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.exception.InvalidParamsException;
import ru.ilyai.lab2.helper.ClientFactoryBuilder;
import ru.ilyai.lab2.model.FormOfEducation;
import ru.ilyai.lab2.model.StudyGroupDto;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@WebService(endpointInterface = "ru.ilyai.lab2.service.Service")
public class ServiceImpl implements Service {
    private final Client client;

    public ServiceImpl() {
        this.client = ClientFactoryBuilder.getClient();
    }

    @WebMethod
    @WebResult(name = "responseText")
    public String changeEducationForm(@WebParam(name = "groupId") long groupId, @WebParam(name = "formOfEducation") String form) throws InvalidParamsException, DataNotFoundException {
        FormOfEducation formOfEducation;

        try {
            formOfEducation = FormOfEducation.valueOf(form);
        } catch (IllegalArgumentException e) {
            throw new ConvertException("Unexpected FormOfEducation value - " + form);
        }

        if (groupId <= 0)
            throw new InvalidParamsException("The 'groupId' param should be greater than 0");

        StudyGroupDto studyGroup = new StudyGroupDto();
        studyGroup.setFormOfEducation(formOfEducation);

        Response response = client.target(ClientFactoryBuilder.getServiceUrl() + "/base/groups/" + groupId)
                .request(MediaType.TEXT_XML)
                .put(Entity.entity(studyGroup, MediaType.TEXT_XML));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())
            throw new DataNotFoundException("Study group not found!");

        if (response.getStatus() > 300)
            throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());

        return "From of education has been successfully changed!";
    }

    @WebMethod
    @WebResult(name = "responseText")
    public String expelStudents(@WebParam(name = "groupId") long groupId) throws DataNotFoundException, InvalidParamsException {
        if (groupId <= 0)
            throw new InvalidParamsException("The 'groupId' param should be greater than 0");

        Response response = client.target(ClientFactoryBuilder.getServiceUrl() + "/base/groups/" +  groupId)
                .request(MediaType.TEXT_XML)
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())
            throw new DataNotFoundException("Study group not found!");

        if (response.getStatus() > 300)
            throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());

        StudyGroupDto studyGroup = response.readEntity(StudyGroupDto.class);
        StudyGroupDto studyGroupToUpdate = new StudyGroupDto();
        studyGroupToUpdate.setShouldBeExpelled(1);

        if (studyGroup.getStudentsCount() > 1){
            studyGroupToUpdate.setExpelledStudents(studyGroup.getExpelledStudents() + studyGroup.getStudentsCount() - 1);
            studyGroupToUpdate.setStudentsCount(1L);
        }

        response = client.target(ClientFactoryBuilder.getServiceUrl() + "/base/groups/" + groupId)
                .request(MediaType.TEXT_XML)
                .put(Entity.entity(studyGroupToUpdate, MediaType.TEXT_XML));

        if (response.getStatus() > 300)
            throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());

        return "Students successfully expelled!";
    }

}
