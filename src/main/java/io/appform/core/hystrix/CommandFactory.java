/*
 * Copyright (c) 2016 Santanu Sinha <santanu.sinha@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appform.core.hystrix;

import java.util.UUID;

/**
 * Builds hystrix command
 */
public class CommandFactory {

    public static <R> GenericHystrixCommand<R> create(
            String group, String command, String traceId) {
        return new GenericHystrixCommand<>(group, traceId, command);
    }

    public static <R> GenericHystrixCommand<R> create(
            String group, String command) {
        return create(group,command, UUID.randomUUID().toString().replaceAll("-", ""));
    }

    private CommandFactory() {}
}
