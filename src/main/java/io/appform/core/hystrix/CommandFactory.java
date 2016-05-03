package io.appform.core.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;

/**
 * Builds hystrix command
 */
public class CommandFactory {

    public static <ReturnType> GenericHystrixCommand<ReturnType> create(String group,
                                                                       String command) {
        return new GenericHystrixCommand<>(
                HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey(group, command))));

    }

    public static <ReturnType> GenericHystrixCollectionCommand<ReturnType> createCollectionCommand(String group,
                                                                                  String command) {
        return new GenericHystrixCollectionCommand<>(
                HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey(group, command))));

    }

    private static String commandKey(final String group, final String command) {
        return String.format("%s.%s", group, command);
    }
}
