
package com.rajeevjaiswal.mvp.data.network;

import com.rajeevjaiswal.mvp.data.network.model.CityResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by janisharali on 28/01/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiCall mApiCall;

    @Inject
    public AppApiHelper( ApiCall apiCall) {
        mApiCall = apiCall;
    }


    @Override
    public Observable<CityResponse> getCities(int limit, int offset) {
        return mApiCall.getCities(limit,offset);
    }
}

