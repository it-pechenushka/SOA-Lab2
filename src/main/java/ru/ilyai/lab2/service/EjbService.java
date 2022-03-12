package ru.ilyai.lab2.service;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EjbService {
    public static Object getServiceFromEjb(String name) throws NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return new InitialContext(properties).lookup(name);
    }
}
