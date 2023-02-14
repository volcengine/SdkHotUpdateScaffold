package com.volcengine.zeusscaffold;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.volcengine.zeus.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuekai
 * @date 2020/8/13
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.BaseViewHolder> {
    private static final int TYPE_TITLE = 0x00000001;
    private static final int TYPE_BUTTON = 0x00000002;
    private List<ItemAction> mData = new ArrayList<>();
    private int mType;

    private int checkedIndex = 0;
    private OnItemClickListener mListener;

    public SimpleAdapter() {
        this.mType = TYPE_BUTTON;
    }

    public SimpleAdapter(OnItemClickListener listener) {
        this.mType = TYPE_TITLE;
        this.mListener = listener;
    }

    public int getCheckedIndex() {
        return checkedIndex;
    }

    public void setCheckedIndex(int checkedIndex) {
        this.checkedIndex = checkedIndex;
    }

    public void setData(List<ItemAction> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_TITLE) {
            return new LeftViewHolder(inflater.inflate(R.layout.recycler_view_item_title, parent, false));
        }

        if (viewType == TYPE_BUTTON) {
            return new RightViewHolder(inflater.inflate(R.layout.recycler_view_item_button, parent, false));
        }

        throw new RuntimeException();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindData(mData.get(position), position, mListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }


    abstract static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        abstract void bindData(ItemAction item, int position, OnItemClickListener mListener);
    }

    private class LeftViewHolder extends BaseViewHolder {
        public LeftViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindData(final ItemAction item, final int position, final OnItemClickListener mListener) {
            ((TextView) itemView).setText(item.title);
            itemView.setSelected(position == checkedIndex);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.subItem != null) {
                        checkedIndex = position;
                        SimpleAdapter.this.notifyDataSetChanged();
                        mListener.onClick(item);
                    }
                }
            });
        }
    }

    private static int idIndex = 0;

    private static class RightViewHolder extends BaseViewHolder {
        private final Button mButton;

        public RightViewHolder(View itemView) {
            super(itemView);
            // 为itemView设置唯一id，方便塞入fragment。trick的写法。
            itemView.setId(0x7fff0000 + idIndex++);
            mButton = itemView.findViewById(R.id.button);
        }

        @Override
        void bindData(final ItemAction item, int position, OnItemClickListener mListener) {
            mButton.setText(item.title);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.action.run(itemView);
                }
            });
        }
    }
}
