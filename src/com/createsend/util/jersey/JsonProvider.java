package com.createsend.util.jersey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class JsonProvider extends JacksonJsonProvider {
    private static DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void writeTo(Object value, Class<?> type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders,
            OutputStream entityStream) throws IOException {
        ObjectMapper mapper = locateMapper(type, mediaType);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders,
                entityStream);
    }
    
    @Override
    public Object readFrom(Class<Object> type, Type genericType,
            Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException {
        ObjectMapper mapper = locateMapper(type, mediaType);
        mapper.getDeserializationConfig().setDateFormat(apiDateFormat);
        mapper.getDeserializationConfig().set(
                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        
        return super.readFrom(type, genericType, annotations, mediaType, httpHeaders,
                entityStream);
    }

}
