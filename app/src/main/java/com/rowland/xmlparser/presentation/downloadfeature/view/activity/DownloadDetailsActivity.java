package com.rowland.xmlparser.presentation.downloadfeature.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rowland.xmlparser.R;
import com.rowland.xmlparser.presentation.downloadfeature.view.fragment.DownloadDetailsFragment;
import com.rowland.xmlparser.presentation.internal.di.HasComponent;
import com.rowland.xmlparser.presentation.internal.di.components.DaggerDownloadComponent;
import com.rowland.xmlparser.presentation.internal.di.components.DownloadComponent;
import com.rowland.xmlparser.presentation.internal.di.modules.DownloadModule;
import com.rowland.xmlparser.presentation.view.activity.ABaseActivity;

/**
 * Activity that shows details of a certain download.
 */
public class DownloadDetailsActivity extends ABaseActivity implements HasComponent<DownloadComponent> {

  private static final String INTENT_EXTRA_PARAM_BID_ID = "com.rowland.xmlparser.INTENT_PARAM_BID_ID";
  private static final String INSTANCE_STATE_PARAM_BID_ID = "com.rowland.xmlparser.STATE_PARAM_BID_ID";

  public static Intent getCallingIntent(Context context, String downloadKey) {
    Intent callingIntent = new Intent(context, DownloadDetailsActivity.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_BID_ID, downloadKey);
    return callingIntent;
  }

  private String downloadKey;
  private DownloadComponent downloadComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_download_details);

    this.initializeActivity(savedInstanceState);
    this.initializeInjector();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
      outState.putString(INSTANCE_STATE_PARAM_BID_ID, this.downloadKey);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
      this.downloadKey = getIntent().getStringExtra(INTENT_EXTRA_PARAM_BID_ID);
      addFragment(R.id.fragmentContainer, new DownloadDetailsFragment(), true);
    } else {
      this.downloadKey = savedInstanceState.getString(INSTANCE_STATE_PARAM_BID_ID);
    }
  }

  private void initializeInjector() {
    this.downloadComponent = DaggerDownloadComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .downloadModule(new DownloadModule(this.downloadKey))
        .build();
  }

  @Override public DownloadComponent getComponent() {
    return downloadComponent;
  }
}
