# Carefree MongoDB

[![Build Status](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter.svg?branch=master)](https://travis-ci.org/kweny/carefree-mongodb-spring-boot-starter)
[![GitHub release](https://img.shields.io/badge/version-v1.0.1-brightgreen.svg)](https://github.com/kweny/carefree-mongodb-spring-boot-starter)
![node](https://img.shields.io/badge/Java-%3E%3D8-brightgreen.svg)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Hex.pm](https://img.shields.io/badge/powered_by-Kweny-blue.svg)](http://kweny.io)

[Spring Data MongoDB][1] 为面向 MongoDB 的开发提供了一套基于 Spring 的编程模型，在 Spring Boot 中可以使用 spring-boot-starter-data-mongodb 很方便的引入 Spring Data MongoDB 以及 MongoDB Java Driver。

然而，Spring Data MongoDB 只提供了最简单的 MongoDB 客户端配置项，且不支持多数据源配置。为了使用连接池、集群等 MongoDB 高级特性，及满足多数据源的需求，我们不得不进行一些额外的配置和编码工作。

Carefree MongoDB 由此而生，除了支持完整的 MongoDB 客户端选项及多数据源配置之外，还提供了一些其它的实用功能。

## Based-On



  [1]: https://projects.spring.io/spring-data-mongodb/