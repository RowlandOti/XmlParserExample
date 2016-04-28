package com.rowland.xmlparser.presentation.downloadfeature.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

import com.rowland.xmlparser.R;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.downloadfeature.presenter.DownloadDetailsPresenter;
import com.rowland.xmlparser.presentation.downloadfeature.view.IDownloadDetailsView;
import com.rowland.xmlparser.presentation.downloadfeature.view.activity.DownloadDetailsActivity;
import com.rowland.xmlparser.presentation.internal.di.components.DownloadComponent;
import com.rowland.xmlparser.presentation.view.fragment.ABaseFragment;

import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that shows details of a certain download.
 */
public class DownloadDetailsFragment extends ABaseFragment implements IDownloadDetailsView {

    @Inject
    DownloadDetailsPresenter downloadDetailsPresenter;

    @Nullable
    @Bind(R.id.tb_download)
    Toolbar mToolbar;
    @Bind(R.id.iv_cover)
    ImageView iv_cover;
    @Bind(R.id.tv_value)
    TextView tv_value;
    @Bind(R.id.tv_key)
    TextView tv_key;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry)
    RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;

    public DownloadDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(DownloadComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_download_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.downloadDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadDownloadDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.downloadDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.downloadDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.downloadDetailsPresenter.destroy();
    }

    @Override
    public void renderDownload(DownloadModel download) {
        if (download != null) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            this.iv_cover.setBackgroundColor(color);
            this.tv_key.setText(download.getKey());
            this.tv_value.setText(download.getValue());
        }
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
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads all downloads.
     */
    private void loadDownloadDetails() {
        if (this.downloadDetailsPresenter != null) {
            this.downloadDetailsPresenter.initialize();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Set the ToolBar
        ((DownloadDetailsActivity) getActivity()).setToolbar(mToolbar, true, true, 0);
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        DownloadDetailsFragment.this.loadDownloadDetails();
    }
}
