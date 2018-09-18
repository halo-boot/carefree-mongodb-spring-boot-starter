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

import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.util.StringUtils;

/**
 * 针对 GridFS 客户端连接工厂的包装。
 *
 * @author Kweny
 * @since 1.0.0
 */
public class GridFsMongoDatabaseFactory implements MongoDbFactory {

    private final MongoDbFactory mongoDbFactory;
    private final String gridFsDatabase;

    GridFsMongoDatabaseFactory(MongoDbFactory mongoDbFactory, String gridFsDatabase) {
        this.mongoDbFactory = mongoDbFactory;
        this.gridFsDatabase = gridFsDatabase;
    }

    @Override
    public MongoDatabase getDb() throws DataAccessException {
        if (StringUtils.hasText(gridFsDatabase)) {
            return this.mongoDbFactory.getDb(gridFsDatabase);
        }
        return this.mongoDbFactory.getDb();
    }

    @Override
    public MongoDatabase getDb(String dbName) throws DataAccessException {
        return this.mongoDbFactory.getDb(dbName);
    }

    @Override
    public PersistenceExceptionTranslator getExceptionTranslator() {
        return this.mongoDbFactory.getExceptionTranslator();
    }

    @Override
    public DB getLegacyDb() {
        return this.mongoDbFactory.getLegacyDb();
    }
}