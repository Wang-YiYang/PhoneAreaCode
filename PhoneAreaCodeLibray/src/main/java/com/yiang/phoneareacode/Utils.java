package com.yiang.phoneareacode;

import android.content.Context;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建：yiang
 * <p>
 * 描述：
 */
public class Utils {


    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 把json 字符串转化成list
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> gsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonElement element = new JsonParser().parse(json);
        if (element instanceof JsonArray) {
            JsonArray array = element.getAsJsonArray();

            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }

        return list;
    }



    /**
     * 返回拼音首字母
     *
     * @param str
     * @return
     */
    public static String getFirstPinYin(String str) {
        if (!TextUtils.isEmpty(str) && str.length() > 0) {
            if (Pinyin.isChinese(str.charAt(0))) {
                String py = Pinyin.toPinyin(str.charAt(0)).substring(0, 1);
                return py;
            } else {
                if (str.substring(0, 1).matches("^[a-zA-Z]*")) {
                    return str.substring(0, 1);
                }
                return "#";
            }
        }
        return "#";
    }
}
