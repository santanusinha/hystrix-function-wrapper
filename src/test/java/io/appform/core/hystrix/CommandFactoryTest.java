package io.appform.core.hystrix;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for {@link CommandFactory}
 */
public class CommandFactoryTest {

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
        List<Boolean> result = CommandFactory.<Boolean>createCollectionCommand("test", "testList")
                .executor(() -> list)
                .toObservable()
                .toList()
                .toBlocking()
                .single();
        Assert.assertEquals(result, list);
    }
}