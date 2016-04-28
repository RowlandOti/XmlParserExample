package com.rowland.xmlparser.presentation.internal.di.modules;

import com.rowland.xmlparser.domain.downloadfeature.interactor.GetDownloadDetailsInteractor;
import com.rowland.xmlparser.domain.downloadfeature.interactor.GetDownloadListInteractor;
import com.rowland.xmlparser.domain.executor.IPostExecutionThread;
import com.rowland.xmlparser.domain.executor.IThreadExecutor;
import com.rowland.xmlparser.domain.interactor.UseCase;
import com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository;
import com.rowland.xmlparser.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides download related collaborators.
 */
@Module
public class DownloadModule {

    private String downloadKey = null;

    public DownloadModule() {
    }

    public DownloadModule(String downloadKey) {
        this.downloadKey = downloadKey;
    }

    @Provides
    @PerActivity
    @Named("downloadList")
    UseCase provideGetDownloadListUseCase(GetDownloadListInteractor getDownloadList) {
        return getDownloadList;
    }

    @Provides
    @PerActivity
    @Named("downloadDetails")
    UseCase provideGetDownloadDetailsUseCase(IDownloadRepository downloadRepository, IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
        return new GetDownloadDetailsInteractor(downloadKey, downloadRepository, threadExecutor, postExecutionThread);
    }
}