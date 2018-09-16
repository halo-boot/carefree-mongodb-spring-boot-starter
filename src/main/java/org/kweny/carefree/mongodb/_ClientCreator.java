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

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Kweny _ClientCreator
 *
 * @author Kweny
 * @since 1.0.0
 */
class _ClientCreator {

    static MongoClient createMongoClient(MongoClientOptions.Builder builder, MongoCarefreeStructure structure) {
        if (structure.getUri() != null && structure.getUri().trim().length() > 0) {
            MongoClientURI clientUri = new MongoClientURI(structure.getUri(), builder);
            return new MongoClient(clientUri);
        }

        List<ServerAddress> serverSeeds = new ArrayList<>();
        if (structure.getAddresses() != null) {
            for (String address : structure.getAddresses()) {
                String host = ServerAddress.defaultHost();
                int port = ServerAddress.defaultPort();
                if (address != null && address.trim().length() > 0) {
                    int portIdx = address.lastIndexOf(":");
                    if (portIdx == -1) {
                        host = address;
                    } else {
                        host = address.substring(0, portIdx);
                        port = Integer.parseInt(address.substring(portIdx + 1));
                    }
                }
                ServerAddress seed = new ServerAddress(host, port);
                serverSeeds.add(seed);
            }
        }

        MongoCredential credential = null;

        if (Boolean.TRUE.equals(structure.getAuth())) {
            String username = structure.getUsername();
            char[] passwordChars = structure.getPassword() != null ? structure.getPassword().toCharArray() : null;
            String mechanismString = structure.getAuthenticationMechanism();
            String source = structure.getAuthenticationSource() != null ? structure.getAuthenticationSource() : "admin";

            if (MongoCredential.GSSAPI_MECHANISM.equalsIgnoreCase(mechanismString)) {
                credential = MongoCredential.createGSSAPICredential(username);

            } else if (MongoCredential.PLAIN_MECHANISM.equalsIgnoreCase(mechanismString)) {
                credential = MongoCredential.createPlainCredential(username, source, passwordChars);

            } else if (MongoCredential.MONGODB_X509_MECHANISM.equalsIgnoreCase(mechanismString)) {
                credential = MongoCredential.createMongoX509Credential(username);

            } else if (MongoCredential.SCRAM_SHA_1_MECHANISM.equalsIgnoreCase(mechanismString)) {
                credential = MongoCredential.createScramSha1Credential(username, source, passwordChars);

            } else if (MongoCredential.SCRAM_SHA_256_MECHANISM.equalsIgnoreCase(mechanismString)) {
                credential = MongoCredential.createScramSha256Credential(username, source, passwordChars);

            } else {
                credential = MongoCredential.createCredential(username, source, passwordChars);
            }
        }

        MongoClientOptions options = builder.build();

        if (credential != null) {
            return new MongoClient(serverSeeds, credential, builder.build());
        } else {
            return new MongoClient(serverSeeds, builder.build());
        }
    }
}