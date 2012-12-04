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
package com.createsend.models.campaigns;

public class CampaignSummary {
    public int Recipients;
    public int TotalOpened;
    public int Clicks;
    public int Unsubscribed;
    public int SpamComplaints;
    public int Bounced;
    public int UniqueOpened;
    public int Forwards;
    public int Mentions;
    public int Likes;
    public String WebVersionURL;
    public String WebVersionTextURL;
    public String WorldviewURL;

    @Override
    public String toString() {
        return String.format(
            "{ Recipients: %s, TotalOpened: %s, Clicks: %s, Unsubscribed: %s, SpamComplaints: %s, Bounced: %s, UniqueOpened: %s, ForwardToAFriends: %s, TwitterTweets: %s, FacebookLikes: %s,  WebVersionURL: %s, WorldviewURL: %s }",
            Recipients, TotalOpened, Clicks, Unsubscribed, SpamComplaints, Bounced, UniqueOpened, Forwards, Mentions, Likes, WebVersionURL, WorldviewURL);
    }
}
