# How To Microservice

Perhaps a misnomer, this is a personal reference for a small Java REST app that
more or less adheres to the principles of the [12 Factor
App](http://12factor.net/).


*Run as a systemd service*

    # build rpm
    gradlew clean build rpm
    # install in CentOS 7 vagrant machine for testing
    vagrant up

*Run as a docker container*

    # build fat jar
    gradlew clean build shadowJar
    # build docker image
    docker build -t how-to-microservice .
    # run docker container
    docker run how-to-microservice


## Jersey for REST

The app embraces Jersey and the JAX-RS API instead of the Servlet API. Avoiding
the servlet API makes the app more portable. Specifically, it allows the Jersey
Test Framework to use the `JdkHttpServerFactory` which initializes quicker than
a full servlet container


## Jetty for HTTP

The app espouses "container-less" deployments i.e. the build artifact is not a
WAR that must be deployed to a servlet container. Rather, the app embeds the
Jetty web server as a dependency. The main class `App` configures and starts the
Jetty web server.


## Jackson for JSON

Configures Jersey to use Jackson and a customized ObjectMapper for JSON
serialization. 


## Logback for Logging

Application logging is done via the SLF4J logging API with a Logback
implementation. Logback is configured to send all messages to STDOUT which makes
development easy to debug and allows production deployments to "treat logs as
event streams" as presribed by the 12 Factor App.


## HK2 for IOC

[HK2](https://hk2.java.net) is a light-weight IOC framework (JSR-330) which is
used internally and exposed by Jersey. While it is possible to use other IOC
frameworks with Jersey, HK2 does a fine job and doesn't require additional
dependencies


## Typesafe Config for Configuration

[Typesafe Config](https://github.com/typesafehub/config) is a comprehensive,
light-weight configuration library for JVM apps. It has good support for
overriding default configuration parameters with external config files or
environment variables. Its flexibility makes it appropriate for different types
of deployments such as external configuration files in
`/etc/how-to-microservice` or environment variables from a PaaS such as Heroku


## shadow for easier deployments

The gradle [shadow](https://github.com/johnrengelman/shadow) plugin enables
gradle to build one "fat jar" which contains all the app's dependencies. Having
one jar to deploy is much easier than a dozen. Use `java -jar
how-to-microservice-0.0.3-all.jar` to run the app


## nebula for RPM deployments

Netflix's [Nebula](http://nebula-plugins.github.io/) gradle plugins include a
plugin for building RPM distributions. Gradle is configured to build an RPM
which installs the app as a systemd daemon on Enterprise Linux 7. The RPM does
the following

* includes Java 8 as a dependency
* adds an unprivileged user to own the app's files and process
* installs a systemd unit file to register the app as a systemd daemon
* installs an external configuration file to
  `/etc/how-to-microservice/settings.conf`
* installs a script for setting envrionment variable `JVM_OPTS` for passing
  arguments such as `javax.net.ssl.keystore`

The project uses a Vagrant file to test the RPM build. After building an RPM
with `gradle clean build rpm`, one can use `vagrant up` to create a new CentOS 7
VirtualBox machine which installs the newly built RPM and runs the daemon.
VirtualBox forwards traffic from port 8001 to the machine's port 8000 for
testing the app e.g. `curl http://localhost:8001/greeting`.


## docker for container deployments

Included Dockerfile builds an image runs the fat jar in a docker container

