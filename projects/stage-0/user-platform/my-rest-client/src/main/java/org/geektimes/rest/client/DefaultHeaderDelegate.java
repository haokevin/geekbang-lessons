package org.geektimes.rest.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.RuntimeDelegate;

public class DefaultHeaderDelegate implements RuntimeDelegate.HeaderDelegate {

    private Class type;

    public DefaultHeaderDelegate(Class type) {
        this.type = type;
    }

    @Override
    public MediaType fromString(String value) {
        String[] strings = value.split("/");
        return new MediaType(strings[0], strings[1]);
    }

    @Override
    public String toString(Object value) {
        return value.toString();
    }
}
