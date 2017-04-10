package edu.ayd.joyfukitchen.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

	private static SharedPreferences sp;

	/** 写 */
	public static void putBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	/** 读 */
	public static boolean getBoolean(Context context, String key, boolean defaultValue) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defaultValue);
	}
	
	
	
	/** 写 */
	public static void putString(Context context, String key, String value) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}

	/** 读 */
	public static String getString(Context context, String key, String defaultValue) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defaultValue);
	}

	/**
	 * 移除节点
	 * */
	public static void remove(Context context, String key) {
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().remove(key);
	}
}
