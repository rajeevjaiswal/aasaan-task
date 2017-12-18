
package com.rajeevjaiswal.mvp.ui.main;

import android.support.annotation.NonNull;
import android.util.Log;

import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.data.network.model.CityResponse;
import com.rajeevjaiswal.mvp.ui.base.BasePresenter;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by rajeev on 16/12/17.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    private int pageNumber = 0;
    private int limit = 10;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void fetchCities() {

        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {
                getMvpView().hideLazyLoading();
                Log.d("exception",e.getMessage());
            }
        });

           /* paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<List<City>>>() {
                    @Override
                    public Publisher<List<City>> apply(@NonNull Integer page) throws Exception {
                        Log.d("publisher","yes");
                        getMvpView().showLazyLoading();
                        return dataFromNetworkOrCache(pageNumber).toFlowable(BackpressureStrategy.DROP);
                    }
                })*/
                  dataFromNetworkOrCache(pageNumber)
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<City>>() {
                            @Override
                            public void accept(List<City> cityList) throws Exception {

                                getMvpView().hideLazyLoading();
                                getMvpView().updateCity(cityList);
                            }


                        });
//        getCompositeDisposable().add(disposable);

//        paginator.onNext(pageNumber);


    }

    private Observable<List<City>> dataFromNetworkOrCache(final int page) {

//        Log.d("called","dataFlow");
        return getmDataManager().getCities(limit,page*10).flatMap(new Function<CityResponse, ObservableSource<List<City>>>() {
            @Override
            public ObservableSource<List<City>> apply(CityResponse cityResponse) throws Exception {
//                Log.d("response","called" + cityResponse.getCities().toString());
                getmDataManager().saveCityList(cityResponse.getCities());
                return Observable.fromArray(cityResponse.getCities());
            }
        }).publish( new Function<Observable<List<City>>, ObservableSource<List<City>>>() {
                            @Override
                            public ObservableSource<List<City>> apply(Observable<List<City>> network) throws Exception {
                                return Observable.merge(
                                        network,
                                        getmDataManager().getCitiesFromCache(limit,page * 10).takeUntil(network)
                                ).distinct();
                            }
                        }).subscribeOn(Schedulers.io());
    }

    @Override
    public void onLoadNextPage(int page) {
//        Log.d("page","next");
        pageNumber = page;
//        paginator.onNext(pageNumber);
        fetchCities();
    }
}