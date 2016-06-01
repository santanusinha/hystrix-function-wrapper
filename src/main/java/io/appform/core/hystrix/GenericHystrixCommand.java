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

import com.netflix.hystrix.HystrixCommand;

/**
 * Command that returns the single element
 */
public class GenericHystrixCommand<ReturnType> {

    private final HystrixCommand.Setter setter;

    public GenericHystrixCommand(HystrixCommand.Setter setter) {
        this.setter = setter;
    }

    public HystrixCommand<ReturnType> executor(HandlerAdapter<ReturnType> function) throws Exception {
        return new HystrixCommand<ReturnType>(setter) {
            @Override
            protected ReturnType run() throws Exception {
                return function.run();
            }

        };
    }
}
