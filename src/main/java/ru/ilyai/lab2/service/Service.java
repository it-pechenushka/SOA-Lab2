package ru.ilyai.lab2.service;

import ru.ilyai.lab2.exception.DataNotFoundException;
import ru.ilyai.lab2.exception.InvalidParamsException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface Service {
    @WebMethod
    @WebResult(name = "responseText")
    String changeEducationForm(@WebParam(name = "groupId") long groupId, @WebParam(name = "formOfEducation") String formOfEducation) throws InvalidParamsException, DataNotFoundException;

    @WebMethod
    @WebResult(name = "responseText")
    String expelStudents(@WebParam(name = "groupId") long groupId) throws DataNotFoundException, InvalidParamsException;
}
