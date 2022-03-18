package ru.ilyai.lab2;

import ru.ilyai.lab2.service.ServiceImpl;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                System.getProperty("ORIGIN_URL") + "/service",
                new ServiceImpl());
    }
}
