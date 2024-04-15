package com.example.skyeatadmin.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GAllOrder {
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
        @SerializedName("sale_id")
        @Expose
        private String sale_id;

        @SerializedName("sale_code")
        @Expose
        private String sale_code;

        @SerializedName("buyer")
        @Expose
        private String buyer;

        @SerializedName("product_details")
        @Expose
        public String product_details;

        @SerializedName("shipping_address")
        @Expose
        public String shipping_address;

        @SerializedName("payment_details")
        @Expose
        public String payment_details;

        @SerializedName("payment_type")
        @Expose
        public String payment_type;

        @SerializedName("item_total")
        @Expose
        public String item_total;

        @SerializedName("grand_total")
        @Expose
        public String grand_totals;

        @SerializedName("delivery_status")
        @Expose
        public String delivery_status;

        @SerializedName("delivary_datetime")
        @Expose
        public String delivary_datetime;

        @SerializedName("status")
        @Expose
        public String status;

        @SerializedName("delivery_assigned")
        @Expose
        public String delivery_assigned;

        @SerializedName("zone_id")
        @Expose
        public String zone_id;

        @SerializedName("vendor")
        @Expose
        public String vendor;

        @SerializedName("delivery_state")
        @Expose
        public String delivery_state;

        public String getDelivery_state() {
            return delivery_state;
        }

        public void setDelivery_state(String delivery_state) {
            this.delivery_state = delivery_state;
        }

        public String getDist_manager_payment_status() {
            return dist_manager_payment_status;
        }

        public void setDist_manager_payment_status(String dist_manager_payment_status) {
            this.dist_manager_payment_status = dist_manager_payment_status;
        }

        @SerializedName("dist_manager_payment_status")
        @Expose
        public String dist_manager_payment_status;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getZone_id() {
            return zone_id;
        }

        public void setZone_id(String zone_id) {
            this.zone_id = zone_id;
        }

        public String getDelivery_assigned() {
            return delivery_assigned;
        }

        public void setDelivery_assigned(String delivery_assigned) {
            this.delivery_assigned = delivery_assigned;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDelivary_datetime() {
            return delivary_datetime;
        }

        public void setDelivary_datetime(String delivary_datetime) {
            this.delivary_datetime = delivary_datetime;
        }

        public String getShipping_address() {
            return shipping_address;
        }

        public void setShipping_address(String shipping_address) {
            this.shipping_address = shipping_address;
        }

        public String getPayment_details() {
            return payment_details;
        }

        public void setPayment_details(String payment_details) {
            this.payment_details = payment_details;
        }

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getItem_total() {
            return item_total;
        }

        public void setItem_total(String item_total) {
            this.item_total = item_total;
        }

        public String getGrand_totals() {
            return grand_totals;
        }

        public void setGrand_totals(String grand_totals) {
            this.grand_totals = grand_totals;
        }

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getSale_id() {
            return sale_id;
        }

        public void setSale_id(String sale_id) {
            this.sale_id = sale_id;
        }

        public String getSale_code() {
            return sale_code;
        }

        public void setSale_code(String sale_code) {
            this.sale_code = sale_code;
        }

        public String getBuyer() {
            return buyer;
        }

        public void setBuyer(String buyer) {
            this.buyer = buyer;
        }

        public String getProduct_details() {
            return product_details;
        }

        public void setProduct_details(String product_details) {
            this.product_details = product_details;
        }
    }
}
