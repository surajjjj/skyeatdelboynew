package com.example.skyeatadmin.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transition
{
    @SerializedName("result")
    @Expose
    public Boolean result;

    @SerializedName("data")
    @Expose
    public List<Data> data;

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
        @SerializedName("driver_name")
        @Expose
        public String driver_name;

        @SerializedName("paymet_amount")
        @Expose
        public String paymet_amount;

        @SerializedName("date")
        @Expose
        public String date;

        @SerializedName("payment_status")
        @Expose
        public String payment_status;

        public String getDriver_name() {
            return driver_name;
        }

        public void setDriver_name(String driver_name) {
            this.driver_name = driver_name;
        }

        public String getPaymet_amount() {
            return paymet_amount;
        }

        public void setPaymet_amount(String paymet_amount) {
            this.paymet_amount = paymet_amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }
    }
}
