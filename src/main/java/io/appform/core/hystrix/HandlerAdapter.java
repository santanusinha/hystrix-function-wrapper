package io.appform.core.hystrix;

/**
 * Adapter for a function
 */
public interface HandlerAdapter<T> {
    T run() throws Exception;
}
