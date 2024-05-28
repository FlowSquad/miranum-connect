package io.miragon.miranum.platform.connect.worker.worker.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerExecuteApi;
import io.miragon.miranum.platform.connect.worker.worker.api.WorkerInterceptor;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of the worker execute api.
 * Follows the observer pattern to register and execute Workers {@see https://en.wikipedia.org/wiki/Observer_pattern#Java}.
 */
@RequiredArgsConstructor
public class WorkerExecuteApiImpl implements WorkerExecuteApi {

    private final List<WorkerInterceptor> interceptors;

    @Override
    public Map<String, Object> execute(final WorkerExecutor executor, final Object data) {
        try {
            final Object in = this.mapInput(executor.getInputType(), data);
            this.interceptors.forEach(obj -> obj.intercept(executor, in));
            return this.mapOutput(executor.execute(in));
        } catch (final IllegalAccessException | JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            // Unwrap the exception wrapped by the InvocationTargetException
            throw (RuntimeException) e.getTargetException();
        }
    }

    private Object mapInput(final Class<?> inputType, final Object object) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return Objects.nonNull(inputType) ? mapper.readValue(mapper.writeValueAsString(object), inputType) : null;
    }

    private Map<String, Object> mapOutput(final Object output) {
        if (Objects.isNull(output)) {
            return new HashMap<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(output, new TypeReference<>() {});
    }
}
