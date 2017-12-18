
package com.rajeevjaiswal.mvp.di.component;

import com.rajeevjaiswal.mvp.di.PerActivity;
import com.rajeevjaiswal.mvp.di.module.ActivityModule;
import com.rajeevjaiswal.mvp.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by rajeev on 16/12/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {


    void inject(MainActivity activity);

}
