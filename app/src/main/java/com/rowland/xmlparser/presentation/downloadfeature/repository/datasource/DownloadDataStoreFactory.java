package com.rowland.xmlparser.presentation.downloadfeature.repository.datasource;

import android.content.Context;

import com.rowland.xmlparser.data.downloadfeature.cache.IDownloadCache;
import com.rowland.xmlparser.data.downloadfeature.repository.datasource.IDownloadDataStore;
import com.rowland.xmlparser.presentation.ApplicationController;
import com.rowland.xmlparser.data.api.ApiManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link IDownloadDataStore}.
 */
@Singleton
public class DownloadDataStoreFactory {

    private final Context context;
    private final IDownloadCache downloadCache;

    @Inject
    public DownloadDataStoreFactory(Context context, IDownloadCache downloadCache) {
        if (context == null || downloadCache == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.downloadCache = downloadCache;
    }

    /**
     * Create {@link IDownloadDataStore} from a download id.
     */
    public IDownloadDataStore create(String downloadKey) {
        IDownloadDataStore downloadDataStore;

        if (!this.downloadCache.isExpired() && this.downloadCache.isCached(downloadKey)) {
            downloadDataStore = new DiskDownloadDataStore(this.downloadCache);
        } else {
            downloadDataStore = createCloudDataStore();
        }
        return downloadDataStore;
    }

    /**
     * Create {@link IDownloadDataStore} to retrieve data from the Cloud.
     */
    public IDownloadDataStore createCloudDataStore() {
        ApiManager apiManager = ApplicationController.apiManager;
        return new CloudDownloadDataStore(apiManager, this.downloadCache);
    }
}
