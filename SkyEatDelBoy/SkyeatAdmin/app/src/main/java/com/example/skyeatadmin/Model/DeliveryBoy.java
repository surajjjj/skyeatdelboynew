package com.example.skyeatadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryBoy
{
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private List<Data> data;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data
    {

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("zone_id")
        @Expose
        private String zone_id;

        @SerializedName("last_name")
        @Expose
        private String last_name;

        @SerializedName("driver_id")
        @Expose
        private String driver_id;

        @SerializedName("phone")
        @Expose
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @SerializedName("email")
        @Expose
        private String email;

        @SerializedName("password")
        @Expose
        private String password;

        @SerializedName("passtemp")
        @Expose
        private String passtemp;

        @SerializedName("payment_status")
        @Expose
        private String payment_status;

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getPasstemp() {
            return passtemp;
        }

        public void setPasstemp(String passtemp) {
            this.passtemp = passtemp;
        }

        @SerializedName("gullak")
        @Expose
        private String gullak;

        public String getGullak() {
            return gullak;
        }

        public void setGullak(String gullak) {
            this.gullak = gullak;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        @SerializedName("interest")
        @Expose
        private String interest;

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
        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getZone_id() {
            return zone_id;
        }

        public void setZone_id(String zone_id) {
            this.zone_id = zone_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
    }
}
