
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
//                Log.d("cityFRomDB","called" );

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
