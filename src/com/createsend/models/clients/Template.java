package com.createsend.models.clients;

public class Template {
    public String TemplateID;
    public String Name;
    public String PreviewURL;
    public String ScreenshotURL;
    
    @Override
    public String toString() {
        return String.format("{ TemplateID: %s, Name: %s, PreviewURL: %s, ScreenshotURL: %s }",
            TemplateID, Name, PreviewURL, ScreenshotURL);
    }
}
