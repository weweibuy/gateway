/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weweibuy.gateway.sentinel.sc.api;


import com.weweibuy.gateway.sentinel.common.api.ApiDefinition;
import com.weweibuy.gateway.sentinel.sc.api.matcher.WebExchangeApiMatcher;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GatewayApiMatcherManager {

    private static final Map<String, Map<String, WebExchangeApiMatcher>> ROUTER_API_MATCHER_MAP
            = new ConcurrentHashMap<>();

    public static Map<String, WebExchangeApiMatcher> getApiMatcherMap(String routerId) {
        return Optional.ofNullable(ROUTER_API_MATCHER_MAP.get(routerId))
                .map(Collections::unmodifiableMap)
                .orElse(Collections.emptyMap());
    }

    public static Optional<WebExchangeApiMatcher> getMatcher(String routerId, String apiName) {
        return Optional.ofNullable(ROUTER_API_MATCHER_MAP.get(routerId))
                .map(m -> m.get(apiName));
    }

    public static Set<ApiDefinition> getApiDefinitionSet() {
        Collection<Map<String, WebExchangeApiMatcher>> values = ROUTER_API_MATCHER_MAP.values();

        return null;
    }

    static synchronized void loadApiDefinitions(/*@Valid*/ Set<ApiDefinition> definitions) {
        if (definitions == null || definitions.isEmpty()) {
            ROUTER_API_MATCHER_MAP.clear();
            return;
        }
        definitions.forEach(GatewayApiMatcherManager::addApiDefinition);
    }

    static void addApiDefinition(ApiDefinition definition) {
        ROUTER_API_MATCHER_MAP.computeIfAbsent(definition.getRouterId(), key -> {
            Map<String, WebExchangeApiMatcher> matcherMap = new ConcurrentHashMap<>();
            matcherMap.put(definition.getApiName(), new WebExchangeApiMatcher(definition));
            return matcherMap;
        });
    }


}
