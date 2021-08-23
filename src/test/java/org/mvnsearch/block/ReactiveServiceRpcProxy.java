package org.mvnsearch.block;

/**
 * Reactive service proxy for RPC
 *
 * @author linux_china
 */
public interface ReactiveServiceRpcProxy {
    Object invoke(String serviceName, String method, Object params, String version);
}

