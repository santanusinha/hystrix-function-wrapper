package io.appform.core.hystrix;

import com.hystrix.configurator.config.CommandThreadPoolConfig;
import com.hystrix.configurator.config.HystrixConfig;
import com.hystrix.configurator.config.HystrixDefaultConfig;
import com.hystrix.configurator.core.HystrixConfigurationFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;


public class CommandFactoryMDCTest {

    private final String TEST_KEY = "test";

    @Before
    public void init() {
        MDC.put(TEST_KEY, TEST_KEY);
    }

    @Test
    public void testMDCInSemaphore() {
        HystrixConfigurationFactory.init(getConfig(true));
        execute();
        Assert.assertTrue("MDC is cleared in semaphore isolation strategy.",
                !MDC.getCopyOfContextMap().isEmpty() && MDC.get(TEST_KEY).equals(TEST_KEY));

    }

    private HystrixConfig getConfig(boolean semaphoreIsolation) {
        return HystrixConfig.builder()
                .defaultConfig(HystrixDefaultConfig.builder()
                        .threadPool(CommandThreadPoolConfig
                                .builder()
                                .semaphoreIsolation(semaphoreIsolation).build())
                        .build())
                .build();
    }

    private void execute() {
        CommandFactory.<Boolean>create("test", "test")
                .executor(() -> true)
                .execute();
    }
}
