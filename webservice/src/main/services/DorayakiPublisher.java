package main.services;

import javax.xml.ws.Endpoint;

public class DorayakiPublisher {
    public static void main(String[] args) {

        Endpoint.publish("http://localhost:8081/ws/ratelimiter", new DorayakiServiceImpl());
    }
}
