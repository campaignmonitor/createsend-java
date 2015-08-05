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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static Configuration Current = new Configuration();

    private Properties properties;
    private Configuration() {
        properties = new Properties();

        try {
            InputStream configProperties = getClass().getClassLoader().getResourceAsStream("com/createsend/util/config.properties");
            if (configProperties == null) {
                throw new FileNotFoundException("Could not find config.properties");
            }
            properties.load(configProperties);
                        
            InputStream createsendProperties = getClass().getClassLoader().getResourceAsStream("createsend.properties");
            if(createsendProperties != null) {
                properties.load(createsendProperties);
            }   
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }

    public void addPropertiesFile(String filename) throws IOException {
        properties.load(ClassLoader.getSystemResourceAsStream(filename));
    }

    public String getApiEndpoint() {
        return properties.getProperty("createsend.endpoint");
    }

    public String getOAuthBaseUri() {
        return properties.getProperty("createsend.oauthbaseuri");
    }

    public String getWrapperVersion() {
        return properties.getProperty("createsend.version");
    }

    public boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("createsend.logging"));
    }
}
