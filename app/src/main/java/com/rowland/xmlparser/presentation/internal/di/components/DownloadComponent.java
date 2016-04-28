package com.rowland.xmlparser.presentation.internal.di.components;

import com.rowland.xmlparser.presentation.internal.di.PerActivity;
import com.rowland.xmlparser.presentation.internal.di.modules.ActivityModule;
import com.rowland.xmlparser.presentation.internal.di.modules.DownloadModule;
import com.rowland.xmlparser.presentation.downloadfeature.view.fragment.DownloadDetailsFragment;
import com.rowland.xmlparser.presentation.downloadfeature.view.fragment.DownloadListFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects download specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DownloadModule.class})
public interface DownloadComponent extends ActivityComponent {
  void inject(DownloadListFragment downloadListFragment);
  void inject(DownloadDetailsFragment downloadDetailsFragment);
}
