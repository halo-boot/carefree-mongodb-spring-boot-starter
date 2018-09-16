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

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.core.env.Environment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * TODO Kweny _StructureResolver
 *
 * @author Kweny
 * @since 1.0.0
 */
class _StructureResolver {

    static List<MongoCarefreeStructure> resolveStructures(Environment environment, String carefreePrefix) {
        String optionsPrefix = carefreePrefix.concat(".options");
        Map<String, String> templateMap = getMongoTemplateNames(environment, optionsPrefix);
        return buildStructures(environment, templateMap);
    }

    private static List<MongoCarefreeStructure> buildStructures(Environment environment, Map<String, String> templateMap) {
        final List<MongoCarefreeStructure> structures = new LinkedList<>();
        final Binder binder = Binder.get(environment);

        // 若没有显示声明 primary，则取第一个作为 primary 数据源。
        boolean primaryDefined = false;
        int index = 0;
        MongoCarefreeStructure firstStructure = null;
        for (Map.Entry<String, String> entry : templateMap.entrySet()) {
            String templateName = entry.getKey();
            String prefix = entry.getValue();

            BindResult<MongoCarefreeStructure> result = binder.bind(prefix, Bindable.of(MongoCarefreeStructure.class));
            MongoCarefreeStructure structure = result.get();

            structure.setTemplateName(templateName);
            structures.add(structure);

            primaryDefined |= Boolean.TRUE.equals(structure.getPrimary());

            if (index == 0) {
                firstStructure = structure;
                index++;
            }
        }

        if (!primaryDefined && firstStructure != null) {
            firstStructure.setPrimary(true);
        }

        return structures;
    }

    private static Map<String, String> getMongoTemplateNames(Environment environment, String optionsPrefix) {
        Map<String, String> templateMap = new TreeMap<>();
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
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
                            templateMap.put(_Assistant.DEFAULT_MONGO_TEMPLATE_NAME, optionsPrefix);
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

    private static String getPropertyNameString(ConfigurationPropertyName propertyName) {
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

}