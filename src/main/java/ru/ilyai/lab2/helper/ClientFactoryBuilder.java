package ru.ilyai.lab2.helper;

import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class ClientFactoryBuilder {

    @SneakyThrows
    public static Client getClient() {
        return ClientBuilder.newBuilder().build();
    }

    public static String getServiceUrl() {
        return System.getProperty("SERVICE_URL");
    }
}
