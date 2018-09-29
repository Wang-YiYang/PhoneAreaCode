package com.yiang.phoneareacode;

import java.io.Serializable;

/**
 * 创建：yiang
 * <p>
 * 描述：
 */
public class AreaCodeModel  implements Serializable{



    private String shortName;
    private String name;
    private String en;
    private String tel;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
