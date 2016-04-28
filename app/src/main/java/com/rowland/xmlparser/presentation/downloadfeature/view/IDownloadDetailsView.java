package com.rowland.xmlparser.presentation.downloadfeature.view;

import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.view.ILoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a download profile.
 */
public interface IDownloadDetailsView extends ILoadDataView {
  /**
   * Render a download in the UI.
   *
   * @param download The {@link DownloadModel} that will be shown.
   */
  void renderDownload(DownloadModel download);
}
