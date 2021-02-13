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
package com.weweibuy.gateway.sentinel.common.api.matcher;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.function.Predicate;
import com.weweibuy.framework.common.core.utils.HttpRequestUtils;
import com.weweibuy.gateway.sentinel.common.api.ApiDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.6.0
 */
public abstract class AbstractApiMatcher implements Predicate<String> {

    protected final String apiName;

    protected final String routerId;

    protected final ApiDefinition apiDefinition;

    /**
     * 精确匹配路径
     */
    protected final Set<String> exactPathSet = new HashSet<>();

    /**
     * 路径匹配  eg: /**
     */
    protected final Set<String> matchPathSet = new HashSet<>();

    public AbstractApiMatcher(ApiDefinition apiDefinition) {
        AssertUtil.notNull(apiDefinition, "apiDefinition cannot be null");
        AssertUtil.assertNotBlank(apiDefinition.getApiName(), "apiName cannot be empty");
        this.apiName = apiDefinition.getApiName();
        this.routerId = apiDefinition.getRouterId();
        this.apiDefinition = apiDefinition;

        try {
            initializeMatchers();
        } catch (Exception ex) {
            RecordLog.warn("[GatewayApiMatcher] Failed to initialize internal matchers", ex);
        }
    }

    /**
     * Initialize the matchers.
     */
    protected abstract void initializeMatchers();

    @Override
    public boolean test(String path) {
        return exactPathSet.contains(path) || matchPathSet.stream()
                .anyMatch(m -> HttpRequestUtils.isMatchPath(m, path));
    }

    public String getApiName() {
        return apiName;
    }

    public ApiDefinition getApiDefinition() {
        return apiDefinition;
    }
}
