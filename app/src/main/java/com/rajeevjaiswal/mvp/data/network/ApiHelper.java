
package com.rajeevjaiswal.mvp.data.network;

import com.rajeevjaiswal.mvp.data.network.model.CityResponse;

import io.reactivex.Observable;

/**
 * Created by rajeev on 16/12/17.
 */

public interface ApiHelper {


    Observable<CityResponse> getCities(int limit, int offset);
}
