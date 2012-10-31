# createsend-java [![Build Status](https://secure.travis-ci.org/campaignmonitor/createsend-java.png)][travis]

[travis]: http://travis-ci.org/campaignmonitor/createsend-java

A Java library which implements the complete functionality of the [Campaign Monitor API](http://www.campaignmonitor.com/api/).

## Downloads
2 pre built jars are available, one with just the wrapper classes, the other with all dependencies.

## Javadoc
Full javadoc for this library is available [here](http://campaignmonitor.github.com/createsend-java/docs/).

## Building

###With Gradle:
Run the following command from the root directory of the repository:

    gradle -i

###Developing with Eclipse:
Gradle can be used to create the .classpath and .project files to import the project into Eclipse. Run the following command from the root directory of the repository:

    gradle eclipse
        
###Developing with IDEA
Gradle can be used to create an IDEA project and module files. Run the following command from the root directory of the repository:

    gradle idea

## Contributing
1. Fork the repository
2. Make your changes.
3. Ensure that the build passes, by running `gradle -i` (CI runs on: `openjdk6`, `openjdk7`, and `oraclejdk7`)
4. It should go without saying, but do not increment the version number in your commits.
5. Submit a pull request.