package com.rowland.xmlparser.presentation.internal.di.modules;

import android.content.Context;

import com.rowland.xmlparser.data.downloadfeature.cache.DownloadCache;
import com.rowland.xmlparser.data.downloadfeature.cache.IDownloadCache;
import com.rowland.xmlparser.data.executor.JobThreadExecutor;

import com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository;
import com.rowland.xmlparser.presentation.downloadfeature.repository.DownloadDataRepository;
import com.rowland.xmlparser.domain.executor.IPostExecutionThread;
import com.rowland.xmlparser.domain.executor.IThreadExecutor;
import com.rowland.xmlparser.presentation.ApplicationController;
import com.rowland.xmlparser.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final ApplicationController application;

    public ApplicationModule(ApplicationController application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    IThreadExecutor provideThreadExecutor(JobThreadExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    IPostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    IDownloadCache provideDownloadCache(DownloadCache downloadCache) {
        return downloadCache;
    }

    @Provides
    @Singleton
    IDownloadRepository provideDownloadRepository(DownloadDataRepository downloadDataRepository) {
        return downloadDataRepository;
    }
}
