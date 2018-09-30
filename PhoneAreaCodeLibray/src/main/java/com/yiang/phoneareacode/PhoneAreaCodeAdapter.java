package com.yiang.phoneareacode;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建：yiang
 * <p>
 * 描述：
 */
public class PhoneAreaCodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter {
    private List<AreaCodeModel> dataList = new ArrayList<>();
    private String stickHeaderColor;
    private OnItemClickListener onItemClickListener;
    private boolean english;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setStickHeaderColor(String stickHeaderColor) {
        this.stickHeaderColor = stickHeaderColor;
    }


    public void setDataList(List<AreaCodeModel> list) {
        if (list == null) return;
        dataList = list;
        notifyDataSetChanged();
    }

    @Override
    public long getHeaderId(int position) {
        if (dataList.size() == 0) return 0;
        String name = "";
        long headerId = 0;
        if (english) {
            name = dataList.get(position).getEn();
            headerId = name.substring(0, 1).hashCode();
        } else {
            name = dataList.get(position).getName();
            headerId = Utils.getFirstPinYin(name).hashCode();
        }


        return headerId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderHolder headerHolder = (HeaderHolder) holder;


        String name = "", header = "";
        if (english) {
            name = dataList.get(position).getEn();
        } else {
            name = dataList.get(position).getName();
        }
        header = Utils.getFirstPinYin(name);
        headerHolder.bindData(header);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_area_code, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Holder holder = (Holder) viewHolder;
        holder.bindData(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setEnglish(boolean english) {
        this.english = english;
        notifyDataSetChanged();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvHeader;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tvHeader);
            View clParent = itemView.findViewById(R.id.clParent);
            clParent.setBackgroundColor(Color.parseColor(stickHeaderColor));

        }

        public void bindData(String header) {
            tvHeader.setText(header);
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvArea, tvCode;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvArea);
            tvCode = itemView.findViewById(R.id.tvCode);

        }

        public void bindData(final AreaCodeModel model) {
            tvArea.setText(english ? model.getEn() : model.getName());
            tvCode.setText("+" + model.getTel());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(model);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(AreaCodeModel model);
    }
}
