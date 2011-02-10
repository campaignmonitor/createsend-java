package com.createsend.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    public static Configuration Current = new Configuration();
        
    private Properties properties;
    private Configuration() {
        properties = new Properties();

        try {
            properties.load(ClassLoader.getSystemResourceAsStream("com/createsend/util/config.properties"));
                        
            InputStream createsendProperties = ClassLoader.getSystemResourceAsStream("createsend.properties");
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
    
    public String getApiKey() {
        return properties.getProperty("createsend.apikey");
    }
    
    public String getApiEndpoint() {
        return properties.getProperty("createsend.endpoint");
    }
    
    public String getWrapperVersion() {
        return properties.getProperty("createsend.version");
    }
    
    public boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("createsend.logging"));
    }
}
