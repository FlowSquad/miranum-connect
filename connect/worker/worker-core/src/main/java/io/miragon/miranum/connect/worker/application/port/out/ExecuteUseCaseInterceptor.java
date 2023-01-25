package io.miragon.miranum.connect.binder.worker.application.port.out;

import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;

/**
 * Interceptor that can be used to execute code before the actual use case is called.
 */
public interface ExecuteUseCaseInterceptor {

    void intercept(Object data, WorkerInfo useCaseInfo);

}
