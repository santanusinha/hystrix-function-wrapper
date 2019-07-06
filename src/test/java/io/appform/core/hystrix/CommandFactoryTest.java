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

import com.hystrix.configurator.config.HystrixConfig;
import com.hystrix.configurator.core.HystrixConfigurationFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
                .execute());
    }

    @Test
    public void testCreateCollectionCommand() throws Exception {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(true);
        List<Boolean> result = CommandFactory.<List<Boolean>>create("test", "testList")
                .executor(() -> list)
                .execute();
        Assert.assertEquals(result, list);
    }
}