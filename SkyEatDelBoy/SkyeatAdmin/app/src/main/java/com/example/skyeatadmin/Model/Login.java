package com.example.skyeatadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login
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

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("wallet")
        @Expose
        private String wallet;

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getZone_name() {
            return zone_name;
        }

        public void setZone_name(String zone_name) {
            this.zone_name = zone_name;
        }

        @SerializedName("zone_name")
        @Expose
        private String zone_name;

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
    }
}
