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

import com.createsend.models.ApiErrorResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ErrorDeserialiser<T> {

    public ApiErrorResponse<T> getResponse(ClientResponse response) {
        ParameterizedType returnType = JerseyClientImpl.getGenericReturnType(this.<T>getClass(), 2);
        Type genericType = this.getClass().getGenericSuperclass();
        if (genericType instanceof ParameterizedType) {
            try {
                Field f = returnType.getClass().getDeclaredField("actualTypeArguments");
                f.setAccessible(true);
                f.set(returnType, ((ParameterizedType)genericType).getActualTypeArguments());
                f.setAccessible(false);
            } catch (NoSuchFieldException e) {
                // ok to ignore
            } catch (IllegalAccessException e) {
                // ok to ignore
            }
        }
        return response.getEntity(new GenericType<ApiErrorResponse<T>>(returnType));
    }
}
