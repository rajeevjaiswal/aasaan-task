package com.rajeevjaiswal.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rajeevjaiswal.mvp.data.db.model.City;

import java.util.List;

/**
 * Created by rajeevjaiswal on 16/12/17.
 */

public class CityResponse {

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Expose
    @SerializedName("objects")
    private List<City> cities;

}
