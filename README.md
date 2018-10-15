# 效果图
![效果图](https://github.com/Wang-YiYang/PhoneAreaCode/blob/master/Screenshots/GIF.gif)

# 介绍

手机选择国家区号

# 使用

```java
 implementation 'me.yiang:PhoneAreaCodeLibray:0.2.0'

 ```

```java
  SelectPhoneCode.with(TestActivity.this)
                        .setTitle("区号选择")
                        .setStickHeaderColor("#41B1FD")//粘性头部背景颜色
                        .setTitleBgColor("#ffffff")//界面头部标题背景颜色
                        .setTitleTextColor("#454545")//标题文字颜色
                        .select();
```

在调用界面重写

```java
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PhoneAreaCodeActivity.resultCode) {
            if (data != null) {
                AreaCodeModel model = (AreaCodeModel) data.getSerializableExtra(PhoneAreaCodeActivity.DATAKEY);
                textView.setText(model.getName() + "  " + model.getTel());
            }
        }
    }
```


