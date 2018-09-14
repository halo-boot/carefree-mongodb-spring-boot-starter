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

package org.kweny.carefree.mongodb.sbs;

import com.mongodb.MongoClient;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.util.StringUtils;

/**
 * TODO Kweny _MongoTemplateUtil
 *
 * @author Kweny
 * @since TODO version
 */
class _MongoTemplateUtil {

    static MongoDbFactory buildMongoDatabaseFactory(MongoCarefreeArchetype archetype) {
        MongoDbFactory factory = new SimpleMongoDbFactory(archetype.getResolvedClient(), archetype.getDatabase());

        GridFsMongoDatabaseFactory gfsFactory = null;

        // 如果指定了 gridFsTemplateName，则创建相应的 GridFsTemplate。
        // 对于默认的 MongoTemplate，始终创建对应的 GridFsTemplate，
        // 如果默认的 MongoTemplate 没有指定 gridFsTemplateName，则使用默认的 "gridFsTemplate"。
        if (_InternalUtil.DEFAULT_MONGO_TEMPLATE_NAME.equals(archetype.getTemplateName())) {
            if (!StringUtils.hasText(archetype.getGridFsTemplateName())) {
                archetype.setGridFsTemplateName(_InternalUtil.DEFAULT_GRID_FS_TEMPLATE_NAME);
            }
        }
        if (StringUtils.hasText(archetype.getGridFsTemplateName())) {
            gfsFactory = new GridFsMongoDatabaseFactory(factory, archetype.getGridFsDatabase());
        }

        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver)
    }

    private static MappingMongoConverter buildConverter() {

    }
}