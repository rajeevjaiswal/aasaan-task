package com.rajeevjaiswal.mvp.ui.main;

import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.ui.base.MvpView;

import java.util.List;

/**
 * Created by rajeevjaiswal on 16/12/17.
 */

public interface MainMvpView extends MvpView {

    void updateCity(List<City> cityList);
    void showLazyLoading();
    void hideLazyLoading();
}
