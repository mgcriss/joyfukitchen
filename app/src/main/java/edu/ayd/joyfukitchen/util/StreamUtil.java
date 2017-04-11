package edu.ayd.joyfukitchen.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Stream 工具类
 * */
public class StreamUtil {

	/**
	 * 将输入流转成字符串
	 * @return
	 * 			成功返回 InputStream 转换成的 String 否则 返回 null
	 * */
	public static String Stream2String(InputStream is) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		// 记录读取内容的临时变量
		int temp = -1;
		try {
			while ((temp = is.read(buffer)) != -1) {
				// 将inputStream写入到 bos
				bos.write(buffer,0,temp);
			}
			return bos.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
