package io.appform.core.hystrix;

import com.hystrix.configurator.config.HystrixConfig;
import com.hystrix.configurator.core.HystrixConfigurationFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for {@link CommandFactory}
 */
public class CommandFactoryTest {

    @Before
    public void setup() {
        HystrixConfigurationFactory.init(new HystrixConfig());
    }

    @Test
    public void testCreate() throws Exception {
        Assert.assertTrue(CommandFactory.<Boolean>create("test", "test")
                .executor(() -> true)
                .toObservable()
                .toBlocking()
                .single());
    }

    @Test
    public void testCreateCollectionCommand() throws Exception {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(true);
        List<Boolean> result = CommandFactory.<List<Boolean>>create("test", "testList")
                .executor(() -> list)
                .toObservable()
                .flatMap(Observable::from)
                .toList()
                .toBlocking()
                .single();
        Assert.assertEquals(result, list);
    }
}