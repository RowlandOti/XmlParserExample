package com.rowland.xmlparser.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import com.rowland.xmlparser.presentation.downloadfeature.view.activity.DownloadDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }


    /**
     * Goes to the download details screen.
     *
     * @param context A Context needed to open the destiny activity.
     * @param downloadKey   A Download id.
     */
    public void navigateToDownloadDetails(Context context, String downloadKey) {
        if (context != null) {
            Intent intentToLaunch = DownloadDetailsActivity.getCallingIntent(context, downloadKey);
            context.startActivity(intentToLaunch);
        }
    }
}
