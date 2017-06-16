# Releasing createsend-java

## Requirements

Before you begin, you must have:
* Apache Maven 3.0.4 or greater installed
* Access to push to the [createsend-java repository on GitHub][]
* A gpg key which is registered on a public key server (see [How To Generate PGP Signatures With Maven][])
* Sonatype OSS account with publish rights for the `com.createsend` group (see [Sonatype OSS Maven Repository Usage Guide][])
* `~/.m2/settings.xml` file that includes the Sonatype repositories, your Sonatype credentials, and your gpg keyname and passphrase:

```xml
<settings>
  <servers>
    <server>
      <id>sonatype-nexus-snapshots</id>
      <username>username</username>
      <password>password</password>
    </server>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>username</username>
      <password>password</password>
    </server>
  </servers>
  <profiles>
    <profile>
      <id>gpg</id>
      <properties>
        <gpg.keyname>yourkeyname</gpg.keyname>
        <gpg.passphrase>yourpassphrase</gpg.passphrase>
      </properties>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>gpg</activeProfile>
  </activeProfiles>
</settings>
```

## Releasing developer snapshot versions

You can release developer SNAPSHOT versions of the package at any time for developer testing. These are released by running:

```sh
$ gradle uploadArchive
```

Developer snapshot versions can be found in the [snapshot repo][].

## Releasing production versions

### Prepare release

* Increment version constants in the following files, ensuring that you use [Semantic Versioning][]:

  - `build.gradle`
  - `pom.xml` (as the SNAPSHOT version - change might not be required)
  - `src/com/createsend/util/config.properties`

* Add an entry to `HISTORY.md` which clearly explains the new release.
* Commit your changes:

```sh
$ git commit -am "Version X.Y.Z"
```

### Releasing to Sonatype OSS staging repository

Ensure that the version specified in `pom.xml` includes the `-SNAPSHOT` suffix. It should take the form: `<version>X.Y.Z-SNAPSHOT</version>`

Then _prepare_ the release. This will tag the repository and increment the version number in `pom.xml` for the next development iteration. When you are asked for the tag to apply to the release, use a tag of the form: `vX.Y.Z`:

```sh
$ mvn -Dresume=false release:prepare
```

Then _perform_ the release. This will build and sign all artifacts and upload them to the staging repository:

```sh
$ mvn release:perform
```

### Promoting the release from staging

In order to promote the release from the staging repository, log in to [Sonatype OSS][], and from the _Build Promotion_ tab on the left hand site select _Staging Repositories_.

The release you just uploaded should show up in the list. Select the release and click _Close_. This will check if the deployment is complete and properly signed, then create a staging repository which can be used for testing. Once the closing process is successful, click _Release_ to actually release it to the [release repo][]. The release repo is synced with [Maven Central][].

### Generate and publish javadoc

Generate and publish the javadoc for the new release:

```sh
$ ./update-javadoc.sh
```

Documentation is published to: http://campaignmonitor.github.io/createsend-java/doc/

[createsend-java repository on GitHub]: https://github.com/campaignmonitor/createsend-java
[Sonatype OSS Maven Repository Usage Guide]: http://central.sonatype.org/pages/ossrh-guide.html
[Sonatype OSS]: https://oss.sonatype.org/
[How To Generate PGP Signatures With Maven]: http://blog.sonatype.com/2010/01/how-to-generate-pgp-signatures-with-maven/
[release repo]: https://oss.sonatype.org/content/repositories/releases/com/createsend/createsend-java/
[snapshot repo]: https://oss.sonatype.org/content/repositories/snapshots/com/createsend/createsend-java/
[Maven Central]: http://repo1.maven.org/maven2/
[Semantic Versioning]: http://semver.org/