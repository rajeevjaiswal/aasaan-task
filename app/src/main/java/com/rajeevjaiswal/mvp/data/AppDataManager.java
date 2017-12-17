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

package com.rajeevjaiswal.mvp.data;


import android.content.Context;

import com.rajeevjaiswal.mvp.data.db.DbHelper;
import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.data.network.ApiHelper;
import com.rajeevjaiswal.mvp.data.network.model.CityResponse;
import com.rajeevjaiswal.mvp.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mApiHelper = apiHelper;
    }



    @Override
    public Observable<CityResponse> getCities(int limit, int offset) {
        return mApiHelper.getCities(limit,offset);
    }

    @Override
    public Observable<List<City>> getCitiesFromCache(int limit, int offset) {
        return mDbHelper.getCitiesFromCache(limit,offset);
    }

    @Override
    public Boolean saveCityList(List<City> cityList) {
        return mDbHelper.saveCityList(cityList);
    }


}
