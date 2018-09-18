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

import com.mongodb.MongoClientOptions;

/**
 * 配置加载监听器。当 {@link MongoClientOptions.Builder} 实例创建完成后触发。
 *
 * @author Kweny
 * @since 1.0.0
 */
public interface MongoCarefreeOptionedListener {

    void optioned(MongoCarefreeStructure structure, MongoClientOptions.Builder builder);

}