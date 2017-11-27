package niuwei.com.headlinedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import niuwei.com.headlinedemo.adapter.MyDownGridViewAdapter;
import niuwei.com.headlinedemo.adapter.MyUPGridViewAdapter;
import niuwei.com.headlinedemo.bean.TabTypeBean;
import niuwei.com.headlinedemo.db.SqliteDao;

/**
 * Created by One Dream on 2017/9/5.
 */

public class PinDaoActivity extends Activity {

    private GridView my_gv;
    private GridView it_gv;
    List<TabTypeBean> uplist,downlist;
    private MyUPGridViewAdapter upAdapter;
    private MyDownGridViewAdapter downAdapter;
    private SqliteDao dao;
    private ImageView pindao_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pindao_layout);
        //寻找控件
        my_gv = (GridView) findViewById(R.id.my_gv);
        it_gv=(GridView) findViewById(R.id.it_gv);
        dao = new SqliteDao(PinDaoActivity.this);
        pindao_image = (ImageView) findViewById(R.id.pindao_image);
        //接受集合
        uplist=dao.selectup();
        downlist=dao.selectdown();
        //适配器
        upAdapter = new MyUPGridViewAdapter(PinDaoActivity.this,uplist);
        my_gv.setAdapter(upAdapter);
        downAdapter = new MyDownGridViewAdapter(PinDaoActivity.this,downlist);
        it_gv.setAdapter(downAdapter);
        //点击左上角图片  进行跳转
        pindao_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PinDaoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //点击上面的那张表
        my_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                downlist.add(uplist.get(position));
                dao.adddown(uplist.get(position).getTitle(),uplist.get(position).getPath());
                downAdapter.notifyDataSetChanged();
                dao.deleteup(uplist.get(position).getTitle());
                uplist.remove(uplist.get(position));

                upAdapter.notifyDataSetChanged();

            }
        });
        //点击下面的那张表
        it_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                uplist.add(downlist.get(position));
                dao.addup(downlist.get(position).getTitle(),downlist.get(position).getPath());
                upAdapter.notifyDataSetChanged();
                dao.deletedown(downlist.get(position).getTitle());
                downlist.remove(downlist.get(position));

                downAdapter.notifyDataSetChanged();


            }
        });

    }


}
