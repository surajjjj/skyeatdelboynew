package com.food.food_order_Delivaryboy.utilities;

import android.graphics.Bitmap;

public class Config {


	//public static String get_url="http://inovatiqsolution.com/demo/sabjiwala/admin/actions.php?";
	public static String post_url="https://skynetitsolutions.com/Android_Register_login/actions.php?";
	//public static String media_url="http://inovatiqsolution.com/demo/sabjiwala/admin/images/";
	public static String resizable_media_url="http://inovatiqsolution.com/demo/sabjiwala/admin/image_opr.php?";

//	public static String get_url="http://inovatiqsolution.com/demo/sabjiwala/admin/actions.php?";
//
//	public static String get_url="http://sanjivaniclinic.org.in/action.php?";
public static String get_url="https://skynetitsolutions.com/skyeat_flutter/actions.php?";
//	public static String post_url="http://inovatiqsolution.com/demo/sabjiwala/admin/actions.php";
	public static String media_url="https://skynetitsolutions.com/Hotel_Admin/images/";
	public static String media_url_new="https://skynetitsolutions.com/Android_Register_login/uploads/";
//	public static String resizable_media_url="http://inovatiqsolution.com/demo/sabjiwala/admin/image_opr.php?";

	/*public static String get_url="http://sipperschoice.com/sc/app/actions.php?";
	public static String post_url="http://sipperschoice.com/sc/app/actions.php";*/
	public static Bitmap resizeBitmap(final Bitmap temp, final int size) {
		if (size > 0) {
			int width = temp.getWidth();
			int height = temp.getHeight();
			float ratioBitmap = (float) width / (float) height;
			int finalWidth = size;
			int finalHeight = size;
			if (ratioBitmap < 1) {
				finalWidth = (int) ((float) size * ratioBitmap);
			} else {
				finalHeight = (int) ((float) size / ratioBitmap);
			}
			return Bitmap.createScaledBitmap(temp, finalWidth, finalHeight, true);
		} else {
			return temp;
		}
	}
	public static final String TOPIC_GLOBAL = "global";
	public static final String REGISTRATION_COMPLETE = "registrationComplete";
	public static final String PUSH_NOTIFICATION = "pushNotification";

	// id to handle the notification in the notification tray
	public static int NOTIFICATION_ID = 100;
	public static int NOTIFICATION_ID_BIG_IMAGE = 101;
	public static final String SHARED_PREF="NSS";

	public static void incrementNotificationId(){
		NOTIFICATION_ID++;
		NOTIFICATION_ID_BIG_IMAGE++;
	}
}