package com.rajeevjaiswal.mvp.ui.main;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView,CityAdapter.Callback {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    CityAdapter mCityAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.city_recycler_view)
    RecyclerView mRecyclerView;

    private int offset = 0;
    private int limit = 10;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
        mCityAdapter.setCallback(this);
        setUp();
    }



    @Override
    protected void setUp() {

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCityAdapter);
        setUpLoadMoreListener();
        mPresenter.fetchCities(limit,offset);
    }

    /**
     * setting listener to get callback for load more
     */
    private void setUpLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = mLayoutManager
                        .findLastVisibleItemPosition();
                if (!loading
                        && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {

                    loading = true;
                    mPresenter.onLoadNextPage();
                }
            }
        });
    }
    @Override
    public void updateCity(List<City> cityList) {
        mCityAdapter.addItems(cityList);
    }

    @Override
    public void showLazyLoading() {
        loading = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLazyLoading() {
        loading = false;
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onCityClicked(int position) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}

