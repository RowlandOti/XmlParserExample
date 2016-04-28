package com.rowland.xmlparser.data.downloadfeature.rest.service;


import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayloadCollection;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface IDownloadService {

    // Constants for Downloads API End Points
    String DOWNLOADS = "/";
    String DOWNLOADS_ITEM = "/downloads/{key}";

    @GET(DOWNLOADS)
    Observable<DownloadPayloadCollection> listDownloads();

    @GET(DOWNLOADS_ITEM)
    Observable<DownloadPayload> getDownloadWithId(@Path("key") String downloadKey);

}
