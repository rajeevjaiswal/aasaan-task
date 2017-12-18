
package com.rajeevjaiswal.mvp.ui.base;

/**
 * Created by rajeev on 16/12/17.
 */

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(Throwable throwable);

}
