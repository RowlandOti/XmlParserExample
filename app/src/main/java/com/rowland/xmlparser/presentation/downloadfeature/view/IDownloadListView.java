package com.rowland.xmlparser.presentation.downloadfeature.view;

import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.view.ILoadDataView;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link DownloadModel}.
 */
public interface IDownloadListView extends ILoadDataView {
  /**
   * Render a download list in the UI.
   *
   * @param downloadModelCollection The collection of {@link DownloadModel} that will be shown.
   */
  void renderDownloadList(Collection<String> downloadModelCollection);

  /**
   * View a {@link DownloadModel} profile/details.
   *
   * @param downloadModel The download that will be shown.
   */
  void viewDownload(String downloadModel);
}
