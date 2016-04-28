package com.rowland.xmlparser.presentation.downloadfeature.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rowland.xmlparser.R;
import com.rowland.xmlparser.presentation.downloadfeature.view.IDownloadListView;
import com.rowland.xmlparser.presentation.downloadfeature.view.activity.DownloadListActivity;
import com.rowland.xmlparser.presentation.downloadfeature.view.adapter.DownloadAdapter;
import com.rowland.xmlparser.presentation.downloadfeature.view.layoutmanager.DownloadLinearLayoutManager;
import com.rowland.xmlparser.presentation.internal.di.components.DownloadComponent;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.downloadfeature.presenter.DownloadListPresenter;
import com.rowland.xmlparser.presentation.view.fragment.ABaseFragment;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows a list of Downloads.
 */
public class DownloadListFragment extends ABaseFragment implements IDownloadListView {

    // Class log identifier
    public final static String LOG_TAG = DownloadListFragment.class.getSimpleName();
    /**
     * Interface for listening download list events.
     */
    public interface DownloadListListener {
        void onDownloadClicked(final String downloadModel);
    }

    @Inject
    DownloadListPresenter downloadListPresenter;
    @Inject
    DownloadAdapter downloadsAdapter;

    @Nullable
    @Bind(R.id.tb_download)
    Toolbar mToolbar;
    @Bind(R.id.rv_downloads)
    RecyclerView rv_downloads;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    private DownloadListListener downloadListListener;

    public DownloadListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof DownloadListListener) {
            this.downloadListListener = (DownloadListListener) activity;
        }
        // Set the ToolBar
        ((DownloadListActivity) getActivity()).setToolbar(mToolbar, false, true, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(DownloadComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_download_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.downloadListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadDownloadList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.downloadListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.downloadListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_downloads.setAdapter(null);
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.downloadListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.downloadListListener = null;
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderDownloadList(Collection<String> downloadModelCollection) {
        if (downloadModelCollection != null) {
            this.downloadsAdapter.setDownloadsCollection(downloadModelCollection);
        }
    }

    @Override
    public void viewDownload(String downloadModel) {
        if (this.downloadListListener != null) {
            this.downloadListListener.onDownloadClicked(downloadModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.downloadsAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_downloads.setLayoutManager(new DownloadLinearLayoutManager(context()));
        this.rv_downloads.setAdapter(downloadsAdapter);
    }

    /**
     * Loads all downloads.
     */
    private void loadDownloadList() {
        this.downloadListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        DownloadListFragment.this.loadDownloadList();
    }

    private DownloadAdapter.OnItemClickListener onItemClickListener =
            new DownloadAdapter.OnItemClickListener() {
                @Override
                public void onDownloadItemClicked(String downloadModel) {
                    if (DownloadListFragment.this.downloadListPresenter != null && downloadModel != null) {
                        DownloadListFragment.this.downloadListPresenter.onDownloadClicked(downloadModel);
                    }
                }
            };
}
