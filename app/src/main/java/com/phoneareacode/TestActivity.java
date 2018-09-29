package com.phoneareacode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yiang.phoneareacode.AreaCodeModel;
import com.yiang.phoneareacode.PhoneAreaCodeActivity;
import com.yiang.phoneareacode.SelectPhoneCode;

import areacode.yiang.com.phoneareacode.R;

/**
 * 创建：yiang
 * <p>
 * 描述：
 */
public class TestActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_test);

        textView = findViewById(R.id.test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPhoneCode.with(TestActivity.this)
                        .setTitle("hah")
                        .setStickHeaderColor("#0099FF")
                        .setTitleBgColor("#D81E06")
                        .setTitleTextColor("#ffffff")
                        .select();
            }
        });
    }

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
}
