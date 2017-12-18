
package com.rajeevjaiswal.mvp.data.db;

import com.rajeevjaiswal.mvp.data.db.model.City;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by janisharali on 08/12/16.
 */

public interface DbHelper {


    Observable<List<City>> getCitiesFromCache(int limit, int offset);

    Boolean saveCityList(List<City> cityList);

}
