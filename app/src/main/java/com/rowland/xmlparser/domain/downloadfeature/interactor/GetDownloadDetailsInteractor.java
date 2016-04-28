package com.rowland.xmlparser.domain.downloadfeature.interactor;

import com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository;
import com.rowland.xmlparser.domain.executor.IPostExecutionThread;
import com.rowland.xmlparser.domain.executor.IThreadExecutor;
import com.rowland.xmlparser.domain.interactor.UseCase;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link com.rowland.xmlparser.domain.downloadfeature.model.Download}.
 */
public class GetDownloadDetailsInteractor extends UseCase {

    private final String downloadKey;
    private final com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository downloadRepository;

    @Inject
    public GetDownloadDetailsInteractor(String downloadKey, IDownloadRepository downloadRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.downloadKey = downloadKey;
        this.downloadRepository = downloadRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.downloadRepository.getItem(this.downloadKey);
    }
}
