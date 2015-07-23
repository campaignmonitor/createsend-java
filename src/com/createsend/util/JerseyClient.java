/**
 * Copyright (c) 2011 Toby Brain
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.createsend.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.createsend.models.PagedResult;
import com.createsend.util.exceptions.CreateSendException;
import com.createsend.util.jersey.ResourceFactory;

public interface JerseyClient {
	public AuthenticationDetails getAuthenticationDetails();
	public void setAuthenticationDetails(AuthenticationDetails authDetails);
	
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
    public <T> T post(Class<T> klass, MultivaluedMap<String, String> queryString, Object entity, String... pathElements) throws CreateSendException;

    public <T> T post(Class<T> klass, Object entity, 
            ErrorDeserialiser<?> errorDeserialiser, String... pathElements) throws CreateSendException;
    public <T> T post(String baseUri, Class<T> klass, Object entity, String... pathElements) throws CreateSendException;
    public <T> T post(String baseUri, Class<T> klass, Object entity,
            ErrorDeserialiser<?> errorDeserialiser, String... pathElements) throws CreateSendException;
    public <T> T post(Class<T> klass, Object entity,
            MediaType mediaType, String... pathElements) throws CreateSendException;
    public <T> T post(String baseUri, Class<T> klass, Object entity,
            MediaType mediaType, String... pathElements) throws CreateSendException;
    public <T> T post(String baseUri, Class<T> klass, Object entity,
            ErrorDeserialiser<?> errorDeserialiser,
            MediaType mediaType, String... pathElements) throws CreateSendException;

    public void put(Object entity, String... pathElements) throws CreateSendException;
    public <T> T put(Class<T> klass, Object entity, String... pathElements) throws CreateSendException;
    public void put(Object entity, MultivaluedMap<String, String> queryString, String... pathElements) throws CreateSendException;
    public void put(Object entity, ErrorDeserialiser<?> errorDeserialiser, String... pathElements) throws CreateSendException;
    
    public void delete(String... pathElements) throws CreateSendException;
	public void delete(MultivaluedMap<String, String> queryString, String... pathElements) throws CreateSendException;
}
