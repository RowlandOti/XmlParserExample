package com.rowland.xmlparser.data.downloadfeature.payload.mapper;

import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.domain.downloadfeature.model.Download;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link DownloadPayload} (in the data layer) to {@link Download} in the
 * domain layer.
 */
@Singleton
public class DownloadPayloadDataMapper {

    @Inject
    public DownloadPayloadDataMapper() {
    }

    /**
     * Transform a {@link DownloadPayload} into an {@link Download}.
     *
     * @param userPayload Object to be transformed.
     * @return {@link Download} if valid {@link DownloadPayload} otherwise null.
     */
    public Download transform(DownloadPayload userPayload) {
        Download download = null;
        if (userPayload != null) {
            download = new Download();
            download.setKey(userPayload.getDownloadPayloadKey());
            download.setValue(userPayload.getValue());
        }
        return download;
    }

    /**
     * Transform a List of {@link DownloadPayload} into a Collection of {@link Download}.
     *
     * @param downloadPayloadCollection Object Collection to be transformed.
     * @return {@link Download} if valid {@link DownloadPayload} otherwise null.
     */
    public List<Download> transform(Collection<DownloadPayload> downloadPayloadCollection) {
        List<Download> downloadList = new ArrayList<>(20);
        Download user;
        for (DownloadPayload userPayload : downloadPayloadCollection) {
            user = transform(userPayload);
            if (user != null) {
                downloadList.add(user);
            }
        }
        return downloadList;
    }
}

