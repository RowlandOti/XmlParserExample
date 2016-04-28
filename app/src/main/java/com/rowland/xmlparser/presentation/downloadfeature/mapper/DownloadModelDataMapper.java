package com.rowland.xmlparser.presentation.downloadfeature.mapper;

import com.rowland.xmlparser.domain.downloadfeature.model.Download;
import com.rowland.xmlparser.presentation.internal.di.PerActivity;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Download} (in the domain layer) to {@link DownloadModel} in the
 * presentation layer.
 */
@PerActivity
public class DownloadModelDataMapper {

  @Inject
  public DownloadModelDataMapper() {}

  /**
   * Transform a {@link Download} into an {@link DownloadModel}.
   *
   * @param download Object to be transformed.
   * @return {@link DownloadModel}.
   */
  public DownloadModel transform(Download download) {
    if (download == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    DownloadModel downloadModel = new DownloadModel();
    downloadModel.setKey(download.getKey());
    downloadModel.setValue(download.getValue());

    return downloadModel;
  }

  /**
   * Transform a Collection of {@link Download} into a Collection of {@link DownloadModel}.
   *
   * @param downloadsCollection Objects to be transformed.
   * @return List of {@link DownloadModel}.
   */
  public Collection<DownloadModel> transform(Collection<Download> downloadsCollection) {
    Collection<DownloadModel> downloadModelsCollection;

    if (downloadsCollection != null && !downloadsCollection.isEmpty()) {
      downloadModelsCollection = new ArrayList<>();
      for (Download download : downloadsCollection) {
        downloadModelsCollection.add(transform(download));
      }
    } else {
      downloadModelsCollection = Collections.emptyList();
    }

    return downloadModelsCollection;
  }
}
