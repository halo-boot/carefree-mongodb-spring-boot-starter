/*
 * Copyright (C) 2018 Apenk.
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

import com.mongodb.MongoClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * 注册 {@link MongoTemplate} Bean。
 *
 * @author Kweny
 * @since 1.0.0
 */
class _BeanRegistrar {

    private ApplicationContext applicationContext;
    private BeanDefinitionRegistry registry;
    private ScopeMetadataResolver scopeMetadataResolver;

    _BeanRegistrar(ApplicationContext applicationContext, BeanDefinitionRegistry registry) {
        this.applicationContext = applicationContext;
        this.registry = registry;

        this.scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    }

    void doRegister(MongoClient mongoClient, MongoCarefreeStructure structure) {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, structure.getDatabase());

        GridFsMongoDatabaseFactory gfsFactory = null;

        // 如果指定了 gridFsTemplateName，则创建相应的 GridFsTemplate。
        // 对于默认的 MongoTemplate，始终创建对应的 GridFsTemplate，
        // 如果默认的 MongoTemplate 没有指定 gridFsTemplateName，则使用默认的 "gridFsTemplate"。
        if (_Assistant.DEFAULT_MONGO_TEMPLATE_NAME.equals(structure.getTemplateName())) {
            if (!StringUtils.hasText(structure.getGridFsTemplateName())) {
                structure.setGridFsTemplateName(_Assistant.DEFAULT_GRID_FS_TEMPLATE_NAME);
            }
        }
        if (StringUtils.hasText(structure.getGridFsTemplateName())) {
            gfsFactory = new GridFsMongoDatabaseFactory(factory, structure.getGridFsDatabase());
        }

        MongoCustomConversions conversions = createMongoCustomConversions();
        MongoMappingContext context = createMongoMappingContext(structure, conversions);
        MappingMongoConverter converter = createMappingMongoConverter(structure, factory, context, conversions);

        registerMongoTemplate(MongoTemplate.class, structure.getTemplateName(),
                                factory, converter, Boolean.TRUE.equals(structure.getPrimary()));

        if (gfsFactory != null) {
            registerMongoTemplate(GridFsTemplate.class, structure.getGridFsTemplateName(),
                                    gfsFactory, converter,  false);
        }
    }

    private void registerMongoTemplate(Class<?> clazz, String templateName,
                                       MongoDbFactory factory, MappingMongoConverter converter,
                                       boolean primary) {
        AnnotatedGenericBeanDefinition definition = new AnnotatedGenericBeanDefinition(clazz);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(definition);
        definition.setScope(scopeMetadata.getScopeName());
        definition.getConstructorArgumentValues().addGenericArgumentValue(factory);
        definition.getConstructorArgumentValues().addGenericArgumentValue(converter);
        definition.setPrimary(primary);
        AnnotationConfigUtils.processCommonDefinitionAnnotations(definition);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(definition, templateName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    private MappingMongoConverter createMappingMongoConverter(MongoCarefreeStructure structure,
                                                              MongoDbFactory factory,
                                                              MongoMappingContext context,
                                                              MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        if (StringUtils.hasText(structure.getTypeKey())) {
            // 指定文档 _class 字段的字段名——
            // true：使用默认的 "_class"；
            // false：不保存 _class 字段；
            // 其它：指定的字段名。
            if ("false".equalsIgnoreCase(structure.getTypeKey())) {
                mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
            } else if (!"true".equalsIgnoreCase(structure.getTypeKey())) {
                mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(structure.getTypeKey()));
            }
        }
        return mappingConverter;
    }

    private MongoMappingContext createMongoMappingContext(MongoCarefreeStructure structure, MongoCustomConversions conversions) {
        MongoMappingContext context = new MongoMappingContext();
        try {
            context.setInitialEntitySet(new EntityScanner(applicationContext).scan(Document.class, Persistent.class));
            if (StringUtils.hasText(structure.getFieldNamingStrategy())) {
                Class<?> strategyClass = Class.forName(structure.getFieldNamingStrategy());
                context.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    private MongoCustomConversions createMongoCustomConversions() {
        return new MongoCustomConversions(Collections.emptyList());
    }

}