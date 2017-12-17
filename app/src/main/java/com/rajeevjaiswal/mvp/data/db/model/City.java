package com.rajeevjaiswal.mvp.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by rajeevjaiswal on 16/12/17.
 */

@Entity(nameInDb = "cities")
public class City {

    @Expose
    @SerializedName("id")
    @Id
    private Long id;

    @Expose
    @SerializedName("name")
    @Property(nameInDb = "name")
    private String cityName;



    @Expose
    @SerializedName("slug")
    @Property(nameInDb = "slug")
    private String slug;


    @Generated(hash = 1942962101)
    public City(Long id, String cityName, String slug) {
        this.id = id;
        this.cityName = cityName;
        this.slug = slug;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
