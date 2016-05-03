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
