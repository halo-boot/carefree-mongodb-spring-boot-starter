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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Carefree MongoDB 配置的描述对象，用于创建 {@link com.mongodb.MongoClientOptions}。
 *
 * @author Kweny
 * @since 1.0.0
 */
@ConfigurationProperties
public class MongoCarefreeArchetype {

    private Boolean primary;

    private String uri;

    private String[] addresses;
    private String database;

    private Boolean auth;
    private String username;
    private String password;
    private String authenticationMechanism;
    private String authenticationSource;

    private String description;
    private String applicationName;

    private Integer connectTimeout;
    private Integer socketTimeout;

    private Integer maxWaitTime;
    private Integer minConnectionsPerHost;
    private Integer maxConnectionsPerHost;
    private Integer maxConnectionIdleTime;
    private Integer maxConnectionLifeTime;
    private Integer threadsAllowedToBlockForConnectionMultiplier;

    private Integer heartbeatFrequency;
    private Integer minHeartbeatFrequency;
    private Integer heartbeatConnectTimeout;
    private Integer heartbeatSocketTimeout;

    private Boolean retryWrites;
    private Boolean alwaysUseMBeans;

    private Boolean sslEnabled;
    private Boolean sslInvalidHostNameAllowed;

    private Integer localThreshold;
    private Integer serverSelectionTimeout;
    private String serverSelector;

    private String requiredReplicaSetName;

    private String writeConcern;
    private String readConcern;
    private String readPreference;

    private Boolean cursorFinalizerEnabled;

    private List<String> commandListeners;
    private List<String> clusterListeners;
    private List<String> connectionPoolListeners;
    private List<String> serverListeners;
    private List<String> serverMonitorListeners;

    private String typeKey;
    private String templateName;
    private String gridFsTemplateName;
    private String gridFsDatabase;

    private List<String> optionedListeners;

    private MongoClientOptions resolvedOptions;
    private MongoClientOptions.Builder resolvedOptionsBuilder;
    private MongoClient resolvedClient;

    MongoClientOptions getResolvedOptions() {
        return resolvedOptions;
    }

    void setResolvedOptions(MongoClientOptions resolvedOptions) {
        this.resolvedOptions = resolvedOptions;
    }

    MongoClientOptions.Builder getResolvedOptionsBuilder() {
        return resolvedOptionsBuilder;
    }

    void setResolvedOptionsBuilder(MongoClientOptions.Builder resolvedOptionsBuilder) {
        this.resolvedOptionsBuilder = resolvedOptionsBuilder;
    }

    MongoClient getResolvedClient() {
        return resolvedClient;
    }

    void setResolvedClient(MongoClient resolvedClient) {
        this.resolvedClient = resolvedClient;
    }

    // ----- getter/setter -----
    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticationMechanism() {
        return authenticationMechanism;
    }

    public void setAuthenticationMechanism(String authenticationMechanism) {
        this.authenticationMechanism = authenticationMechanism;
    }

    public String getAuthenticationSource() {
        return authenticationSource;
    }

    public void setAuthenticationSource(String authenticationSource) {
        this.authenticationSource = authenticationSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public Integer getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public Integer getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public Integer getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(Integer threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public Integer getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(Integer heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public Integer getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(Integer minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public Integer getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(Integer heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public Integer getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(Integer heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public Boolean getRetryWrites() {
        return retryWrites;
    }

    public void setRetryWrites(Boolean retryWrites) {
        this.retryWrites = retryWrites;
    }

    public Boolean getAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(Boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(Boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public Boolean getSslInvalidHostNameAllowed() {
        return sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public Integer getLocalThreshold() {
        return localThreshold;
    }

    public void setLocalThreshold(Integer localThreshold) {
        this.localThreshold = localThreshold;
    }

    public Integer getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(Integer serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public String getServerSelector() {
        return serverSelector;
    }

    public void setServerSelector(String serverSelector) {
        this.serverSelector = serverSelector;
    }

    public String getRequiredReplicaSetName() {
        return requiredReplicaSetName;
    }

    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }

    public String getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(String writeConcern) {
        this.writeConcern = writeConcern;
    }

    public String getReadConcern() {
        return readConcern;
    }

    public void setReadConcern(String readConcern) {
        this.readConcern = readConcern;
    }

    public String getReadPreference() {
        return readPreference;
    }

    public void setReadPreference(String readPreference) {
        this.readPreference = readPreference;
    }

    public Boolean getCursorFinalizerEnabled() {
        return cursorFinalizerEnabled;
    }

    public void setCursorFinalizerEnabled(Boolean cursorFinalizerEnabled) {
        this.cursorFinalizerEnabled = cursorFinalizerEnabled;
    }

    public List<String> getCommandListeners() {
        return commandListeners;
    }

    public void setCommandListeners(List<String> commandListeners) {
        this.commandListeners = commandListeners;
    }

    public List<String> getClusterListeners() {
        return clusterListeners;
    }

    public void setClusterListeners(List<String> clusterListeners) {
        this.clusterListeners = clusterListeners;
    }

    public List<String> getConnectionPoolListeners() {
        return connectionPoolListeners;
    }

    public void setConnectionPoolListeners(List<String> connectionPoolListeners) {
        this.connectionPoolListeners = connectionPoolListeners;
    }

    public List<String> getServerListeners() {
        return serverListeners;
    }

    public void setServerListeners(List<String> serverListeners) {
        this.serverListeners = serverListeners;
    }

    public List<String> getServerMonitorListeners() {
        return serverMonitorListeners;
    }

    public void setServerMonitorListeners(List<String> serverMonitorListeners) {
        this.serverMonitorListeners = serverMonitorListeners;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getGridFsTemplateName() {
        return gridFsTemplateName;
    }

    public void setGridFsTemplateName(String gridFsTemplateName) {
        this.gridFsTemplateName = gridFsTemplateName;
    }

    public String getGridFsDatabase() {
        return gridFsDatabase;
    }

    public void setGridFsDatabase(String gridFsDatabase) {
        this.gridFsDatabase = gridFsDatabase;
    }

    public List<String> getOptionedListeners() {
        return optionedListeners;
    }

    public void setOptionedListeners(List<String> optionedListeners) {
        this.optionedListeners = optionedListeners;
    }
}