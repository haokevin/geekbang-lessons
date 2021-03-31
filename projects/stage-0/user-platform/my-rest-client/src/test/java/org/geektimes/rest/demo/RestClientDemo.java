package org.geektimes.rest.demo;

import org.geektimes.rest.Domain.User;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;


public class RestClientDemo {

    @Test
    public void testGet() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://www.baidu.com")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }

    @Test
    public void testPost() {
        User user = new User("shenhao11", 29);
        Entity entity = Entity.json(user);


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:8080/user/registerUser")      // WebTarget
                .request() // Invocation.Builder
                .header("Content-Type", "application/json")
                .post(entity);                                  //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }


}
