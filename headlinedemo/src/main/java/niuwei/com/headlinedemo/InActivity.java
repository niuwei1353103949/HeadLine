package niuwei.com.headlinedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InActivity extends AppCompatActivity {

    private ViewPager vp;
    private List<View> list;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private TextView in;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                    Intent intent=new Intent(InActivity.this,MainActivity.class);
                    startActivity(intent);
            }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_activity);
        vp = (ViewPager) findViewById(R.id.vp);

        //将页面加入集合
        initPage();
        //适配
        vp.setAdapter(new MyPageAdapter());
        //判断是否第一次进入
        panduan();
    }

    public void panduan(){
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        edit = sharedPreferences.edit();
        boolean flag=sharedPreferences.getBoolean("flag",false);
        if (flag){
            setContentView(R.layout.two_in_layout);
            handler.sendEmptyMessageDelayed(1,3000);
        }else{
            edit.putBoolean("flag",true).commit();
        }
    }

    //找到三个布局  让其可以滑动
    //第三个界面有一个进入按钮  点击的时候进入界面  同时存一个boolean为true
    private void initPage() {
        list = new ArrayList<View>();
        View v1=View.inflate(this,R.layout.in_one_layout,null);
        View v2=View.inflate(this,R.layout.in_two_layout,null);
        View v3=View.inflate(this,R.layout.in_three_layout,null);
        list.add(v1);
        list.add(v2);
        list.add(v3);
        in = (TextView)v3.findViewById(R.id.in);
        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InActivity.this,MainActivity.class);
                edit.putBoolean("flag",true).commit();
                startActivity(intent);
            }
        });
    }
    private  class MyPageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
