package com.yiang.phoneareacode;

import android.content.Context;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * @param context  上下文
     * @param fileName 文件名称
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
     * @param json 数据
     * @return 数据模型
     */
    public static List<AreaCodeModel> jsonToList(String json) {
        List<AreaCodeModel> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            AreaCodeModel model;
            for (int i = 0; i < jsonArray.length(); i++) {
                model = new AreaCodeModel();
                JSONObject object = jsonArray.getJSONObject(i);
                model.setTel(object.getString("tel"));
                model.setEn(object.getString("en"));
                model.setName(object.getString("name"));
                model.setShortName(object.getString("shortName"));
                list.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * 返回拼音首字母
     *
     * @param str 要提取首字母的字符串
     * @return 首字母
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
