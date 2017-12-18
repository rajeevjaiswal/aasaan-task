
package com.rajeevjaiswal.mvp.data;


import android.content.Context;
import android.util.Log;

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
 * Created by rajeev on 16/12/17.
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
        Log.d("cities","called");
        return mDbHelper.getCitiesFromCache(limit,offset);
    }

    @Override
    public Boolean saveCityList(List<City> cityList) {
        return mDbHelper.saveCityList(cityList);
    }


}
