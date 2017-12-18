
package com.rajeevjaiswal.mvp;

import android.app.Application;
import android.content.Context;

import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.di.component.ApplicationComponent;
import com.rajeevjaiswal.mvp.di.component.DaggerApplicationComponent;
import com.rajeevjaiswal.mvp.di.module.ApplicationModule;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rajeev on 16/12/17.
 */

public class MvpApp extends Application {

    @Inject
    DataManager mDataManager;

    @Inject
    CalligraphyConfig calligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    public static MvpApp get(Context context) {
        return (MvpApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        CalligraphyConfig.initDefault(calligraphyConfig);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
