



package com.food.food_order_Delivaryboy.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 22/09/2017.
 */
public class DBHelper  extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "student", null, 10677);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tbl_login ( id varchar, name varchar, mobile varchar, email varchar, address varchar, pincode varchar, password varchar,village varchar)");
        db.execSQL("create table if not exists tbl_cart (product_id varchar, product_name, product_qty varchar, product_price_id varchar, product_unit_qty varchar, product_unit varchar, product_price varchar, product_image varchar)");
        // db.execSQL("create table if not exists tbl_new_cart (menu_id varchar, menu_name, menu_image varchar, menu_prices varchar,menu_qty varchar)");
        db.execSQL("create table if not exists tbl_new_cart (menu_id varchar, menu_name, menu_image varchar, menu_prices varchar,menu_qty varchar,menu_category_id varchar,server_delivary_charges varchar,category_name varchar)");
        db.execSQL("create table if not exists tbl_grocary (gmenu_id varchar, gcategory_id varchar, gmenu_photo varchar, gmenu_name varchar,gmenu_qty varchar,gmenu_price varchar,gmenu_delivarycharges varchar,gmenu_unit varchar,gmenu_status varchar,gmenu_discount varchar)");
        db.execSQL("create table if not exists sub_catagory_search (category_id,subcategory_id,category_name,category_image,status)");
        db.execSQL("create table if not exists tbl_delivaryboy_login (id,name,mobile,password,aadhar,address,Drivinglincesno,BankAccountno,Bankifsccode,BankAccountholdername,BranchName,bike_no)");
        db.execSQL("create table if not exists HotelAdminLogin (id,city)");
        db.execSQL("create table if not exists disptach_order (del_boy_id,dispatch_id,order_id,gmenu_id,user_id,gmenu_name,gmenu_qty,gmenu_unit,gmenu_price,delivary_charge,gmenu_total,gmenu_ordertype,customer_name,customer_mobile,customer_address,order_date,gmenu_instruction,del_boy_name,del_boy_mobile,del_boy_bikeno,status)");
        db.execSQL("create table if not exists getdelivaryboystatus (order_id,status)");
        db.execSQL("create table if not exists delivary_charges (id,delivary_charges)");
        db.execSQL("create table if not exists status_tbl (id,delivary_charges)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tbl_login");
        db.execSQL("drop table if exists tbl_cart");
        db.execSQL("drop table if exists tbl_new_cart");
        db.execSQL("drop table if exists tbl_grocary");
        db.execSQL("drop table if exists insertDelivaryLogin");
        db.execSQL("drop table if exists disptach_order");
        db.execSQL("drop table if exists getdelivaryboystatus");
        db.execSQL("drop table if exists delivary_charges");
        onCreate(db);
    }

    public boolean insertLogin(String id, String name, String mobile, String email, String address, String pincode, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("mobile", mobile);
        cv.put("email", email);
        cv.put("address", address);
        cv.put("pincode", pincode);
        //  cv.put("village",village);

        cv.put("password", password);
        // cv.put("client_type",client_type);
        long result = db.insert("tbl_login", null, cv);
        if (result != -1) {
            return true;
        }
        return false;
    }
    public boolean insertDelivaryLogin(String id,String name,String mobile,String password,String aadhar,String address,String Drivinglincesno,String BankAccountno,String Bankifsccode,String BankAccountholdername,String BranchName,String bike_no) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("mobile", mobile);
        cv.put("password", password);
        cv.put("aadhar", aadhar);
        cv.put("address", address);
        cv.put("Drivinglincesno", Drivinglincesno);
        cv.put("BankAccountno", BankAccountno);
        cv.put("Bankifsccode", Bankifsccode);
        cv.put("BankAccountholdername", BankAccountholdername);
        cv.put("BranchName", BranchName);
        cv.put("bike_no", bike_no);
        long result = db.insert("tbl_delivaryboy_login", null, cv);
        if (result != -1) {
            return true;
        }
        return false;
    }
    public boolean insertHotelLogin(String id,String city) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("city", city);


        long result = db.insert("HotelAdminLogin", null, cv);
        if (result != -1) {
            return true;
        }
        return false;
    } public boolean status_tbl(String order_id,String order_status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("order_id", order_id);
        cv.put("order_status", order_status);


        long result = db.insert("status", null, cv);
        if (result != -1) {
            return true;
        }
        return false;
    }
    public Cursor getstatus_tbl() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from status", null);
    }

    public boolean isLoggedInDelivary() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_delivaryboy_login", null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLoggedIn() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HotelAdminLogin", null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public String getId() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_login", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("id"));
        }
        return "";
    }

    public String getDelivaryboyId() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_delivaryboy_login", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("id"));
        }
        return "";
    }
    public String getHotelAdminId() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HotelAdminLogin", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("category_id"));
        }
        return "";
    }

    public String gethotelcity() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from HotelAdminLogin", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("city"));
        }
        return "";
    }



    public String getClientType() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_login", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("client_type"));
        }
        return "client";
    }

    public String getPincode() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_login", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("pincode"));
        }
        return "";
    }

    public String getCustomerName() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_login", null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("name"));
        }
        return "";
    }

    public Cursor getProfile() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from HotelAdminLogin", null);
    }

    public Cursor DelivaryboygetProfile() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from tbl_delivaryboy_login", null);
    }
    public Cursor HotelAdmingetProfile() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from HotelAdminLogin", null);
    }

    public boolean updateProfile(String name, String email, String address, String pincode) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("address", address);
        cv.put("pincode", pincode);
        long result = db.update("tbl_login", cv, null, null);
        if (result == 1) {
            return true;
        }
        return false;
    }

    public boolean updateMobile(String client_mobile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("mobile", client_mobile);
        long result = db.update("tbl_login", cv, null, null);
        if (result == 1) {
            return true;
        }
        return false;
    }

    public boolean updatePassword(String client_password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("password", client_password);
        long result = db.update("tbl_login", cv, null, null);
        if (result == 1) {
            return true;
        }
        return false;
    }

    public void logout() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_login", null, null);
    } public void logoutdelivaryboy() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_delivaryboy_login", null, null);
    }
    public void logoutHotelAdmin() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("HotelAdminLogin", null, null);
    }

    public void insertIntoCart(String product_id, String product_name, String product_qty, String product_price, String product_unit, String product_unit_qty, String product_price_id, String product_image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("product_id", product_id);
        cv.put("product_name", product_name);
        cv.put("product_qty", product_qty);
        cv.put("product_price", product_price);
        cv.put("product_unit", product_unit);
        cv.put("product_unit_qty", product_unit_qty);
        cv.put("product_price_id", product_price_id);
        cv.put("product_image", product_image);
        db.insert("tbl_cart", null, cv);
    }
    public void Insert_into_status(String order_id, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("order_id", order_id);
        cv.put("status", status);

        db.insert("getdelivaryboystatus", null, cv);
    }
    public void removeFromstatus(String menu_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_new_cart", "menu_id=?", new String[]{menu_id});
    }
    public Cursor getDelivaryBoyStatus() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from getdelivaryboystatus", null);
    }
