package edu.ayd.joyfukitchen.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/28.
 * 文本工具类
 */

public class TextUtil {

    /**
     * 设置搜索关键字高亮,颜色红色
     * @param content 原文本内容
     * @param keyword 关键字
     */
    private SpannableString setKeyWordColor(String content, String keyword){
        SpannableString s = new SpannableString(content);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()){
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(Color.RED),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }


}
