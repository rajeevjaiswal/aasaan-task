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

package com.rajeevjaiswal.mvp.data.db;

import android.util.Log;

import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.data.db.model.DaoMaster;
import com.rajeevjaiswal.mvp.data.db.model.DaoSession;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession mDaoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        mDaoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }


    @Override
    public Observable<List<City>> getCitiesFromCache(final int limit, final int offset) {
        return Observable.fromCallable(new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                Log.d("cityFRomDB","called" );

                return mDaoSession.getCityDao().queryBuilder().limit(limit).offset(offset).list();
            }
        });
    }

    @Override
    public Boolean saveCityList(final List<City> cityList) {


//            Log.d("data",""+cityList.get(0).getCityName());
            mDaoSession.getCityDao().insertOrReplaceInTx(cityList);
            return true;


    }


}
