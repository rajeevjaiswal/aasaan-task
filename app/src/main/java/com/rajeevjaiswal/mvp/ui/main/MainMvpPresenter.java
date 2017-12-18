
package com.rajeevjaiswal.mvp.ui.main;


import com.rajeevjaiswal.mvp.di.PerActivity;
import com.rajeevjaiswal.mvp.ui.base.MvpPresenter;

/**
 * Created by rajeev on 16/12/17.
 */

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    void fetchCities();

    void onLoadNextPage(int page);

}
