package com.rowland.xmlparser.presentation.downloadfeature.repository;

import android.util.Log;

import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.mapper.DownloadPayloadDataMapper;
import com.rowland.xmlparser.data.downloadfeature.repository.datasource.IDownloadDataStore;
import com.rowland.xmlparser.domain.repository.IRepository;
import com.rowland.xmlparser.domain.downloadfeature.model.Download;
import com.rowland.xmlparser.domain.downloadfeature.repository.IDownloadRepository;
import com.rowland.xmlparser.presentation.downloadfeature.repository.datasource.DownloadDataStoreFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link IDownloadRepository} for retrieving download data.
 */
@Singleton
public class DownloadDataRepository implements IRepository, IDownloadRepository {

    // Class log identifier
    public final static String LOG_TAG = DownloadDataRepository.class.getSimpleName();

    private final DownloadDataStoreFactory downloadDataStoreFactory;
    private final DownloadPayloadDataMapper downloadPayloadDataMapper;

    /**
     * Constructs a {@link IDownloadRepository}.
     *
     * @param dataStoreFactory     A factory to construct different data source implementations.
     * @param downloadPayloadDataMapper {@link DownloadPayloadDataMapper}.
     */
    @Inject
    public DownloadDataRepository(DownloadDataStoreFactory dataStoreFactory, DownloadPayloadDataMapper downloadPayloadDataMapper) {
        this.downloadDataStoreFactory = dataStoreFactory;
        this.downloadPayloadDataMapper = downloadPayloadDataMapper;
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<List<String>> getList() {
        //we always get all downloads from the cloud
        final IDownloadDataStore downloadDataStore = this.downloadDataStoreFactory.createCloudDataStore();
        Log.d(LOG_TAG, "I WAS CALLED by getList");
        return downloadDataStore.downloadPayloadList().map(downloadPayload -> downloadPayload);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<Download> getItem(String downloadKey) {
        final IDownloadDataStore downloadDataStore = this.downloadDataStoreFactory.create(downloadKey);
        Log.d(LOG_TAG, "I WAS CALLED by getItem");
        return downloadDataStore.downloadPayloadDetails(downloadKey).map(downloadEntity -> this.downloadPayloadDataMapper.transform(downloadEntity));
    }
}
