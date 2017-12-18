
package com.rajeevjaiswal.mvp.di.component;

import android.app.Application;
import android.content.Context;

import com.rajeevjaiswal.mvp.MvpApp;
import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.di.ApplicationContext;
import com.rajeevjaiswal.mvp.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rajeev on 16/12/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}