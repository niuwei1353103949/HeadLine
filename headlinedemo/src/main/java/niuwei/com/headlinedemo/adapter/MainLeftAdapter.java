package niuwei.com.headlinedemo.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import niuwei.com.headlinedemo.R;

/** 左侧的适配器
 * Created by One Dream on 2017/9/1.
 */

public class MainLeftAdapter extends BaseAdapter {
    String[] str;
    FragmentActivity context;
    List<Integer> list;

    public MainLeftAdapter(List<Integer> list,String[] strName, FragmentActivity activity) {
        this.str=strName;
        this.context=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return str[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.left_item,null);
            viewHolder=new ViewHolder();
            viewHolder.image=(ImageView) convertView.findViewById(R.id.left_image);
            viewHolder.name=(TextView) convertView.findViewById(R.id.left_name);

            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.image.setImageResource(list.get(position));
        viewHolder.name.setText(str[position]);

        return convertView;
    }
    class ViewHolder{
        ImageView image;
        TextView name;
    }
}
