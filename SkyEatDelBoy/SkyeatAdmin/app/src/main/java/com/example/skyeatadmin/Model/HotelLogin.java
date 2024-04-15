package com.example.skyeatadmin.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotelLogin
{
    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    private List<Data> data;
    public class Data
    {
        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("vendor_id")
        @Expose
        private String vendor_id;

        @SerializedName("temppass")
        @Expose
        private String temppass;

        public String getTemppass() {
            return temppass;
        }

        public void setTemppass(String temppass) {
            this.temppass = temppass;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVendor_id() {
            return vendor_id;
        }

        public void setVendor_id(String vendor_id) {
            this.vendor_id = vendor_id;
        }
    }

    }
