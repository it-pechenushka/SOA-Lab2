package ru.ilyai.lab2.service;

import lombok.SneakyThrows;
import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.exception.InvalidParamsException;
import ru.ilyai.lab2.helper.ClientFactoryBuilder;
import ru.ilyai.lab2.model.FormOfEducation;
import ru.ilyai.lab2.model.StudyGroupDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class EduService {
    private final Client client;

    public EduService() {
        this.client = ClientFactoryBuilder.getClient();
    }

    @SneakyThrows
    public void changeEducationForm(long groupId, FormOfEducation formOfEducation) {
        StudyGroupDto studyGroup = new StudyGroupDto();
        studyGroup.setFormOfEducation(formOfEducation);

        Response response = client.target(ClientFactoryBuilder.getStorageServiceUrl() + "/" + groupId)
                .request(MediaType.TEXT_XML)
                .put(Entity.entity(studyGroup, MediaType.TEXT_XML));

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode())
            throw new DataNotFoundException("Study group not found!");

        if (response.getStatus() > 300)
            throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());
    }
}
