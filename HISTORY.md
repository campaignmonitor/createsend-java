# createsend-java history

## v8.0.0 - 4 March 2022

* Migrated from deprecated gradle plugin 'Maven' to 'Maven-publish'
* Set sourceComptability to Java 1.9
* Updated sourceComptability syntax
* Replaced gradle compile dependency with 'implementation'
* Replaced all instances of deprecated ``$buildDir`` with ``layout.buildDirectory``
* Replaced ``uploadArchives`` with new ``publishing`` method
* Updated ``writePom``

## v7.0.1 - 2 December 2022

* Added support for rate limiting errors

## v7.0.0 - 14 December 2021

* Upgrades to Createsend API v3.3 which includes new breaking changes
* New client tags() returns the list of tags 
* Client draftCampaigns() now return Tags
* Client scheduledCampaigns() now returns Tags
* Breaking: Revamped client sentCampaigns() includes these improvements: 
  * The method returns a PagedResult of SentCampaign.
  * Entries can be fetched using the Page parameter.
  * Up to 1000 entries are returned per method call. A different amount can be returned by setting the PageSize value.
  * Campaigns can be sorted by their Sent Date using OrderDirection
  * Campaigns can be filtered by Sent Date using sentFromDate and sentToDate in YYYY-MM-DD format
  * Campaigns can be filtered by their tags.
* Campaign summary() now returns Name
* Adding support for returning ListJoinedDate for each subscriber. 
  * List active()
  * List bounce()
  * List unsubscribe()
  * List unconfirmed()
  * List deleted()
  * Segment active()
  * Subscriber details()

## v6.1.1 - 21 February 2020

* Fix Security Vulnerability  

## v6.1.0 - 2 September 2019

