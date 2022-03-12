package ru.ilyai.lab2.helper;

import lombok.SneakyThrows;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.FileInputStream;
import java.security.KeyStore;

public class ClientFactoryBuilder {
    private static Client client;

    @SneakyThrows
    public static Client getClient() {
        client = ClientBuilder.newBuilder().build();
        return client;
    }

    public static String getStorageServiceUrl() {
        return System.getProperty("SERVICE_URL");
    }
}
