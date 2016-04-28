package com.rowland.xmlparser.presentation.downloadfeature.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rowland.xmlparser.R;
import com.rowland.xmlparser.presentation.downloadfeature.model.DownloadModel;
import com.rowland.xmlparser.presentation.downloadfeature.view.fragment.DownloadListFragment;
import com.rowland.xmlparser.presentation.internal.di.HasComponent;
import com.rowland.xmlparser.presentation.internal.di.components.DownloadComponent;
import com.rowland.xmlparser.presentation.internal.di.components.DaggerDownloadComponent;
import com.rowland.xmlparser.presentation.view.activity.ABaseActivity;

/**
 * Activity that shows a list of Downloads.
 */
public class DownloadListActivity extends ABaseActivity implements HasComponent<DownloadComponent>, DownloadListFragment.DownloadListListener {

    // Class log identifier
    public final static String LOG_TAG = DownloadListActivity.class.getSimpleName();

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DownloadListActivity.class);
    }

    private DownloadComponent downloadComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new DownloadListFragment(), true);
        }
    }

    private void initializeInjector() {
        this.downloadComponent = DaggerDownloadComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public DownloadComponent getComponent() {
        return downloadComponent;
    }

    @Override
    public void onDownloadClicked(String downloadModel) {
        this.navigator.navigateToDownloadDetails(this, downloadModel);
    }
}
