package edu.ayd.joyfukitchen.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	/**
	 * 显示一个Toast
	 * @param context: 上下文对象
	 * @param msg: 显示的字符串/消息
	 * */
	public static void show(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
