package com.createsend.util;

import java.lang.reflect.ParameterizedType;

import com.createsend.models.ApiErrorResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;



public class ErrorDeserialiser<T> {

    public ApiErrorResponse<T> getResponse(ClientResponse response) {
        ParameterizedType type = JerseyClientImpl.getGenericReturnType(this.getClass(), 2);
        
        return response.getEntity(new GenericType<ApiErrorResponse<T>>(type));
    }
    
}
