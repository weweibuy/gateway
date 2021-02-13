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
package com.weweibuy.gateway.sentinel.sc.api.matcher;

import com.weweibuy.gateway.sentinel.common.api.ApiDefinition;
import com.weweibuy.gateway.sentinel.common.api.matcher.AbstractApiMatcher;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
public class WebExchangeApiMatcher extends AbstractApiMatcher {

    public WebExchangeApiMatcher(ApiDefinition apiDefinition) {
        super(apiDefinition);
    }

    @Override
    protected void initializeMatchers() {
        if (CollectionUtils.isNotEmpty(apiDefinition.getPath())) {
            Map<Boolean, List<String>> pathMatchMap = apiDefinition.getPath().stream()
                    .collect(Collectors.groupingBy(p -> p.indexOf('*') != -1));
            Optional.ofNullable(pathMatchMap.get(true))
                    .ifPresent(l -> matchPathSet.addAll(l));
            Optional.ofNullable(pathMatchMap.get(false))
                    .ifPresent(l -> exactPathSet.addAll(l));
        }
    }

}
