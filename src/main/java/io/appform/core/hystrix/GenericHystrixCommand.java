package io.appform.core.hystrix;

import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

/**
 * Command that returns the single element
 */
public class GenericHystrixCommand<ReturnType> {

    private final HystrixObservableCommand.Setter setter;

    public GenericHystrixCommand(HystrixObservableCommand.Setter setter) {
        this.setter = setter;
    }

    public HystrixObservableCommand<ReturnType> executor(HandlerAdapter<ReturnType> function) throws Exception {
        return new HystrixObservableCommand<ReturnType>(setter) {
            @Override
            protected Observable<ReturnType> construct() {
                return Observable.create(observer -> {
                    if(!observer.isUnsubscribed()) {
                        try {
                            observer.onNext(function.run());
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
