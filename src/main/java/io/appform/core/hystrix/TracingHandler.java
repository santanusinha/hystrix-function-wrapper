package io.appform.core.hystrix;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class that handles all tracing related operations
 */
@Slf4j
public class TracingHandler {

    public static Tracer getTracer() {
        try {
            return GlobalTracer.get();
        } catch (Exception e) {
            log.error("Error while getting tracer", e);
            return null;
        }
    }

    public static Span getParentActiveSpan(final Tracer tracer) {
        try {
            return tracer == null ? null : tracer.activeSpan();
        } catch (Exception e) {
            log.error("Error while getting activeSpan", e);
            return null;
        }
    }

    public static Span startChildSpan(final Tracer tracer,
                                      final Span parentSpan,
                                      final String command) {
        try {
            if (tracer == null || parentSpan == null) {
                return null;
            }
            return tracer.buildSpan("hystrix:"+command)
                    .asChildOf(parentSpan)
                    .withTag("hystrix.command", command)
                    .start();
        } catch (Exception e) {
            log.error("Error while starting child span", e);
            return null;
        }
    }

    public static Scope activateSpan(final Tracer tracer,
                                     final Span span) {
        try {
            if (tracer == null || span == null) {
                return null;
            }
            return tracer.activateSpan(span);
        } catch (Exception e) {
            log.error("Error while activating span", e);
            return null;
        }
    }

    public static void closeScopeAndSpan(final Span span,
                                         final Scope scope) {
        try {
            if (scope != null) {
                scope.close();
            }
            if (span != null) {
                span.finish();
            }
        } catch (Exception e) {
            log.error("Error while closing span and scope", e);
        }
    }
}
