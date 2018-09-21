# Carefree MongoDB

English | [中文][1]

[![Build Status](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter.svg?branch=master)](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter)
[![GitHub release](https://img.shields.io/badge/version-v1.0.1-brightgreen.svg)](https://github.com/kweny/carefree-mongodb-spring-boot-starter)
![node](https://img.shields.io/badge/Java-%3E%3D8-brightgreen.svg)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Hex.pm](https://img.shields.io/badge/powered_by-Kweny-blue.svg)](http://kweny.io)

[Spring Data MongoDB][2] provides a Spring-based programming model for MongoDB development. Spring Data MongoDB and [MongoDB Java Driver][3] can be easily introduced using spring-boot-starter-data-mongodb in Spring Boot.

However, Spring Data MongoDB only provides the simplest MongoDB client options and not support multiple data source configruations. In order to use MongoDB advanced features such as connection pooling, clustering, etc., and to meet the needs of multiple data sources, we had to do some additional configuration and coding work.

Carefree MongoDB comes for this, in addition to supporting the full MongoDB client options and Multi-data source configuration, there are some other useful features. Once used, Carefree MongoDB will automatically create and inject MongoTemplate and GridFsTemplate instances.

## Getting started

You can quickly introduce Carefree MongoDB using Gradle or Maven. Both spring-data-mongodb and mongo-java-driver will be introduced, so there is no need to define the introduction of both.

### Gradle

```groovy
compile 'org.kweny.carefree:carefree-mongodb-spring-boot-starter:1.0.1'
```

### Maven

```xml
<dependency>
  <groupId>org.kweny.carefree</groupId>
  <artifactId>carefree-mongodb-spring-boot-starter</artifactId>
  <version>1.0.1</version>
</dependency>
```

### @EnableMongoCarefree

Add the `@EnableMongoCarefree` annotation on the application main class to enable automatic configuration and disable Spring Boot's default MongoDB auto-configuration:

```java
@EnableMongoCarefree
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class Application {
   public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
   }
}
```

Carefree MongoDB will automatically load the properties of the configuration file `application.properties` or `application.yml` prefixed with `carefree.mongodb`.

The prefix of the property name can be customized, such as:

```
@EnableMongoCarefree("mongodb.custom.prefix")
```

It also supports placeholders to reference defined property values, such as:

```
@EnableMongoCarefree("mongodb.${placeholder}.prefix")
@EnableMongoCarefree("${mongodb.placeholder.prefix}")
```

## Configuration options

Carefree MongoDB supports the full MongoDB Java Driver client options and multiple data source configurations, as well as some additional configuration options.

### Configuration example

#### application.yml
```yml
carefree:
  mongodb:
    enable: true
    options:
      primary: true
      uri: mongodb://[username:password@]host1[:port1][,host2[:port2],…[,hostN[:portN]]][/[database][?options]]
      addresses:
      - 192.168.1.1:27017
      - 192.168.1.2:27017
      database: test_db
      auth: true
      username: test_user
      password: test_pwd
      authentication-mechanism: SCRAM-SHA-1
      authentication-source: admin
      description: some description
      application-name: test app
      connect-timeout: 10000
      socket-timeout: 0
      max-wait-time: 120000
      min-connections-per-host: 0
      max-connections-per-host: 100
      max-connection-idle-time: 0
      max-connection-life-time: 0
      threads-allowed-to-block-for-connection-multiplier: 5
      heartbeat-frequency: 10000
      min-heartbeat-frequency: 500
      heartbeat-connect-timeout: 20000
      heartbeat-socket-timeout: 20000
      retry-writes: false
      always-use-m-beans: false
      ssl-enabled: false
      ssl-invalid-host-name-allowed: false
      local-threshold: 15
      server-selection-timeout: 30000
      server-selector: com.xxx.CustomServerSelector
      required-replica-set-name: replica_name
      write-concern: w1
      read-concern: local
      read-preference: primary
      cursor-finalizer-enabled: true
      command-listeners:
      - com.xxx.CustomCommandListener
      cluster-listeners:
      - com.xxx.CustomClusterListener
      connection-pool-listeners:
      - com.xxx.CustomConnectionPoolListener
      server-listeners:
      - com.xxx.CustomServerListener
      server-monitor-listeners:
      - com.xxx.CustomServerMonitorListener
      type-key: _class
      grid-fs-template-name: gridFsTemplate
      grid-fs-database: test_db
      field-naming-strategy: com.xxx.CustomFieldNamingStrategy
      optioned-listeners:
      - com.xxx.CustomOptionedListener
```

#### application.properties
```
carefree.mongodb.enable=true
carefree.mongodb.options.uri=mongodb://[username:password@]host1[:port1][,host2[:port2],…[,hostN[:portN]]][/[database][?options]]
carefree.mongodb.options.primary=true
carefree.mongodb.options.addresses[0]=192.168.1.1:27017
carefree.mongodb.options.addresses[1]=192.168.1.2:27017
carefree.mongodb.options.database=test_db
carefree.mongodb.options.auth=true
carefree.mongodb.options.username=test_user
carefree.mongodb.options.password=test_pwd
carefree.mongodb.options.authentication-mechanism=SCRAM-SHA-1
carefree.mongodb.options.authentication-source=admin
carefree.mongodb.options.description=some description
carefree.mongodb.options.application-name=test app
carefree.mongodb.options.connect-timeout=10000
carefree.mongodb.options.socket-timeout=0
carefree.mongodb.options.max-wait-time=120000
carefree.mongodb.options.min-connections-per-host=0
carefree.mongodb.options.max-connections-per-host=100
carefree.mongodb.options.max-connection-idle-time=0
carefree.mongodb.options.max-connection-life-time=0
carefree.mongodb.options.threads-allowed-to-block-for-connection-multiplier=5
carefree.mongodb.options.heartbeat-frequency=10000
carefree.mongodb.options.min-heartbeat-frequency=500
carefree.mongodb.options.heartbeat-connect-timeout=20000
carefree.mongodb.options.heartbeat-socket-timeout=20000
carefree.mongodb.options.retry-writes=false
carefree.mongodb.options.always-use-m-beans=false
carefree.mongodb.options.ssl-enabled=false
carefree.mongodb.options.ssl-invalid-host-name-allowed=false
carefree.mongodb.options.local-threshold=15
carefree.mongodb.options.server-selection-timeout=30000
carefree.mongodb.options.server-selector=com.xxx.CustomServerSelector
carefree.mongodb.options.required-replica-set-name=replica_name
carefree.mongodb.options.write-concern=w1
carefree.mongodb.options.read-concern=local
carefree.mongodb.options.read-preference=primary
carefree.mongodb.options.cursor-finalizer-enabled=true
carefree.mongodb.options.command-listeners[0]=com.xxx.CustomCommandListener
carefree.mongodb.options.cluster-listeners[0]=com.xxx.CustomClusterListener
carefree.mongodb.options.connection-pool-listeners[0]=com.xxx.CustomConnectionPoolListener
carefree.mongodb.options.server-listeners[0]=com.xxx.CustomServerListener
carefree.mongodb.options.server-monitor-listeners[0]=com.xxx.CustomServerMonitorListener
carefree.mongodb.options.type-key=_class
carefree.mongodb.options.grid-fs-template-name=gridFsTemplate
carefree.mongodb.options.grid-fs-database=test_db
carefree.mongodb.options.field-naming-strategy=com.xxx.CustomFieldNamingStrategy
carefree.mongodb.options.optioned-listeners[0]=com.xxx.CustomOptionedListener
```

#### Multiple data source

When configuring multiple data sources, you needo to explicitly specify the MongoTemplate Bean name for each data source, such as:

```yml
carefree:
  mongodb:
    enable: true
    options:
      primary: true
      uri: xxx
      
      masterTemplate:
        uri: xxx
        
      testTemplate:
        uri: yyy
```

The above configuration defines 3 data sources, and 3 beans: `mongoTemplate`、`masterTemplate`、`testTemplate`, will be created. Where `mongoTemplate` is the default name and no need to specify explicitly, if no name is specified, it will be created and injected with this name. That is, the following two configurations are equivalent:

```
carefree.mongodb.options.mongoTemplate.xxx
```
```
carefree.mongodb.options.xxx
```

### Configuration instructions

A detailed description of the MongoDB Java Driver client options can be found in the article [MongoDB 客户端连接选项][4].

*Note: Since the socket-keep-alive option and the MONGODB-CR authentication method have been deprecated, Carefree MongoDB does not support them.*

Hare are some options that are specifically handled by Carefree MongoDB:

* **carefree.mongodb.enable** - Used to indicate whether to enable auto-configuration of Carefree MongoDB. Setting this option to false overrides the `@EnableMongoCarefree` annotation and turns off automatic configuration. The default is true.
* **uri** - The connection string for MongoDB. When `uri` is configured, the connection-related options such as addresses`, `database`, `username` will be ignored, and use `uri` to create connections.
* **auth** - Whether the server needs authentication, the default is false. If the server needs authentication, set this option to true. Otherwise, even if the options `username`, `password` are configured, it will be ignored.
* **authentication-mechanism** - The algorithm used for server authentication. The optional values are `PLAIN`, `GSSAPI`, `MONGODB-X509`, `SCRAM-SHA-1`, `SCRAM-SHA-256`. *Note: Since the MONGODB-CR authentication method has been deprecated, Carefree MongoDB does not support it.*
* **server-selector** - The full name of the `com.mongodb.selector.ServerSelector` interface implementation class.
* **command-listeners** - The full name of the `com.mongodb.event.CommandListener` interface implementation class. it can be multiple.
* **cluster-listeners** - The full name of the `com.mongodb.event.ClusterListener` interface implementation class. it can be multiple.
* **connection-pool-listeners** - The full name of the `com.mongodb.event.ConnectionPoolListener` interface implementation class. it can be multiple.
* **server-listeners** - The full name of the `com.mongodb.event.ServerListener` interface implementation class. it can be multiple.
* **server-monitor-listeners** - The full name of the `com.mongodb.event.ServerMonitorListener` interface implementation class. it can be multiple.
* **write-concern** - The value accepted by this option is as follows:
    * `w1`、`w2`、`w3` ... - The numbers can be specified according to the actual situation.
    * `majority`、`journal` - Corresponds to `WriteConcern.MAJORITY` and `WriteConcern.JOURNALED` respectively.
    * `w2-10000-true`、`w2-10000-false` - Where `w2` indicates write mode; `10000` indicates write timeout, ie wtimeout, in milliseconds; `true/false` indicates whether journalling is required.
* **read-concern** - The optional values are `local`, `majority`, `linearizable`, `snapshot`.
* **read-preference** - The value accepted by this option is as follows:
    * `primary`、`primaryPreferred`、`secondary`、`secondaryPreferred`、`nearest` - Corresponds to 5 modes: primary node, preferred primary node, secondary node, preferred secondary node, and nearest node.
    * `mode-tagSet-staleness` - in `non-primary` mode, this configuration can specify which nodes to read from (tagSet) and the maximum latency (staleness) . Where tagSet can be multiple, and staleness is in milliseconds. Such as `secondary-[{a=0,b=1},{c=3,d=4},{e=5}]-10000`,`secondary-[{a=0,b=1}]` , `secondary-10000`.
* **type-key** - When a Java object is stored as a Document for MongoDB, the class name is stored in a field named `_class`. This option is used to specify the name of this field. If set it to false, this field will not be stored; if true, it will be stored with the default `_class`; other values, the value will be used as the field name.
* **field-naming-strategy** - The full name of the `org.springframework.data.mapping.model.FieldNamingStrategy` interface implementation class.
* **grid-fs-template-name** - Specifies the `GridFsTemplate` bean name of the data source. If not specified it, the `GridFsTemplate` of the data source will not be created. The default (named `mongoTemplate`) data source creates a bean named `gridFsTemplate` even if this option is not specified.
* **grid-fs-database** - The GridFS database name. The value of `database` is used by default.
* **optioned-listeners** - The full name of the `org.kweny.carefree.mongodb.MongoCarefreeOptionedListener` interface implementation class. it can be multiple. This listener fires after the configuration options have been loaded and resolved. Accepts two instance parameters: `org.kweny.carefree.mongodb.MongoCarefreeStructure` and `com.mongodb.MongoClientOptions.Builder`. This listener can do something before the instances of connections, factories, templates, etc., are created. For example, manually set some values that are not (or cannot) be specified by the configuration file.
        


  [1]: https://github.com/kweny/carefree-mongodb-spring-boot-starter/blob/master/README.md
  [2]: https://projects.spring.io/spring-data-mongodb/
  [3]: https://mongodb.github.io/mongo-java-driver/
  [4]: http://kweny.io/mongodb-client-connection-options/