//    public boolean getDelivaryBoyStatus(String menu_id) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from tbl_grocary where menu_id=?", new String[]{menu_id});
//        if (cursor.moveToFirst()) {
//            return true;
//        }
//        return false;
//    }

    public void insertIntodisptach_order(String del_boy_id, String dispatch_id, String order_id, String gmenu_id, String user_id, String gmenu_name, String gmenu_qty, String gmenu_unit, String gmenu_price, String delivary_charge, String gmenu_total, String gmenu_ordertype, String customer_name, String customer_mobile, String customer_address, String order_date, String gmenu_instruction, String del_boy_name, String del_boy_mobile, String del_boy_bikeno, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("del_boy_id", del_boy_id);
        cv.put("dispatch_id", dispatch_id);
        cv.put("order_id", order_id);
        cv.put("gmenu_id", gmenu_id);
        cv.put("user_id", user_id);
        cv.put("gmenu_name", gmenu_name);
        cv.put("gmenu_qty", gmenu_qty);
        cv.put("gmenu_unit", gmenu_unit);
        cv.put("gmenu_price", gmenu_price);
        cv.put("delivary_charge", delivary_charge);
        cv.put("gmenu_total", gmenu_total);
        cv.put("gmenu_ordertype", gmenu_ordertype);
        cv.put("customer_name", customer_name);
        cv.put("customer_mobile", customer_mobile);
        cv.put("customer_address", customer_address);
        cv.put("order_date", order_date);
        cv.put("gmenu_instruction", gmenu_instruction);
        cv.put("del_boy_name", del_boy_name);
        cv.put("del_boy_mobile", del_boy_mobile);
        cv.put("del_boy_bikeno", del_boy_bikeno);
        cv.put("status", status);
        db.insert("disptach_order", null, cv);
    }
    public void insertIntoNewCart(String menu_id, String menu_name, String menu_image, String menu_prices, String menu_qty, String menu_category_id, String server_delivary_charges, String category_name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("menu_id", menu_id);
        cv.put("menu_name", menu_name);
        cv.put("menu_image", menu_image);
        cv.put("menu_prices", menu_prices);
        cv.put("menu_qty", menu_qty);
        cv.put("menu_category_id", menu_category_id);
        cv.put("server_delivary_charges", server_delivary_charges);
        cv.put("category_name", category_name);
        db.insert("tbl_new_cart", null, cv);
    }

    public void updateCart(String menu_id, String menu_qty) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("menu_id", menu_id);
        cv.put("menu_qty", menu_qty);
        db.update("tbl_new_cart", cv, "menu_id=?", new String[]{menu_qty});
    }

    public void removeFromCart(String menu_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_new_cart", "menu_id=?", new String[]{menu_id});
    }

    public void emptyCart() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_new_cart", null, null);
    }

    public boolean existInCart(String menu_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_new_cart where menu_id=?", new String[]{menu_id});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public String getQty(String menu_qty) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_new_cart where menu_qty=?", new String[]{menu_qty});
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("menu_qty"));
        }
        return "0";
    }

    public Cursor getCartDetails() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from tbl_new_cart", null);
    }

    public void updateCartt(String menu_id, String menu_price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("menu_id", menu_id);
        cv.put("menu_prices", menu_price);
        // cv.put("menu_qty", menu_qty);
        db.update("tbl_new_cart", cv, "menu_id=?", new String[]{menu_id});
    }
