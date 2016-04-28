package com.rowland.xmlparser.domain.downloadfeature.interactor;

import com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository;
import com.rowland.xmlparser.domain.executor.IPostExecutionThread;
import com.rowland.xmlparser.domain.executor.IThreadExecutor;
import com.rowland.xmlparser.domain.interactor.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Download}.
 */
public class GetDownloadListInteractor extends UseCase {

    // Class log identifier
    public final static String LOG_TAG = GetDownloadListInteractor.class.getSimpleName();

    private final IDownloadRepository downloadRepository;

    @Inject
    public GetDownloadListInteractor(com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository downloadRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.downloadRepository = downloadRepository;
    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.downloadRepository.getList();
    }
}