* Inreased minor version to add support for [Journey API](https://www.campaignmonitor.com/api/journeys)

## v6.0.3 - 2 September 2019

* Added support for [Journey API](https://www.campaignmonitor.com/api/journeys)

## v6.0.2 - 31 January 2019

* Fixed JavaDoc generation

## v6.0.1 - 30 January 2019

* Changed API endpoint to use https by default

## v6.0.0 - 28 May 2018

* Upgrades to Createsend API v3.2 which includes new breaking changes
* Breaking: 'Consent to track' field is now mandatory for sending smart and classic transactional emails
* Breaking: 'Consent to track' field is now mandatory when adding or updating subscribers
* Optional 'Include tracking preference' field when retrieving lists of subscribers
* Fix 'Add recipient to list ID' field when sending classic transactional emails

## v5.1.3 - 14 Jun 2017

* Undo v5.1.2; instead configure JavaDoc generation to not fail on errors. Sonatype insists on JavaDoc :-P 

## v5.1.2 - 14 Jun 2017

* Disable JavaDoc generation for release, for now, as it's failing with Java 8.

## v5.1.1 - 13 Jun 2017

* Fix date format in `JsonProvider.ApiDateFormat`
* Fix NPE in `JerseyClientImpl`
* Fix incorrect List `deactivateWebhook` endpoint.

## v5.1.0 - 28 Jul 2015

* Transactional API

## v5.0.0 - 6 Feb, 2014

* Updated to v3.1 API
* Added support for new segments structure
  * Segment now includes a new `RuleGroup[] RuleGroups` member, instead of a `Rule[] Rules` member.

    ```java
   	public RuleGroup[] RuleGroups;
    ```

    So for example, when you _previously_ would have created a segment like so:

    ```java
	segment.Rules = new Rule[] {new Rule()};
    segment.Rules[0].Subject = "EmailAddress";
    segment.Rules[0].Clauses = new String[] {"CONTAINS example.com"};
    ```

    You would _now_ do this:

    ```java
    segment.RuleGroups = new RuleGroup[] {new RuleGroup()};
    segment.RuleGroups[0].Rules = new Rule[] {new Rule()};
    segment.RuleGroups[0].Rules[0].RuleType = "EmailAddress";
    segment.RuleGroups[0].Rules[0].Clause = "CONTAINS example.com";
    ```
    
  * The Add Rule call is now Add Rule Group, taking a `RuleGroup` in a `ruleGroup` argument instead of a rule argument.

    ```java
 	public void Segment.addRuleGroup(RuleGroup ruleGroup)
    ```

    So for example, when you _previously_ would have added a rule like so:

    ```java
	Rule rule = new Rule();
    rule.Subject = "EmailAddress";
    rule.Clauses = new String[] {"CONTAINS @hello.com"};
    segmentAPI.addRule(rule);
    ```

    You would _now_ do this:

    ```java
    RuleGroup extraRuleGroup = new RuleGroup();
    extraRuleGroup.Rules = new Rule[] {new Rule()};
    extraRuleGroup.Rules[0].RuleType = "EmailAddress";
    extraRuleGroup.Rules[0].Clause = "CONTAINS @hello.com";
    segmentAPI.addRuleGroup(extraRuleGroup);
    ```
* Removed the getAPIKey method to promote usage of oAuth authentication

## v4.0.1 - 16 Jan, 2014

* Removed jersey-apache-client reference due to vulnerability. See #20.

## v4.0.0 - 24 Oct, 2013

* [Campaign bounces should include Reason and BounceType](https://github.com/campaignmonitor/createsend-java/issues/18). This introduces a breaking change to the return type of ```com.createsend.Campaigns.bounces``` from ```com.createsend.models.PagedResult<com.createsend.models.subscribers.Subscriber>``` to ```com.createsend.models.PagedResult<com.createsend.models.subscribers.BouncedSubscriber>```.
  * If you're using this method, upgrading to this version will require you to update any code to use the new signature. For example

  ```
  PagedResult<Subscriber> bouncedSubscribers = campaigns.bounces();
  ```

  would need to be updated to

  ```
  PagedResult<BouncedSubscriber> bouncedSubscribers = campaigns.bounces();
  ```

## v3.1.3 - 21 June, 2013

* Improve error handling and determination of generic error handling.

## v3.1.2 - 5 June, 2013

* Correctly configure the date format used by our JsonProvider. See #16.

## v3.1.1 - 15 May, 2013

* Updated `com.sun.jersey` and `org.codehaus.jackson` dependencies to the latest versions.

## v3.1.0 - 16 Apr, 2013

* Added support for [single sign on](http://www.campaignmonitor.com/api/account/#single_sign_on) which allows initiation of external login sessions to Campaign Monitor.

## v3.0.0 - 25 Mar, 2013

* Added support for authenticating using OAuth. See the [README](README.md#authenticating) for full usage instructions.
* Refactored authentication so that it is _always_ done at the instance level. This introduces some breaking changes, which are clearly explained below.
  * Authentication using an API key is no longer supported using the `config.properties` or `createsend.properties` files.

      So if you _previously_ entered your API key into a properties file as follows:

      ```
      createsend.apikey = your api key
      ```

      If you want to authenticate with an API key, you should _now_ always authenticate at the instance level. For example, as follows:

      ```java
      import com.createsend.Clients;
      import com.createsend.models.campaigns.SentCampaign;
      import com.createsend.util.ApiKeyAuthenticationDetails;
      import com.createsend.util.exceptions.CreateSendException;

      public class Tester {
          public static void main(String[] args) throws CreateSendException {
              ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
                  "your api key");
              Clients clients = new Clients(auth, "your client id");
              SentCampaign[] campaigns = clients.sentCampaigns();
          }
      }
      ```

## v2.6.0 - 4 Jan, 2013

* Added support for instantiating JerseyClientImpl using a specific API key.

## v2.5.0 - 11 Dec, 2012

* Added support for including from name, from email, and reply to email in
drafts, scheduled, and sent campaigns.
* Added support for campaign text version urls.
* Added support for transferring credits to/from a client.
* Added support for getting account billing details as well as client credits.
* Made all date fields optional when getting paged results.

## v2.4.0 - 5 Nov, 2012

* Added EmailClient class and Campaigns.emailClientUsage().
* Added support for ReadsEmailWith field on subscriber objects.
* Added support for retrieving unconfirmed subscribers for a list.
* Added support for suppressing email addresses.
* Added support for retrieving spam complaints for a campaign, as well as adding SpamComplaints field to campaign summary output.
* Added VisibleInPreferenceCenter field to custom field output.
* Added support for setting preference center visibility when creating custom
fields.
* Added the ability to update a custom field name and preference visibility.
* Added documentation explaining that TextUrl may be provided as null or as an
empty string when creating a campaign.

## v2.3.0 - 10 Oct, 2012

* Added support for creating campaigns from templates.
* Added support for unsuppressing an email address.

## v2.2.0 - 17 Sep, 2012

* Added WorldviewURL field to com.createsend.models.campaigns.CampaignSummary.
* Introduced CampaignEventWithGeoData, CampaignOpen, and CampaignClick to
support correct retrieval of open and click data.

## v2.1.0 - 30 Aug, 2012

* Added support for basic / unlimited pricing.

## v2.0.0 - 23 Aug, 2012

* Removed com.createsend.Clients.setAccess() and removed all other parts of the
library which were allowing calls to the API in a deprecated manner.

## v1.2.0 - 22 Aug, 2012

* Added support for UnsubscribeSetting field when creating, updating and
getting list details.
* Added support for AddUnsubscribesToSuppList and ScrubActiveWithSuppList
fields when updating a list.
* Added com.createsend.Clients.listsForEmailAddress() to support finding all
client lists to which a subscriber with a specific email address belongs.

## v1.1.1 - 23 Jul, 2012

* Added support for specifying whether subscription-based autoresponders should
be restarted when adding or updating subscribers.

## v1.1.0 - 12 Jul, 2012

* Added support for team management.
* Added Travis CI support

## 1.0.5 - 31 Oct, 2011

* Added support for new APIs.
* Fixed field names to be consistent with fields returned by API.

## 1.0.4 - 11 Sep, 2011

* Fixed #3. Removed TemplateScreenshotURL from the sample for creating
a template.

## 1.0.3 - 17 Aug, 2011

* Initial release which supports current Campaign Monitor API.
