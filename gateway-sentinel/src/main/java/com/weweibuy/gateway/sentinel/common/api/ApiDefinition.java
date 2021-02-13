/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weweibuy.gateway.sentinel.common.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * A group of HTTP API patterns.
 *
 * @author Eric Zhao
 * @since 1.6.0
 */
@Data
@NoArgsConstructor
public class ApiDefinition {

    private String apiName;

    private String routerId;

    private Set<String> path;

    public ApiDefinition(String apiName, String routerId) {
        this.apiName = apiName;
        this.routerId = routerId;
    }

}
