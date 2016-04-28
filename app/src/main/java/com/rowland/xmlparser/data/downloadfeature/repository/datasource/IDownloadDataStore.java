package com.rowland.xmlparser.data.downloadfeature.repository.datasource;

import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayload;
import com.rowland.xmlparser.data.downloadfeature.payload.DownloadPayloadCollection;

import java.util.List;
import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface IDownloadDataStore {
  /**
   * Get an {@link Observable} which will emit a List of {@link DownloadPayload}.
   */
  Observable<List<String>> downloadPayloadList();

  /**
   * Get an {@link Observable} which will emit a {@link DownloadPayload} by its id.
   *
   * @param downloadKey The id to retrieve download data.
   */
  Observable<DownloadPayload> downloadPayloadDetails(final String downloadKey);
}
