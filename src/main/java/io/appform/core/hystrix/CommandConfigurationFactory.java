package io.appform.core.hystrix;

import com.netflix.hystrix.HystrixCommand;

public interface CommandConfigurationFactory {

    HystrixCommand.Setter provide(String group, String command);

}
