package com.createsend.util;

import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.PagedResult;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.ResourceFactory;

public interface JerseyClient {
    public <T> T get(Class<T> klass, String... pathElements) throws CreateSendException;
    public <T> T get(Class<T> klass, MultivaluedMap<String, String> queryString,
            String... pathElements) throws CreateSendException;   
    public <T> T get(Class<T> klass, ErrorDeserialiser<?> errorDeserialiser, 
            String... pathElements) throws CreateSendException;
    public <T> T get(Class<T> klass, MultivaluedMap<String, String> queryString, 
            ResourceFactory resourceFactory, String... pathElements) throws CreateSendException;
    public <T> PagedResult<T> getPagedResult(Integer page, Integer pageSize, String orderField, 
            String orderDirection, MultivaluedMap<String, String> queryString, String... pathElements) 
            throws CreateSendException;
    
    public <T> T post(Class<T> klass, Object entity, String... pathElements) throws CreateSendException;
    public <T> T post(Class<T> klass, Object entity, ErrorDeserialiser<?> errorDeserialiser, 
            String...pathElements) throws CreateSendException;
    
    public void put(Object entity, String... pathElements) throws CreateSendException;
    public void put(Object entity, ErrorDeserialiser<?> errorDeserialiser, String... pathElements) throws CreateSendException;
    
    public void delete(String... pathElements) throws CreateSendException;
}
