package com.rowland.xmlparser.presentation.downloadfeature.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.rowland.xmlparser.domain.exception.DefaultErrorBundle;
import com.rowland.xmlparser.domain.exception.IErrorBundle;
import com.rowland.xmlparser.domain.interactor.DefaultSubscriber;
import com.rowland.xmlparser.domain.interactor.UseCase;
import com.rowland.xmlparser.domain.downloadfeature.model.Download;
import com.rowland.xmlparser.presentation.exception.ErrorMessageFactory;
import com.rowland.xmlparser.presentation.internal.di.PerActivity;
import com.rowland.xmlparser.presentation.downloadfeature.mapper.DownloadModelDataMapper;
import com.rowland.xmlparser.presentation.presenter.IPresenter;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.downloadfeature.view.IDownloadDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link IPresenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class DownloadDetailsPresenter implements IPresenter {

  private IDownloadDetailsView viewDetailsView;

  private final UseCase getDownloadDetailsUseCase;
  private final DownloadModelDataMapper downloadModelDataMapper;

  @Inject
  public DownloadDetailsPresenter(@Named("downloadDetails") UseCase getDownloadDetailsUseCase, DownloadModelDataMapper downloadModelDataMapper) {
    this.getDownloadDetailsUseCase = getDownloadDetailsUseCase;
    this.downloadModelDataMapper = downloadModelDataMapper;
  }

  public void setView(@NonNull IDownloadDetailsView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getDownloadDetailsUseCase.unsubscribe();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by start retrieving download details.
   */
  public void initialize() {
    this.loadDownloadDetails();
  }

  /**
   * Loads download details.
   */
  private void loadDownloadDetails() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getDownloadDetails();
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(IErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(), errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

  private void showDownloadDetailsInView(Download download) {
    final DownloadModel downloadModel = this.downloadModelDataMapper.transform(download);
    this.viewDetailsView.renderDownload(downloadModel);
  }

  private void getDownloadDetails() {
    this.getDownloadDetailsUseCase.execute(new DownloadDetailsSubscriber());
  }

  @RxLogSubscriber
  private final class DownloadDetailsSubscriber extends DefaultSubscriber<Download> {

    @Override public void onCompleted() {
      DownloadDetailsPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      DownloadDetailsPresenter.this.hideViewLoading();
      DownloadDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      DownloadDetailsPresenter.this.showViewRetry();
    }

    @Override public void onNext(Download download) {
      DownloadDetailsPresenter.this.showDownloadDetailsInView(download);
    }
  }
}
