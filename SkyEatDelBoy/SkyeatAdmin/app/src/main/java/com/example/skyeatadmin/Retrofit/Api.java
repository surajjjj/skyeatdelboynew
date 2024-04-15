package com.example.skyeatadmin.Retrofit;

import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Model.Hotel;
import com.example.skyeatadmin.Model.HotelLogin;
import com.example.skyeatadmin.Model.Login;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.Model.Transition;
import com.example.skyeatadmin.Model.Version;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

//    @GET("actions.php")
//    Call<Results> getVillageData(@Query("action") String action);

    @GET("actions.php")
    Call<GAllOrder> getOrder(@Query("action") String action);
    @GET("actions.php")

    Call<Version> getDeliveryBoyVersion(@Query("action") String action);

    @GET("actions.php")
    Call<GAllOrder> getHotelOrders(@Query("action") String action,
                                          @Query("vendor") String vendor);

    @GET("actions.php")
    Call<Sample> updateRestaurantProductPrice(@Query("action") String action,
                                            @Query("vendor") String vendor,
                                            @Query("percentage")int percentage);
    @GET("actions.php")
    Call<GAllOrder> getDeliveryBoyOrders(@Query("action") String action,
                                   @Query("delivery_assigned") String delivery_assigned);



    @GET("actions.php")
    Call<DeliveryBoy> getDeliveryBoy(@Query("action") String action);

    @GET("actions.php")
    Call<DeliveryBoy> getDeliveryBoyGullak(@Query("action") String action,
                                           @Query("driver_id") String driver_id);

    @GET("actions.php")
    Call<Transition> getDeliveryBoyTransition(@Query("action") String action,
                                          @Query("driver_id") String driver_id);

    @GET("actions.php")
    Call<Sample> updateDeliveryBoyPaymentSatus(@Query("action") String action,
                                           @Query("delivery_assigned") String delivery_assigned);
    @GET("actions.php")
    Call<Sample> updateDeliveryBoyEarnStatus(@Query("action") String action,
                                               @Query("delivery_assigned") String delivery_assigned);

    @GET("actions.php")
    Call<Area> getZone(@Query("action") String action);

    @GET("actions.php")
    Call<Area> getOrderCharge(@Query("action") String action,
                              @Query("zone_id") String zone_id);



    @GET("actions.php")
    Call<Login> getLogin(@Query("action") String action);

    @GET("actions.php")
    Call<HotelLogin> getHotelLogin(@Query("action") String action,
                                   @Query("email") String email,
                                   @Query("temppass") String temppass);
    @GET("actions.php")
    Call<DeliveryBoy> getDeliveryBoyLogin(@Query("action") String action,
                                          @Query("email") String email,
                                          @Query("passtemp") String passtemp);

    @GET("actions.php")
    Call<Hotel> getHotel(@Query("action") String action);

    @GET("actions.php")
    Call<Sample> insertDelBoyPayment(@Query("action") String action,
                             @Query("driver_id") String driver_id,
                             @Query("driver_name") String driver_name,
                             @Query("date") String date,
                             @Query("paymet_amount") String paymet_amount);



    @GET("actions.php")
    Call<Sample> acceptOrder(@Query("action") String action,
//                             @Query("status") String status,
                             @Query("delivery_assigned") String delivery_assigned,
                             @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> acceptOrderByDeliveryBoy(@Query("action") String action,
                             @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> rejectOrderByDeliveryBoy(@Query("action") String action,
                                          @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> pickupOrderByDeliveryBoy(@Query("action") String action,
                                          @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> deliverOrderByDeliveryBoy(@Query("action") String action,
                                          @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> rejectOrder(@Query("action") String action,
                             @Query("status") String status,
                             @Query("sale_id") String sale_id);

    @GET("actions.php")
    Call<Sample> cancelOrder(@Query("action") String action,
                             @Query("order_id") String order_id,
                             @Query("zone_id") String zone_id,
                             @Query("manager_name") String manager_name,
                             @Query("vendor_id") String vendor_id,
                             @Query("resaon") String resaon);

    @GET("actions.php")
    Call<GAllOrder> getDeliveryBoyOrder(@Query("action") String action,
                                        @Query("delivery_assigned") String delivery_assigned,
                                        @Query("from_date") String from_date,
                                        @Query("to_date") String to_date);

    @GET("actions.php")
    Call<GAllOrder> getHotelOrder(@Query("action") String action,
                                        @Query("vendor") String vendor,
                                        @Query("from_date") String from_date,
                                        @Query("to_date") String to_date);

    @GET("actions.php")
    Call<Sample> setWallet(@Query("action") String action,
                           @Query("wallet") String wallet,
                           @Query("id") String id);

    @GET("actions.php")
    Call<Sample> updateWalletStatus(@Query("action") String action,
                           @Query("zone_id") String zone_id);

    @GET("actions.php")
    Call<Sample> deleteItem(@Query("action") String action,
                             @Query("position") int position,
                             @Query("grand_total") String grand_total,
                             @Query("sale_id") String sale_id);

}