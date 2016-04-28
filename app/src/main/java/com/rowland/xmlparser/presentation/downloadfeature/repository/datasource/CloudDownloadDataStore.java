package com.rowland.xmlparser.presentation.downloadfeature.repository.datasource;

import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.rowland.xmlparser.data.api.ApiManager;
import com.rowland.xmlparser.data.downloadfeature.cache.IDownloadCache;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayloadCollection;
import com.rowland.xmlparser.data.downloadfeature.repository.datasource.IDownloadDataStore;
import com.rowland.xmlparser.data.exception.NetworkConnectionException;
import com.rowland.xmlparser.data.utility.NetworkUtility;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link IDownloadDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudDownloadDataStore implements IDownloadDataStore {

    // Class log identifier
    public final static String LOG_TAG = CloudDownloadDataStore.class.getSimpleName();

    //private final IRestApi restApi;
    private final ApiManager mApiManager;
    private final IDownloadCache downloadCache;
    private final Action1<DownloadPayload> saveToCacheAction = downloadEntity -> {
        if (downloadEntity != null) {
            CloudDownloadDataStore.this.downloadCache.put(downloadEntity);
        }
    };

    /**
     * Construct a {@link IDownloadDataStore} based on connections to the api (Cloud).
     *
     * @param apiManager    The {@link ApiManager} implementation to use.
     * @param downloadCache A {@link IDownloadCache} to cache data retrieved from the api.
     */
    public CloudDownloadDataStore(ApiManager apiManager, IDownloadCache downloadCache) {
        this.mApiManager = apiManager;
        this.downloadCache = downloadCache;
    }

    @RxLogObservable
    @Override
    public Observable<List<String>> downloadPayloadList() {
        Observable<DownloadPayloadCollection> downloadListPayloadObservable = this.mApiManager.listDownloads();
        return downloadListPayloadObservable.create(subscriber -> {
            if (NetworkUtility.isNetworkAvailable(mApiManager.getContext())) {
                List<String> responseDownloadPayload = null;
                try {
                    responseDownloadPayload = downloadListPayloadObservable.toBlocking().single().getResult();
                    subscriber.onNext(responseDownloadPayload);
                    subscriber.onCompleted();
                    Log.d(LOG_TAG, "OUR LIST CONTENTS " + downloadListPayloadObservable.toBlocking().single().toString());
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                    Log.d(LOG_TAG, "NETWORK UNKNOWNHOST EXCEPTION 1");
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
                Log.d(LOG_TAG, "NETWORK INTERNET EXCEPTION");
            }
        });
    }

    @RxLogObservable
    @Override
    public Observable<DownloadPayload> downloadPayloadDetails(final String downloadKey) {
        Observable<DownloadPayload> downloadDetailsPayloadObservable = this.mApiManager.getDownloadById(downloadKey).doOnNext(saveToCacheAction);
        return downloadDetailsPayloadObservable.create(subscriber -> {
            DownloadPayload responseDownloadDetails = null;
            if (NetworkUtility.isNetworkAvailable(mApiManager.getContext())) {
                try {
                    responseDownloadDetails = downloadDetailsPayloadObservable.toBlocking().single();
                    subscriber.onNext(responseDownloadDetails);
                    subscriber.onCompleted();
                    Log.d(LOG_TAG, "OUR DETAILS CONTENTS " + downloadDetailsPayloadObservable.toBlocking().single().toString());
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }

            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }
}
