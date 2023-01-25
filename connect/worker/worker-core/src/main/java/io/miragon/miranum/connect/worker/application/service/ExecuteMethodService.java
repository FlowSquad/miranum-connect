package io.miragon.miranum.connect.binder.worker.application.service;

import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.out.ExecuteUseCaseInterceptor;
import io.miragon.miranum.connect.binder.worker.domain.BusinessException;
import io.miragon.miranum.connect.binder.worker.domain.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Log
@RequiredArgsConstructor
public class ExecuteMethodService implements ExecuteMethodUseCase {

    private final List<ExecuteUseCaseInterceptor> interceptors;


    @Override
    public Object execute(final ExecuteMethodCommand command) throws BusinessException, TechnicalException {
        try {
            //1. execute interceptors if present
            this.interceptors.forEach(obj -> obj.intercept(command.getData(), command.getWorker()));
            //2. execute methods
            return command.getWorker().getMethod().invoke(command.getWorker().getInstance(), command.getData());
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
