package com.example.skyeatadmin;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;
import android.util.Patterns;

import com.example.skyeatadmin.Activity.DeliveryBoyActivity;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Constant

{
    public static String ZONE_NAME;
    public static String key ="SECRETEKEY";
    public static String IV ="abcdefghi";
    public static String MODE ="Blowfish/CBC/PKCS5Padding";
    public static String ALGORITHM ="Blowfish";
    public static final String APP_PACKAGE_NAME = "food.food_order_Delivaryboy";
//
//    public AlertDialog alertDialog;
//    public AlertDialog.Builder builder;

    public static String longToDate(String dateAndTime)
    {
       // long timestamp = 1620000000; // Unix timestamp in seconds
        long timestamp = Long.parseLong(dateAndTime); // Unix timestamp in seconds
        Date date = new Date(timestamp * 1000L); // Convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    public static String mlongToDate(String dateAndTime)
    {
        // long timestamp = 1620000000; // Unix timestamp in seconds
        long timestamp = Long.parseLong(dateAndTime); // Unix timestamp in seconds
        Date date = new Date(timestamp * 1000L); // Convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String dateToLong(String dateString)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(dateString);
            long longValue = date.getTime();
            return String.valueOf(longValue);

        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void exitAlertDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any necessary actions and close the activity
                //super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the activity
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String epass) {
    try {
        byte[] values = Base64.decode(epass, Base64.DEFAULT);
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[8])); // Use a random IV for each encryption, not hardcoded
        return new String(cipher.doFinal(values));
    } catch (Exception e) {
        e.printStackTrace(); // Handle exceptions appropriately
        return null; // Return an error indicator or handle the error as needed
    }
}



}
