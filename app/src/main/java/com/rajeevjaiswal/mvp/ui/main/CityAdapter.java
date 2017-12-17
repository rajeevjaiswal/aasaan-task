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

package com.rajeevjaiswal.mvp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajeevjaiswal.mvp.R;
import com.rajeevjaiswal.mvp.data.db.model.City;
import com.rajeevjaiswal.mvp.ui.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajeev on 16-12-2017.
 */

public class CityAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Callback mCallback;
    private List<City> mCityList;

    public CityAdapter(List<City> cityList) {
        mCityList = cityList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_list, parent, false));
    }



    @Override
    public int getItemCount() {

      return mCityList.size();

    }

    public void addItems(List<City> cityList) {
        mCityList.addAll(cityList);
        notifyDataSetChanged();
    }

    public void clear() {
        mCityList.clear();
//        notifyDataSetChanged();
    }

    public interface Callback {
        void onCityClicked(int position);
    }

    public class ViewHolder extends BaseViewHolder {



        @BindView(R.id.tv_city_name)
        TextView titleTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }


        public void onBind(final int position) {
            super.onBind(position);

            final City city = mCityList.get(position);


            if (city.getCityName() != null) {
                titleTextView.setText(city.getCityName());
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallback != null)
                        mCallback.onCityClicked(position);
                }

            });
        }
    }


}
