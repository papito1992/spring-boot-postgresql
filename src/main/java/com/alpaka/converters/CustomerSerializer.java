package com.alpaka.converters;

import com.alpaka.model.customerLogs.CustomerActivity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomerSerializer extends StdSerializer<CustomerActivity> {

    public CustomerSerializer() {
        this(null);
    }

    public CustomerSerializer(Class<CustomerActivity> t) {
        super(t);
    }

    @Override
    public void serialize(
        CustomerActivity customerActivity, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("visitedEndpoint", customerActivity.getVisitedEndpoint());
        jsonGenerator.writeNumberField("responseStatus", customerActivity.getResponseStatus());
        jsonGenerator.writeStringField("actionName", customerActivity.getActionName());
        jsonGenerator.writeStringField("ipAddress", customerActivity.getIpAddress());
        jsonGenerator.writeEndObject();
    }
}
