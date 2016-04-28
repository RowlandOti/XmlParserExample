package com.rowland.xmlparser.data.downloadfeature.cache;


import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;

import rx.Observable;

/**
 * An interface representing a user Cache.
 */
public interface IDownloadCache {
    /**
     * Gets an {@link Observable} which will emit a {@link DownloadPayload}.
     *
     * @param downloadKey The download id to retrieve data.
     */
    Observable<DownloadPayload> get(final String downloadKey);

    /**
     * Puts and element into the cache.
     *
     * @param userPayload Element to insert in the cache.
     */
    void put(DownloadPayload userPayload);

    /**
     * Checks if an element (Download) exists in the cache.
     *
     * @param userId The id used to look for inside the cache.
     * @return true if the element is cached, otherwise false.
     */
    boolean isCached(final String userId);

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
