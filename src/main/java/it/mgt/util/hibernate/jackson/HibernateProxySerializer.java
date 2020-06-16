package it.mgt.util.hibernate.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.hibernate.Hibernate;

import java.io.IOException;

public class HibernateProxySerializer extends JsonSerializer<Object> {

    @Override
    public boolean isUnwrappingSerializer() {
        return true;
    }

    @Override
    public void serializeWithType(Object value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Object unproxied = Hibernate.unproxy(value);

        JsonSerializer<Object> serializer = serializers.findValueSerializer(unproxied.getClass());
        if (serializer.equals(this))
            throw new RuntimeException("Resolved self as delegated serializer");

        serializer.serialize(unproxied, gen, serializers);
    }
}
