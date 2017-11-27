package niuwei.com.headlinedemo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import niuwei.com.headlinedemo.R;
import niuwei.com.headlinedemo.adapter.MyXlvAdapter;
import niuwei.com.headlinedemo.bean.Bean;
import niuwei.com.headlinedemo.util.StreamTools;
import niuwei.com.headlinedemo.WebActivity;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by One Dream on 2017/8/31.
 */

public class NewsActivity extends Fragment  implements XListView.IXListViewListener{

    private View view;
    private XListView xlv;

    private String path;
    boolean flag;

    private MyXlvAdapter adapter;
    private List<Bean.DataBean> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.main_news,null);
        path = getArguments().getString("path");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isOnline()) {
            //加载数据
            anys(path);
        }else{
            //弹出提示对话框
            showDialog();
            
        }
        //找控件
        xlv = (XListView) view.findViewById(R.id.xlv);
        //设置让其可以下拉和上拉
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);

        //点击条目进入详情页
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",data.get(position-1).getUrl());
               startActivity(intent);
            }
        });

        View view=View.inflate(getActivity(),R.layout.pop_layout,null);
        //找控件
        final TextView no= (TextView) view.findViewById(R.id.no);
        ImageView image= (ImageView)view.findViewById(R.id.image);
        //实例化popupwindow
        final PopupWindow popupWindow=new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,30);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap)null));
        //点击弹出popupwindow
        xlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                popupWindow.showAsDropDown(view);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.remove(position-1);
                        adapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });
                return true;
            }
        });
        //点击×关闭popouwindow
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }
    /**
     * 联网提示框
     */
    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("当前无网络");
        builder.setNegativeButton("算了吧",null);
        builder.setPositiveButton("我要去联网", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //跳转网络设置界面
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

            }
        });
        builder.create().show();
    }
    /**
     * 判断网络是否连接
     * @return true 代表网络是连接 false 没有连接
     */
    public boolean isOnline() {

        //得到一个连接管理者
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //得到联网信息
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //判断设备是否联网
        return (networkInfo != null && networkInfo.isConnected());
    }




    public void anys(final String url){
        new AsyncTask<String,View,String>(){
            @Override
            protected String doInBackground(String... params) {
                try {
                    String path=params[0];
                    URL url=new URL(path);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    while (connection.getResponseCode()==200){
                        return  StreamTools.read(connection.getInputStream());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s==null){
                    return;
                }
                Gson gson=new Gson();
                Bean bean = gson.fromJson(s, Bean.class);
                data = bean.getData();
                if (adapter==null){
                    adapter = new MyXlvAdapter(data,getActivity());
                    xlv.setAdapter(adapter);
                }else{
                    adapter.loadMore(data,flag);
                }

            }
        }.execute(url);
    }
    public static Fragment NewInstance(String path){
        NewsActivity fragment1 = new NewsActivity();
        Bundle bundle = new Bundle();
        bundle.putString("path",path);
        fragment1.setArguments(bundle);

        return fragment1;
    }
    //下拉刷新
    @Override
    public void onRefresh() {

        flag=true;
        anys(path);
        xlv.stopRefresh(true);
    }
    //上拉加载
    @Override
    public void onLoadMore() {

        flag=false;
        anys(path);
        xlv.stopLoadMore();
    }
}
