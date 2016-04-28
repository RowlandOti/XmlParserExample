package com.rowland.xmlparser.data.api;

import android.content.Context;

import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayloadCollection;

import java.util.List;

import rx.Observable;

public class ApiManager extends ABaseApiManager {

    // The class Log identifier
    private static final String LOG_TAG = ABaseApiManager.class.getSimpleName();

    public ApiManager(Context context) {
        mContext = context;
    }

    /**
     * Download API
     */
    public Observable<DownloadPayloadCollection> listDownloads() {
        return getDownloadsApi().listDownloads();
    }

    public Observable<DownloadPayload> getDownloadById(String key) {
        return getDownloadsApi().getDownloadWithId(key);
    }
}
