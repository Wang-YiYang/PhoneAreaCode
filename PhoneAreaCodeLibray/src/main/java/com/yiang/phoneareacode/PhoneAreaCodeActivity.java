package com.yiang.phoneareacode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建：yiang
 * <p>
 * 描述：
 */
public class PhoneAreaCodeActivity extends AppCompatActivity implements OnQuickSideBarTouchListener {

    private QuickSideBarView quickSideBarView;
    private QuickSideBarTipsView quickSideBarTipsView;
    public static final int resultCode = 0x1110;
    public static final String DATAKEY = "AreaCodeModel";
    private boolean isEnglish;
    private List<String> sections = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private List<AreaCodeModel> datalist;


    public static Intent newInstance(Context context, String title, String titleTextColor, String titleColor, String stickHeaderColor) {
        Intent intent = new Intent(context, PhoneAreaCodeActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("titleColor", titleColor);
        intent.putExtra("titleTextColor", titleTextColor);
        intent.putExtra("stickHeaderColor", stickHeaderColor);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_phoneareacode);

        quickSideBarView = (QuickSideBarView) findViewById(R.id.quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);
        quickSideBarView.setOnQuickSideBarTouchListener(this);

        String titleColor = getIntent().getStringExtra("titleColor");
        String stickHeaderColor = getIntent().getStringExtra("stickHeaderColor");
        String title = getIntent().getStringExtra("title");
        String titleTextColor = getIntent().getStringExtra("titleTextColor");


        //界面标题
        TextView tvTitle = findViewById(R.id.tvTitle);
        if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
        if (!TextUtils.isEmpty(titleTextColor))
            tvTitle.setTextColor(Color.parseColor(titleTextColor));


        //标题背景颜色
        if (!TextUtils.isEmpty(titleColor))
            findViewById(R.id.llTitle).setBackgroundColor(Color.parseColor(titleColor));
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //读取数据
        String json = Utils.readAssetsTxt(this, "phoneAreaCode");
        datalist = Utils.jsonToList(json);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sortList(datalist);
        final PhoneAreaCodeAdapter adapter = new PhoneAreaCodeAdapter();
        adapter.setDataList(datalist);
        if (!TextUtils.isEmpty(stickHeaderColor)) adapter.setStickHeaderColor(stickHeaderColor);
        recyclerView.setAdapter(adapter);
        //设置recyclerView需要的Decoration;
        StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(decoration);
        adapter.setOnItemClickListener(new PhoneAreaCodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AreaCodeModel model) {
                Intent intent = new Intent();
                intent.putExtra(DATAKEY, model);
                setResult(resultCode, intent);
                finish();
            }
        });

        final TextView tvLan = findViewById(R.id.tvLan);
        if (!TextUtils.isEmpty(titleTextColor))
            tvLan.setTextColor(Color.parseColor(titleTextColor));

        tvLan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnglish = !isEnglish;
                tvLan.setText(isEnglish ? "中文" : "English");
                sortList(datalist);
                adapter.setDataList(datalist);
                adapter.setEnglish(isEnglish);

            }
        });


    }


    /**
     * 根据国家中文拼音首字母排序
     *
     * @param datalist
     */
    private void sortList(List<AreaCodeModel> datalist) {
        Collections.sort(datalist, new Comparator<AreaCodeModel>() {
                    @Override
                    public int compare(AreaCodeModel o1, AreaCodeModel o2) {
                        if (isEnglish) {
                            return Utils.getFirstPinYin(o1.getEn())
                                    .compareTo(Utils.getFirstPinYin(o2.getEn()));
                        }
                        return Utils.getFirstPinYin(o1.getName())
                                .compareTo(Utils.getFirstPinYin(o2.getName()));
                    }
                }
        );


        sections.clear();
        for (AreaCodeModel area : datalist) {
            String section = "";
            if (isEnglish) {
                section = Utils.getFirstPinYin(area.getEn());
            } else {
                section = Utils.getFirstPinYin(area.getName());
            }
            if (!sections.contains(section)) sections.add(section);
        }
        quickSideBarView.setLetters(sections);
    }


    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        layoutManager.scrollToPositionWithOffset(index(letter), 0);
    }

    private int index(String letter) {
        for (int i = 0; i < datalist.size(); i++) {
            AreaCodeModel area = datalist.get(i);
            String section = "";
            if (isEnglish) {
                section = Utils.getFirstPinYin(area.getEn());
            } else {
                section = Utils.getFirstPinYin(area.getName());
            }
            if (TextUtils.equals(letter, section)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onLetterTouching(boolean touching) {
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.GONE);

    }
}
