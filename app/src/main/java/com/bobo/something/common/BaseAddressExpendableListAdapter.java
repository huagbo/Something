package com.bobo.something.common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.bobo.something.data.ProvinceCityBean.DataBean;
import com.bobo.something.data.ProvinceCityBean.DataBean.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbo on 2017/6/12.
 */

public abstract class BaseAddressExpendableListAdapter extends BaseExpandableListAdapter {
    public static final int TYPE_PARENT = 1;
    public static final int TYPE_CHILD = 2;

    int parent_layout = 0;
    int child_layout = 0;

    Context mContext;
    List<DataBean> mData;
    protected final byte[] sLock = new byte[0];

    public BaseAddressExpendableListAdapter(Context context, List<DataBean> data) {
        this.mContext = context;
        this.mData = data;

    }

    protected abstract View convert(View convertView, int type, DataBean dataBean, AreaBean areaBean);

    public BaseAddressExpendableListAdapter(Context context) {
        this(context, null);
    }

    protected void addItemType(int type, int layoutId) {
        switch (type) {
            case TYPE_PARENT:
                parent_layout = layoutId;
                break;
            case TYPE_CHILD:
                child_layout = layoutId;
                break;
        }
    }

    @Override
    public int getGroupCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public DataBean getGroup(int groupPosition) {
        return mData == null ? null : mData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, parent_layout, null);
        }
        convertView.setClickable(true);//设置不可收起
        return convert(convertView, TYPE_PARENT, getGroup(groupPosition), null);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, child_layout, null);
        }
        return convert(convertView, TYPE_CHILD, getGroup(groupPosition), getChild(groupPosition, childPosition));
    }

    @Override
    public AreaBean getChild(int groupPosition, int childPosition) {
        try {
            List<AreaBean> array = mData.get(groupPosition).getProvinces();
            return array.get(childPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return mData.get(groupPosition).getProvinces().size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return Long.parseLong(groupPosition + "" + childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public final void setDatas(final List<DataBean> list) {
        synchronized (sLock) {
            if (list == null) {
                mData = new ArrayList<>();
            } else {
                mData = list;
            }
            notifyDataSetChanged();
        }
    }


}
