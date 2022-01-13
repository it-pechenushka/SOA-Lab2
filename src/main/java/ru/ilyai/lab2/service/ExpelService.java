package ru.ilyai.lab2.service;

import lombok.SneakyThrows;
import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.exception.InvalidParamsException;
import ru.ilyai.lab2.helper.ClientFactoryBuilder;
import ru.ilyai.lab2.model.StudyGroupDto;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ExpelService {
    private final Client client;

    public ExpelService() {
        this.client = ClientFactoryBuilder.getClient();
    }

    @SneakyThrows
    public void expelStudents(long groupId) {
        Response response = client.target(ClientFactoryBuilder.getStorageServiceUrl() + "/" + groupId)
                .request(MediaType.TEXT_XML)
                .get();

        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            throw new DataNotFoundException("Study group not found!");
        }
        if (response.getStatus() > 300) throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());

        StudyGroupDto studyGroup = response.readEntity(StudyGroupDto.class);
        StudyGroupDto studyGroupToUpdate = new StudyGroupDto();
        studyGroupToUpdate.setShouldBeExpelled(1);

        if (studyGroup.getStudentsCount() > 1){
            studyGroupToUpdate.setExpelledStudents(studyGroup.getExpelledStudents() + studyGroup.getStudentsCount() - 1);
            studyGroupToUpdate.setStudentsCount(1L);
        }

        response = client.target(ClientFactoryBuilder.getStorageServiceUrl() + "/" + groupId)
                .request(MediaType.TEXT_XML)
                .put(Entity.entity(studyGroupToUpdate, MediaType.TEXT_XML));

        if (response.getStatus() > 300) throw new InvalidParamsException("Storage request failed with status: " + response.getStatus());

    }
}
