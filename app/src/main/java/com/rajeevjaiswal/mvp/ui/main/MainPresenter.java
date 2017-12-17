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
 * Created by janisharali on 27/01/17.
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
    public void fetchCities(final int limit, final int offset) {

        Log.d("fetch","called" + limit +"---"+ offset);
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {
                getMvpView().hideLazyLoading();
                Log.d("exception",e.getMessage());
            }
        });

            paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<List<City>>>() {
                    @Override
                    public Publisher<List<City>> apply(@NonNull Integer page) throws Exception {
                        Log.d("publisher","yes");
                        getMvpView().showLazyLoading();
                        return dataFromNetworkOrCache(page).toFlowable(BackpressureStrategy.DROP);
                    }
                })
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

        paginator.onNext(pageNumber);

        /*getmDataManager()
                .getCities(limit,offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CityResponse>() {
                    @Override
                    public void accept(CityResponse cityList) throws Exception {
                        if (cityList != null) {
                            getMvpView().updateCity(cityList.getCities());
                        }
                    }
                });*/
    }

    private Observable<List<City>> dataFromNetworkOrCache(final int page) {

        Log.d("called","dataFlow");
        return getmDataManager().getCities(limit,page*10).flatMap(new Function<CityResponse, ObservableSource<List<City>>>() {
            @Override
            public ObservableSource<List<City>> apply(CityResponse cityResponse) throws Exception {
                Log.d("response","called" + cityResponse.getCities().toString());
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
    public void onLoadNextPage() {
        pageNumber++;
        paginator.onNext(pageNumber);
    }
}