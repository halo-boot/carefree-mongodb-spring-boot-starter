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

package org.kweny.carefree.mongodb.sbs;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * 从 Spring Boot 的环境配置中加载 MongoDB Client 的配置，
 * 根据配置创建并注册 {@link org.springframework.data.mongodb.core.MongoTemplate} Bean。
 *
 * @author Kweny
 * @since 1.0.0
 */
public class MongoCarefreeConfigurer implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private static final String DEFAULT_MONGO_TEMPLATE_NAME = "mongoTemplate";

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!isEnabled()) {
            return;
        }

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableMongoCarefree.class.getName()));

        if (attributes == null) {
            return;
        }

        String carefreePrefix = getEnvironment().resolvePlaceholders(attributes.getString("prefix"));

        /*
         * < mongo-template-bean-name, mongo-settings-property-name-prefix>
         * e.g. < "masterTemplate", "carefree.mongodb.settings.mastertemplate" >
         * Note: All letters in the property-name-prefix are lowercase.
         */
        Map<String, String> templateMap = getMongoTemplateNames(carefreePrefix.concat(".options"));

        Map<String, MongoCarefreeArchetype> archetypes = buildMongoOptionArchetypes(templateMap);

        archetypes.forEach((templateName, archetype) -> {
            MongoClientOptions clientOptions = _MongoClientOptionsUtil.buildMongoClientOptions(archetype);
        });
    }

    private Map<String, MongoCarefreeArchetype> buildMongoOptionArchetypes(Map<String, String> templateMap) {
        final Map<String, MongoCarefreeArchetype> archetypeMap = new HashMap<>();
        final Binder binder = Binder.get(getEnvironment());
        templateMap.forEach((templateName, prefix) -> {
            BindResult<MongoCarefreeArchetype> result = binder.bind(prefix, Bindable.of(MongoCarefreeArchetype.class));
            MongoCarefreeArchetype archetype = result.get();
            archetypeMap.put(templateName, archetype);
        });
        return archetypeMap;
    }

    private Map<String, String> getMongoTemplateNames(String optionsPrefix) {
        Map<String, String> templateMap = new HashMap<>();
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(getEnvironment());
        for (ConfigurationPropertySource source : sources) {
            if (source instanceof IterableConfigurationPropertySource) {
                IterableConfigurationPropertySource propertySource = (IterableConfigurationPropertySource) source;
                for (ConfigurationPropertyName propertyName : propertySource) {

                    // carefree.mongodb.settings.templateName.xxx
                    String name = getPropertyNameString(propertyName);

                    if (name.toLowerCase().startsWith(optionsPrefix.toLowerCase())) {
                        // templateName.xxx
                        String nameWithoutPrefix = name.substring(optionsPrefix.length() + 1);

                        // [templateName, xxx]
                        String[] nameElements = nameWithoutPrefix.split("\\.");

                        if (nameElements.length == 1) {
                            // < mongoTemplate, carefree.mongodb.options >
                            templateMap.put(DEFAULT_MONGO_TEMPLATE_NAME, optionsPrefix);
                        } else if (nameElements.length > 1) {
                            String templateName = nameElements[0];
                            // < templateName, carefree.mongodb.options.templatename >
                            templateMap.put(templateName, optionsPrefix.concat(".").concat(templateName.toLowerCase()));
                        }
                    }
                }
            }
        }
        return templateMap;
    }

    private String getPropertyNameString(ConfigurationPropertyName propertyName) {
        StringBuilder result = new StringBuilder();
        for (int elementIdx = 0; elementIdx < propertyName.getNumberOfElements(); elementIdx++) {
            String element = propertyName.getElement(elementIdx, ConfigurationPropertyName.Form.ORIGINAL);
            boolean isIndexed = propertyName.isNumericIndex(elementIdx);
            if (result.length() > 0 && !isIndexed) {
                result.append(".");
            }
            if (isIndexed) {
                result.append('[').append(element).append(']');
            } else {
                for (int j = 0; j < element.length(); j++) {
                    char ch = element.charAt(j);
                    result.append((ch != '_') ? ch : "");
                }
            }
        }
        return result.toString();
    }

    private boolean isEnabled() {
        if (getClass() == MongoCarefreeConfigurer.class) {
            return getEnvironment().getProperty(EnableMongoCarefree.ENABLED_OVERRIDE_PROPERTY, Boolean.class, true);
        }
        return true;
    }

    private Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}