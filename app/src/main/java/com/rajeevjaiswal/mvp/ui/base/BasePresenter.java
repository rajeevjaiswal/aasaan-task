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

package com.rajeevjaiswal.mvp.ui.base;

/**
 * Created by janisharali on 27/01/17.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.DataManager;
import com.rajeevjaiswal.mvp.data.network.model.ApiError;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Inject;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * onAttach() and onDetach(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager mDataManager;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getmDataManager() {
        return mDataManager;
    }


    @Override
    public void handleApiError(Throwable throwable) {

        try {

            if (!(throwable instanceof HttpException)) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            HttpException httpException = (HttpException) throwable;

            final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
            final Gson gson = builder.create();

            ApiError apiError = gson.fromJson(httpException.response().errorBody().string(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }
            switch (httpException.code()) {

                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_NOT_FOUND:
                default:
                    getMvpView().onError(apiError.getMessage());
            }
        } catch (IOException | JsonSyntaxException | NullPointerException e) {
            e.printStackTrace();
            getMvpView().onError(R.string.api_default_error);
        }
    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
