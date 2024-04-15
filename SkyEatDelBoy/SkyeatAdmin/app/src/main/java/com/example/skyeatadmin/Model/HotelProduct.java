package com.example.skyeatadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelProduct
{
    @SerializedName("data")
    @Expose
    public List<HotelProduct.Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("price")
        @Expose
        private String price;

        @SerializedName("sale_price")
        @Expose
        private String sale_price;

        @SerializedName("default_price")
        @Expose
        private String default_price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getDefault_price() {
            return default_price;
        }

        public void setDefault_price(String default_price) {
            this.default_price = default_price;
        }
    }
}
