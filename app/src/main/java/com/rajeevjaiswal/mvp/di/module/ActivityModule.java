/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.rajeevjaiswal.mvp.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.di.ActivityContext;
import com.rajeevjaiswal.mvp.di.PerActivity;
import com.rajeevjaiswal.mvp.ui.main.CityAdapter;
import com.rajeevjaiswal.mvp.ui.main.MainMvpPresenter;
import com.rajeevjaiswal.mvp.ui.main.MainMvpView;
import com.rajeevjaiswal.mvp.ui.main.MainPresenter;


import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }


    @Provides
    LinearLayoutManager provideLayoutManager() {
        return new LinearLayoutManager(activity);
    }


    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMain2Presenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    CityAdapter provideCityAdapter() {return new CityAdapter(new ArrayList<City>());}

}
