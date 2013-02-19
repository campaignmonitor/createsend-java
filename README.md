# createsend-java [![Build Status](https://secure.travis-ci.org/campaignmonitor/createsend-java.png)][travis]

[travis]: http://travis-ci.org/campaignmonitor/createsend-java

A Java library which implements the complete functionality of the [Campaign Monitor API](http://www.campaignmonitor.com/api/).

## Authenticating

The Campaign Monitor API supports authentication using either OAuth or an API key.

### Using OAuth

The first thing your application should do is redirect your user to the Campaign Monitor authorization URL where they will have the opportunity to approve your application to access their Campaign Monitor account. You can get this authorization URL by using `com.createsend.General.getAuthorizeUrl()`, like so:

```java
import com.createsend.General;

String authorizeUrl = General.getAuthorizeUrl(
    32132,                 // The Client ID for your application
    "982u39823r928398",    // 'The Client Secret for your application
    "http://example.com/", // Redirect URI for your application
    "ViewReports",         // The permission level your application requires
    "some state data"      // Optional state data to be included
);
// Redirect your users to authorizeUrl.
```

TODO: Implement and document OAuth flow.

Once you have an access token and refresh token for your user, you can authenticate and make further API calls like so:

```java
import com.createsend.General;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;

public class Tester {
    public static void main(String[] args) throws CreateSendException {
        OAuthAuthenticationDetails auth = new OAuthAuthenticationDetails(
            "your access token", "your refresh token");
        General client = new General(auth);
        ClientBasics[] clients = client.getClients();
    }
}
```

TODO: Add instructions for refreshing an access token.

### Using an API key

```java
import com.createsend.General;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.ApiKeyAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;

public class Tester {
    public static void main(String[] args) throws CreateSendException {
        ApiKeyAuthenticationDetails auth = new ApiKeyAuthenticationDetails(
            "your api key");
        General client = new General(auth);
        ClientBasics[] clients = client.getClients();
    }
}
```

## Example

Here's a super simple example to get you started. This code just lists the clients in your account:

```java
import com.createsend.General;
import com.createsend.models.clients.ClientBasics;
import com.createsend.util.OAuthAuthenticationDetails;
import com.createsend.util.exceptions.CreateSendException;

public class Tester {
    public static void main(String[] args) throws CreateSendException {
        OAuthAuthenticationDetails auth = new OAuthAuthenticationDetails(
            "your access token", "your refresh token");
        General client = new General(auth);
        ClientBasics[] clients = client.getClients();
        for (ClientBasics c : clients) {
            System.out.printf("%s (ID: %s)\n", c.Name, c.ClientID);
        }
    }
}
```

See the [samples](https://github.com/campaignmonitor/createsend-java/blob/master/samples/com/createsend/samples/SampleRunner.java) directory for more example code.

## Documentation
Full javadoc for this library is available [here](http://campaignmonitor.github.com/createsend-java/doc/).

## Building

### Use Gradle:
Run the following command from the root directory of the repository:

```
gradle -i
```

### Developing with Eclipse:
Gradle can be used to create the `.classpath` and `.project` files to import the project into Eclipse. Run the following command from the root directory of the repository:

```
gradle eclipse
```

### Developing with IDEA
Gradle can be used to create an IDEA project and module files. Run the following command from the root directory of the repository:

```
gradle idea
```

## Contributing
1. Fork the repository
2. Make your changes.
3. Ensure that the build passes, by running `gradle -i` (CI runs on: `openjdk6`, `openjdk7`, and `oraclejdk7`)
4. It should go without saying, but do not increment the version number in your commits.
5. Submit a pull request.
