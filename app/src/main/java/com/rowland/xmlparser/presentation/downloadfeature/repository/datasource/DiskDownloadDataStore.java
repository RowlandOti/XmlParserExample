package com.rowland.xmlparser.presentation.downloadfeature.repository.datasource;

import com.rowland.xmlparser.data.downloadfeature.cache.IDownloadCache;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayloadCollection;
import com.rowland.xmlparser.data.downloadfeature.repository.datasource.IDownloadDataStore;
import com.rowland.xmlparser.domain.downloadfeature.model.Download;

import java.util.List;

import rx.Observable;

/**
 * {@link IDownloadDataStore} implementation based on file system data store.
 */
public class DiskDownloadDataStore implements IDownloadDataStore {

    private final IDownloadCache downloadCache;

    /**
     * Construct a {@link IDownloadDataStore} based file system data store.
     *
     * @param downloadCache A {@link IDownloadCache} to cache data retrieved from the api.
     */
    public DiskDownloadDataStore(IDownloadCache downloadCache) {
        this.downloadCache = downloadCache;
    }

    public Observable<List<String>> downloadPayloadList() {
        //TODO: implement simple cache for storing/retrieving collections of users.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<DownloadPayload> downloadPayloadDetails(final String downloadKey) {
        return this.downloadCache.get(downloadKey);
    }


}
