/*
 * Copyright (C) 2018 Kweny.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kweny.carefree.mongodb;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 *     启用 MongoDB 自动配置的注解，添加到应用程序主类上——
 * </p>
 * <pre>
 *    {@code @EnableMongoCarefree}
 *    {@code @SpringBootApplication}
 *     public class Application {
 *         public static void main(String[] args) {
 *             SpringApplication.run(Application.class, args);
 *         }
 *     }
 * </pre>
 *
 * <p>
 *     可以指定属性名的前缀：
 *     {@code @EnableMongoCarefree("mongodb.custom.prefix")}。
 *     默认为 {@code carefree.mongodb}。
 * </p>
 *
 * <p>
 *     另外前缀中可以使用占位符：
 *     {@code @EnableMongoCarefree("mongodb.${placeholder}.prefix")}
 * </p>
 *
 * @author Kweny
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MongoCarefreeImportSelector.class})
public @interface EnableMongoCarefree {
    String prefix() default "carefree.mongodb";
}