# How To Microservice

Perhaps a misnomer, this is a personal reference for a small Java REST app that
more or less adheres to the principles of the [12 Factor
App](http://12factor.net/).

*Run*

    ./gradlew run

The application is packaged to be deployed in one of two ways:


*As a systemd service*

    # build rpm
    ./gradlew rpm
    # install in CentOS 7 vagrant machine for testing
    vagrant up

*As a docker container*

    # build docker image
    ./gradlew jibDockerBuilder
    # run docker container
    docker run --rm gilday/how-to-microservice


## Jersey for REST

The app embraces Jersey and the JAX-RS API instead of the Servlet API. Avoiding
the servlet API makes the app more portable. Specifically, it allows the Jersey
Test Framework to use the `JdkHttpServerFactory` which initializes quicker than
a full servlet container.


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
dependencies.


## Typesafe Config for Configuration

[Typesafe Config](https://github.com/typesafehub/config) is a comprehensive,
light-weight configuration library for JVM apps. It has good support for
overriding default configuration parameters with external config files or
environment variables. Its flexibility makes it appropriate for different types
of deployments such as external configuration files in
`/etc/how-to-microservice` or environment variables from a PaaS such as Heroku.


## Application plugin for easy deployments

The standard Gradle Application plugin generates distribution tar and zip files
which contain the application, its dependencies, and scripts for starting the
application. The easiest way to use the Application plugin to run the
application is `./gradlew run`. Use `./gradlew distZip` and `./gradlew distTar`
to build redistributable packages.

## nebula for RPM deployments

Netflix's [Nebula](http://nebula-plugins.github.io/) gradle plugins include a
plugin for building RPM distributions. Gradle is configured to build an RPM
which installs the app as a systemd daemon on Enterprise Linux 7. The RPM does
the following

* includes Java 11 as a dependency
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


## jib for container deployments

Project is configured to use [jib](https://github.com/GoogleContainerTools/jib)
to build a container image pushed to Docker Hub at
[gilday/how-to-microservice](https://cloud.docker.com/u/gilday/repository/docker/gilday/how-to-microservice).
To build a test the image with a local docker daemon, use the `jibDockerBuild`
task.
