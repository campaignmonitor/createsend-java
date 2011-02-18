package com.createsend.models.lists;

import java.net.URI;

public class Webhook {
    public String WebhookID;
    public String[] Events; // TODO: Might want to move this to an enum
    public URI Url;
    public String Status; // TODO: Might want to move this to an enum
    public String PayloadFormat; // TODO: Might want to move this to an enum
}
