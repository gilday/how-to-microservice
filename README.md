# How To Microservice

A personal reference for a Jersey + Jetty + Jackson set-up

## Goals

In general, this project espouses many of the same ideals as a [12 Factor App](http://12factor.net/). In some way, it's like my take on an even more light-weight [Dropwizard](http://dropwizard.io/)

* **container-less deployments:** use Jetty to achieve container-less model. Unlike WARs, there's not just one way to deploy a container-less app. This app assumes it will be deployed to a linux system where it will run as a service. To this end, the gradle build will create an RPM which includes a service wrapper script.
* **config:** Out of the box, the app is configured for a development environment. All config values may be overridden by an external config file. By default, the app will look for the config in some well known location, but it will allow the path to this location to be overridden by an environment variable or system property
* **avoid servlet:** embracing Jersey and keeping the Servlet API out of Jersey classes makes the app more portable. Specifically, it allows the Jersey Test Framework to use the JdkHttpServerFactory which initializes far quicker than a servlet container