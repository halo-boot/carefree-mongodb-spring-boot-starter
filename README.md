# Carefree MongoDB

[English][1] | 中文

[![Build Status](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter.svg?branch=master)](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter)
[![GitHub release](https://img.shields.io/badge/version-v1.0.1-brightgreen.svg)](https://github.com/kweny/carefree-mongodb-spring-boot-starter)
![node](https://img.shields.io/badge/Java-%3E%3D8-brightgreen.svg)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Hex.pm](https://img.shields.io/badge/powered_by-Kweny-blue.svg)](http://kweny.io)

[Spring Data MongoDB][2] 为面向 MongoDB 的开发提供了一套基于 Spring 的编程模型，在 Spring Boot 中使用 spring-boot-starter-data-mongodb 可以很方便的引入 Spring Data MongoDB 以及 [MongoDB Java Driver][3]。

然而，Spring Data MongoDB 只提供了最简单的 MongoDB 客户端选项，且不支持多数据源配置。为了使用连接池、集群等 MongoDB 高级特性，及满足多数据源的需求，我们不得不进行一些额外的配置和编码工作。

Carefree MongoDB 由此而生，除了支持完整的 MongoDB 客户端选项及多数据源配置之外，还提供了一些其它的实用功能。使用后，Carefree MongoDB 将自动创建并注入 MongoTemplate 以及 GridFsTemplate 实例。

## 快速使用

可以使用 Gradle 或 Maven 快速引入 Carefree MongoDB。将同时引入 spring-data-mongodb 和 mongo-java-driver，因此无需再额外定义二者的引入。

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

在应用主类上添加 `@EnableMongoCarefree` 注解开启自动配置——

```java
@EnableMongoCarefree
@SpringBootApplication
public class Application {
   public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
   }
}
```

Carefree MongoDB 将自动加载配置文件 `application.properties` 或 `application.yml` 中以 `carefree.mongodb` 为前缀的属性。

属性名的前缀可自定义，如——

```
@EnableMongoCarefree("mongodb.custom.prefix")
```

同时支持占位符，用以引用定义好的属性值，如——

```
@EnableMongoCarefree("mongodb.${placeholder}.prefix")
@EnableMongoCarefree("${mongodb.placeholder.prefix}")
```

## 配置选项

Carefree MongoDB 支持完整的 MongoDB Java Driver 客户端选项，及多数据源配置，同时也提供了一些额外的配置项。

### 配置示例

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

#### 多数据源

进行多数据源配置时，需要明确指定各数据源的 MongoTemplate Bean 名称。如——

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

以上配置表示 3 个数据源，将创建 `mongoTemplate`、`masterTemplate`、`testTemplate` 三个 Bean。其中 `mongoTemplate` 为默认名称，不需要显示声明，当不指定名称时，将以此为名创建并注入。即以下两种配置等价——

```
carefree.mongodb.options.mongoTemplate.xxx
```
```
carefree.mongodb.options.xxx
```

### 配置说明

关于 MongoDB Java Driver 客户端选项的详细说明可以参考 [MongoDB 客户端连接选项][4] 一文。

*注：由于官方已对 socket-keep-alive 选项以及 MONGODB-CR 认证方式标注废弃，因此 Carefree MongoDB 也不予支持。*

以下将对一些由 Carefree MongoDB 特别处理的配置项进行说明——

* **carefree.mongodb.enable** - 用于指示是否开启 Carefree MongoDB 的自动配置。该选项设为 false 时将覆盖 `@EnableMongoCarefree` 注解并关闭自动配置。默认为 true。
* **uri** - MongoDB 的连接字符串，当配置了 `uri` 时，将忽略 `addresses`、`database`、`username` 等连接相关的配置项，而直接使用 `uri` 建立连接。
* **auth** - 服务端是否需要认证，默认为 false，如果服务端需要认证，请将该选项设为 true，否则即使配置了 `username`、`password` 等选项也会被忽略。
* **authentication-mechanism** - 服务端认证所采用的算法，可选值为 `PLAIN`、`GSSAPI`、`MONGODB-X509`、`SCRAM-SHA-1`、`SCRAM-SHA-256`。*注：由于官方已对 `MONGODB-CR` 认证方式标注废弃，因此 Carefree MongoDB 直接不予支持。*
* **server-selector** - `com.mongodb.selector.ServerSelector` 接口实现类的全名。
* **command-listeners** - `com.mongodb.event.CommandListener` 接口实现类的全名，可以指定多个。
* **cluster-listeners** - `com.mongodb.event.ClusterListener` 接口实现类的全名，可以指定多个。
* **connection-pool-listeners** - `com.mongodb.event.ConnectionPoolListener` 接口实现类的全名，可以指定多个。
* **server-listeners** - `com.mongodb.event.ServerListener` 接口实现类的全名，可以指定多个。
* **server-monitor-listeners** - `com.mongodb.event.ServerMonitorListener` 接口实现类的全名，可以指定多个。
* **write-concern** - 该选项接受的值形式如下——
    * `w1`、`w2`、`w3` ... - 其中的数字可根据实际情况指定。
    * `majority`、`journal` - 分别对应 `WriteConcern.MAJORITY` 和 `WriteConcern.JOURNALED` 两种模式。
    * `w2-10000-true`、`w2-10000-false` - 其中 `w2` 表示写入模式；`10000` 表示写入超时时间，即 wtimeout，单位为毫秒；`true/false` 表示是否需要 journalling。
* **read-concern** - 可选值为 `local`、`majority`、`linearizable`、`snapshot`。
* **read-preference** - 该选项接受的值形式如下——
    * `primary`、`primaryPreferred`、`secondary`、`secondaryPreferred`、`nearest` - 分别表示主节点、首选主节点、从节点、首选从节点以及最近节点 5 种模式。
    * `mode-tagSet-staleness` - 这种配置方式在 `非 primary` 模式下可以指定从哪些节点读取（tagSet）以及容忍的最大延迟（staleness），其中 tagSet 可以指定多个，staleness 单位为毫秒。如 `secondary-[{a=0,b=1},{c=3,d=4},{e=5}]-10000`、`secondary-[{a=0,b=1}]`、`secondary-10000`。
* **type-key** - Java 对象存储为 MongoDB 的 Document 时，会同时以一个名为 `_class` 的字段存储类名。该选项用于指定这个字段的名称，如果设为 false 将不存储这个字段；若为 true 则以默认的 `_class` 存储；其它值则以指定的值为名存储这个字段。
* **field-naming-strategy** - `org.springframework.data.mapping.model.FieldNamingStrategy` 接口实现类的全名。
* **grid-fs-template-name** - 指定该数据源 `GridFsTemplate` 的 Bean 名称。若不指定则不创建该数据源的 `GridFsTemplate`。默认的（名为 `mongoTemplate`）的数据源即使不指定该选项也会创建名为 `gridFsTemplate` 的 Bean。
* **grid-fs-database** - GridFS 数据库名称。默认使用 `database` 的值。
* **optioned-listeners** - `org.kweny.carefree.mongodb.MongoCarefreeOptionedListener` 接口实现类的全名，可以指定多个。这个监听器于配置选项被加载解析完成后触发，接受 `org.kweny.carefree.mongodb.MongoCarefreeStructure` 和 `com.mongodb.MongoClientOptions.Builder` 两个实例参数，可以在连接、工厂、template 等对象真正创建之前进行一些操作，如手动设置一些没有（无法）通过配置文件来指定的值等。
        


  [1]: https://github.com/kweny/carefree-mongodb-spring-boot-starter/blob/master/README_en.md
  [2]: https://projects.spring.io/spring-data-mongodb/
  [3]: https://mongodb.github.io/mongo-java-driver/
  [4]: http://kweny.io/mongodb-client-connection-options/