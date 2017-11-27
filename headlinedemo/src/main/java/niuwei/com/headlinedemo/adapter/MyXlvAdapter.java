package niuwei.com.headlinedemo.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import niuwei.com.headlinedemo.R;
import niuwei.com.headlinedemo.bean.Bean;
import niuwei.com.headlinedemo.util.MyApplication;

/** xlv 主页面的适配器
 * Created by One Dream on 2017/9/11.
 */

public class MyXlvAdapter extends BaseAdapter {
    private List<Bean.DataBean> list;
    private Context context;
    public final  static  int TYPE_ONE=0;
    public final  static  int TYPE_TWO=1;
    public MyXlvAdapter(List<Bean.DataBean> data, FragmentActivity activity) {
        this.list=data;
        this.context=activity;
    }
    public  void loadMore(List<Bean.DataBean> datas,boolean flag){
        for (Bean.DataBean dataBean : datas) {
            //flag = true 代表下拉  false 上拉
            if(flag){
                list.add(0,dataBean);
            }else {
                //将更多的数据添加到原来与适配器绑定的集合中去
                list.add(dataBean);
            }
        }
        //刷新界面
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null?list.size() : 0;
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
    public int getItemViewType(int position) {
        if (position%2==0){
            return TYPE_ONE;
        }else{
            return  TYPE_TWO;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);
        Viewholder viewHolder;
        switch (type){
            case TYPE_ONE:
                if (convertView==null){
                    convertView=View.inflate(context, R.layout.news_one,null);
                    viewHolder=new Viewholder();
                    viewHolder.newstitle=(TextView) convertView.findViewById(R.id.newstitle);
                    viewHolder.image=(ImageView) convertView.findViewById(R.id.img);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder=(Viewholder) convertView.getTag();
                }
                viewHolder.newstitle.setText(list.get(position).getTitle());

//        list.get(position).getUser_info().getAvatar_url()==null?"":list.get(position).getUser_info().getAvatar_url()

                ImageLoader.getInstance().displayImage(list.get(position).getUser_info()==null?"":list.get(position).getUser_info().getAvatar_url(), viewHolder.image,new MyApplication().options());
                break;
            case TYPE_TWO:
                if (convertView==null){
                    convertView=View.inflate(context, R.layout.news_two,null);
                    viewHolder=new Viewholder();
                    viewHolder.newstitle=(TextView) convertView.findViewById(R.id.newstitle);
                    viewHolder.image=(ImageView) convertView.findViewById(R.id.img);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder=(Viewholder) convertView.getTag();
                }
                viewHolder.newstitle.setText(list.get(position).getTitle());

//        list.get(position).getUser_info().getAvatar_url()==null?"":list.get(position).getUser_info().getAvatar_url()

                ImageLoader.getInstance().displayImage(list.get(position).getUser_info()==null?"":list.get(position).getUser_info().getAvatar_url(), viewHolder.image,new MyApplication().options());
                break;

        }


        return convertView;
    }
    class Viewholder{
        TextView newstitle;
        ImageView image;
    }
}
