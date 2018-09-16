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

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * TODO Kweny MongoCarefreeImportSelector
 *
 * @author Kweny
 * @since 1.0.0
 */
public class MongoCarefreeImportSelector implements DeferredImportSelector, EnvironmentAware, Ordered {

    private static final String[] NO_IMPORTS = {};

    private Environment environment;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        if (!isEnabled()) {
            return NO_IMPORTS;
        }

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableMongoCarefree.class.getName()));

        if (attributes == null) {
            return NO_IMPORTS;
        }

        _Assistant.CAREFREE_PREFIX = getEnvironment().resolvePlaceholders(attributes.getString("prefix"));

        return new String[] {MongoCarefreeConfigurer.class.getName()};
    }

    private boolean isEnabled() {
        if (getClass() == MongoCarefreeImportSelector.class) {
            return getEnvironment().getProperty(EnableMongoCarefree.ENABLED_OVERRIDE_PROPERTY, Boolean.class, true);
        }
        return true;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE - 1;
    }

}