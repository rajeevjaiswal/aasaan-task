/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.rajeevjaiswal.mvp;


import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.network.model.CityResponse;
import com.rajeevjaiswal.mvp.ui.main.MainMvpView;
import com.rajeevjaiswal.mvp.ui.main.MainPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMockMainMvpView;
    @Mock
    DataManager mMockDataManager;

    private MainPresenter<MainMvpView> mMainPresenter;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mMainPresenter = new MainPresenter<>(
            mMockDataManager);
        mMainPresenter.onAttach(mMockMainMvpView);
    }

    @Test
    public void testCityDataFromServer() {


        CityResponse cityResponse = new CityResponse();

        doReturn(Observable.just(cityResponse))
                .when(mMockDataManager)
                .getCities(10,0);

        mMainPresenter.fetchCities();
        mTestScheduler.triggerActions();

        verify(mMockMainMvpView).showLazyLoading();
        verify(mMockMainMvpView).showLazyLoading();
    }


    @After
    public void tearDown() throws Exception {
        mMainPresenter.onDetach();
    }

}