
package com.rajeevjaiswal.mvp.di.module;

import android.app.Application;
import android.content.Context;

import com.rajeevjaiswal.mvp.BuildConfig;
import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.AppDataManager;
import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.db.AppDbHelper;
import com.rajeevjaiswal.mvp.data.db.DbHelper;
import com.rajeevjaiswal.mvp.data.network.ApiCall;
import com.rajeevjaiswal.mvp.data.network.ApiHelper;
import com.rajeevjaiswal.mvp.data.network.AppApiHelper;
import com.rajeevjaiswal.mvp.di.ApiInfo;
import com.rajeevjaiswal.mvp.di.ApplicationContext;
import com.rajeevjaiswal.mvp.di.DatabaseInfo;
import com.rajeevjaiswal.mvp.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by rajeev on 16/12/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }
    

    @Provides
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }


    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiCall provideApiCall() {
        return ApiCall.Factory.create();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
