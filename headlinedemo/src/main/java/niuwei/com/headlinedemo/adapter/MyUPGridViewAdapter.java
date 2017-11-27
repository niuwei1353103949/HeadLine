package niuwei.com.headlinedemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import niuwei.com.headlinedemo.PinDaoActivity;
import niuwei.com.headlinedemo.R;
import niuwei.com.headlinedemo.bean.TabTypeBean;

/** 频道管理上面数据的适配器
 * Created by One Dream on 2017/9/7.
 */

public class MyUPGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<TabTypeBean> list;
    public MyUPGridViewAdapter(Context pinDaoActivity, List<TabTypeBean> list) {
        this.list=list;
        this.context=pinDaoActivity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(context, R.layout.pindao_item_layout,null);
        }
        TextView button= (TextView) convertView.findViewById(R.id.bt);

        button.setText(list.get(position).getTitle());
        return convertView;
    }
}
