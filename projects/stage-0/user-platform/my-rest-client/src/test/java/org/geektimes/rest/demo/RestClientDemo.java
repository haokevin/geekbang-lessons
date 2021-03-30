package org.geektimes.rest.demo;

import org.geektimes.rest.Domain.User;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;


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
        User user = new User("kevin", 28);
        Entity entity = Entity.entity(user, "application/json");


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://www.baidu.com")      // WebTarget
                .request() // Invocation.Builder
                .post(entity);                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);

    }


}
