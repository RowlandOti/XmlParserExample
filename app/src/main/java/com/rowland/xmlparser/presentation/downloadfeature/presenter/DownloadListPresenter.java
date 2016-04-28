package com.rowland.xmlparser.presentation.downloadfeature.presenter;

import android.support.annotation.NonNull;

import com.rowland.xmlparser.domain.downloadfeature.model.Download;
import com.rowland.xmlparser.domain.exception.DefaultErrorBundle;
import com.rowland.xmlparser.domain.exception.IErrorBundle;
import com.rowland.xmlparser.domain.interactor.DefaultSubscriber;
import com.rowland.xmlparser.domain.interactor.UseCase;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.downloadfeature.view.IDownloadListView;
import com.rowland.xmlparser.presentation.exception.ErrorMessageFactory;
import com.rowland.xmlparser.presentation.internal.di.PerActivity;
import com.rowland.xmlparser.presentation.downloadfeature.mapper.DownloadModelDataMapper;
import com.rowland.xmlparser.presentation.presenter.IPresenter;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link IPresenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class DownloadListPresenter implements IPresenter {

    // Class log identifier
    public final static String LOG_TAG = DownloadListPresenter.class.getSimpleName();

    private IDownloadListView viewListView;

    private final UseCase getDownloadListUseCase;
    private final DownloadModelDataMapper downloadModelDataMapper;

    @Inject
    public DownloadListPresenter(@Named("downloadList") UseCase getDownloadListDownloadCase, DownloadModelDataMapper downloadModelDataMapper) {
        this.getDownloadListUseCase = getDownloadListDownloadCase;
        this.downloadModelDataMapper = downloadModelDataMapper;
    }

    public void setView(@NonNull IDownloadListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getDownloadListUseCase.unsubscribe();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the download list.
     */
    public void initialize() {
        this.loadDownloadList();
    }

    /**
     * Loads all downloads.
     */
    private void loadDownloadList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getDownloadList();
    }

    public void onDownloadClicked(String downloadModel) {
        this.viewListView.viewDownload(downloadModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(IErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showDownloadsCollectionInView(Collection<String> downloadsCollection) {
        this.viewListView.renderDownloadList(downloadsCollection);
    }

    private void getDownloadList() {
        this.getDownloadListUseCase.execute(new DownloadListSubscriber());
    }

    private final class DownloadListSubscriber extends DefaultSubscriber<List<String>> {

        @Override
        public void onCompleted() {
            DownloadListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            DownloadListPresenter.this.hideViewLoading();
            DownloadListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            DownloadListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<String> downloads) {
            DownloadListPresenter.this.showDownloadsCollectionInView(downloads);
        }
    }
}
