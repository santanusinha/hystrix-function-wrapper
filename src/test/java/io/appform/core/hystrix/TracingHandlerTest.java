package io.appform.core.hystrix;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.noop.NoopScopeManager;
import io.opentracing.noop.NoopSpan;
import io.opentracing.util.GlobalTracer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kanika.khetawat on 10/02/21
 */
public class TracingHandlerTest {

    @Test
    public void testGetTracer() {
        Tracer tracer = TracingHandler.getTracer();
        Assert.assertNotNull(tracer);
    }

    @Test
    public void testGetParentActiveSpan() {
        Assert.assertNull(TracingHandler.getParentActiveSpan(null));
        Span span = TracingHandler.getParentActiveSpan(GlobalTracer.get());
        Assert.assertNotNull(span);
        Assert.assertTrue(span instanceof NoopSpan);
    }

    @Test
    public void testStartChildSpan() {
        Assert.assertNull(TracingHandler.startChildSpan(null, NoopSpan.INSTANCE, "test"));
        Assert.assertNull(TracingHandler.startChildSpan(GlobalTracer.get(), null, "test"));
        Span span = TracingHandler.startChildSpan(GlobalTracer.get(), NoopSpan.INSTANCE, "test");
        Assert.assertNotNull(span);
        Assert.assertTrue(span instanceof NoopSpan);
    }

    @Test
    public void testActivateSpan() {
        Assert.assertNull(TracingHandler.activateSpan(null, NoopSpan.INSTANCE));
        Assert.assertNull(TracingHandler.activateSpan(GlobalTracer.get(), null));
        Scope scope = TracingHandler.activateSpan(GlobalTracer.get(), NoopSpan.INSTANCE);
        Assert.assertNotNull(scope);
        Assert.assertTrue(scope instanceof NoopScopeManager.NoopScope);
    }
}
