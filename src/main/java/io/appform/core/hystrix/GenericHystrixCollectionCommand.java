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

import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

import java.util.List;

/**
 * A command that returns an observable list
 */
public class GenericHystrixCollectionCommand<ReturnType> {

    private final HystrixObservableCommand.Setter setter;

    public GenericHystrixCollectionCommand(HystrixObservableCommand.Setter setter) {
        this.setter = setter;
    }

    public HystrixObservableCommand<ReturnType> executor(HandlerAdapter<List<ReturnType>> function) throws Exception {
        return new HystrixObservableCommand<ReturnType>(setter) {
            @Override
            protected Observable<ReturnType> construct() {
                return Observable.create(observer -> {
                    if(!observer.isUnsubscribed()) {
                        try {
                            List<ReturnType> objects = function.run();
                            objects.stream().forEach(observer::onNext);
                            observer.onCompleted();
                        } catch (Throwable t) {
                            observer.onError(t);
                        }
                    }
                });
            }

        };
    }
}
