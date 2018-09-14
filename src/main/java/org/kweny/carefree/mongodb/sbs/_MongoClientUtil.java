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

import com.mongodb.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Kweny _MongoClientUtil
 *
 * @author Kweny
 * @since 1.0.0
 */
class _MongoClientUtil {

    static MongoCarefreeArchetype buildMongoClient(MongoCarefreeArchetype archetype) {
        if (archetype.getUri() != null && archetype.getUri().trim().length() > 0) {
            MongoClientURI clientUri = new MongoClientURI(archetype.getUri(), archetype.getResolvedOptionsBuilder());
            archetype.setResolvedClient(new MongoClient(clientUri));
            return archetype;
        }

        List<ServerAddress> serverSeeds = new ArrayList<>();
        if (archetype.getAddresses() != null) {
            for (String address : archetype.getAddresses()) {
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

        if (Boolean.TRUE.equals(archetype.getAuth())) {
            String username = archetype.getUsername();
            char[] passwordChars = archetype.getPassword() != null ? archetype.getPassword().toCharArray() : null;
            String mechanismString = archetype.getAuthenticationMechanism();
            String source = archetype.getAuthenticationSource() != null ? archetype.getAuthenticationSource() : "admin";

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

        if (credential != null) {
            archetype.setResolvedClient(new MongoClient(serverSeeds, credential, archetype.getResolvedOptions()));
        } else {
            archetype.setResolvedClient(new MongoClient(serverSeeds, archetype.getResolvedOptions()));
        }

        return archetype;
    }
}