package com.rowland.xmlparser.domain.executor;

    import com.rowland.xmlparser.domain.interactor.UseCase;

    import java.util.concurrent.Executor;

    /**
     * Executor implementation can be based on different frameworks or techniques of asynchronous
     * execution, but every implementation will execute the
     * {@link UseCase} out of the UI thread.
     */
    public interface IThreadExecutor  extends Executor {}
