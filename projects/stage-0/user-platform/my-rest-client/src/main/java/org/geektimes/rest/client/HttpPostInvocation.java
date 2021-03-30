/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.geektimes.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;

/**
 * HTTP POST Method {@link Invocation}
 *
 * @since
 */
class HttpPostInvocation implements Invocation {

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> requestBody;

    HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> requestBody) {
        this.uri = uri;
        this.headers = headers;
        this.requestBody = requestBody;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            setRequestHeaders(connection);
            setRequestBody(connection);

            int statusCode = connection.getResponseCode();
            DefaultResponse response = new DefaultResponse();

            response.setConnection(connection);
            response.setStatus(statusCode);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRequestBody(HttpURLConnection connection) throws IOException {
        Object entity = requestBody.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        byte[] bytes = mapper.writeValueAsBytes(entity);
        connection.setDoOutput(true);
        connection.getOutputStream().write(bytes);
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }
}