////all grocary


    public void tbl_grocary(String gmenu_id, String gcategory_id, String gmenu_photo, String gmenu_name, String gmenu_qty, String gmenu_price, String gmenu_delivarycharges, String gmenu_unit, String gmenu_status, String gmenu_discount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("gmenu_id", gmenu_id);
        cv.put("gcategory_id", gcategory_id);
        cv.put("gmenu_photo", gmenu_photo);
        cv.put("gmenu_name ", gmenu_name);
        cv.put("gmenu_qty", gmenu_qty);
        cv.put("gmenu_price", gmenu_price);
        cv.put("gmenu_delivarycharges", gmenu_delivarycharges);
        cv.put("gmenu_unit", gmenu_unit);
        cv.put("gmenu_status", gmenu_status);
        cv.put("gmenu_discount", gmenu_discount);
        db.insert("tbl_grocary", null, cv);
    }


    public void updateCartgrocary(String gmenu_id, String gmenu_qty) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("gmenu_id", gmenu_id);
        cv.put("gmenu_qty", gmenu_qty);
        db.update("tbl_grocary", cv, "gmenu_id=?", new String[]{gmenu_qty});
    }

    public void removeFromCartgrocary(String gmenu_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_grocary", "gmenu_id=?", new String[]{gmenu_id});
    }

    public void emptyCartgrocary() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tbl_grocary", null, null);
    }

    public boolean existInCartgrocary(String menu_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_grocary where menu_id=?", new String[]{menu_id});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public String getQtygrocary(String gmenu_qty) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_grocary where gmenu_qty=?", new String[]{gmenu_qty});
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("gmenu_qty"));
        }
        return "0";
    }

    public Cursor getCartDetailsgrocary() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from tbl_grocary", null);
    }

    public void sub_catagory_search(String category_id, String subcategory_id, String category_name, String category_image, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("category_id", category_id);
        cv.put("subcategory_id", subcategory_id);
        cv.put("category_name", category_name);
        cv.put("category_image", category_image);
        cv.put("status", status);

        db.insert("sub_catagory_search", null, cv);
    }


}
