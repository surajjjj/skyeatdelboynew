package com.example.skyeatadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Area
{
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("zone_id")
        @Expose
        private String zone_id;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("order_charg")
        @Expose
        private String order_charg;

        public String getOrder_charg() {
            return order_charg;
        }

        public void setOrder_charg(String order_charg) {
            this.order_charg = order_charg;
        }

        public String getZone_id() {
            return zone_id;
        }

        public void setZone_id(String zone_id) {
            this.zone_id = zone_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
