package niuwei.com.headlinedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.app.ActionBar.LayoutParams;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 * Created by One Dream on 2017/9/12.
 */

public class WebActivity extends Activity {
    private Tencent mTencent;
    private String APP_ID = "1105602574";
    private IUiListener loginListener;
    private String SCOPE = "all";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        //寻找控件
        WebView web= (WebView) findViewById(R.id.web);
        ImageView share= (ImageView) findViewById(R.id.share);
        //接受传过来的Url
        String url = getIntent().getStringExtra("url");
        WebSettings set=web.getSettings();
        set.setJavaScriptEnabled(true);
        set.setLoadWithOverviewMode(true);
        //加载网页
        web.loadUrl(url);
        //设置可以在本页面加载网页
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
        });

        //弹框
        final View print=View.inflate(WebActivity.this,R.layout.web_layout,null);       //父布局
        View view=View.inflate(WebActivity.this,R.layout.alert_layout,null);            //子布局
        //PopupWindow的实例化
        //第一个控件是子布局
        //第二个控件是宽度
        //字三个控件是高度
        final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,100);
        // 外部空白区域是否可以触摸
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        // popupWindow需要得到焦点，才能进行编辑
        popupWindow.setFocusable(true);
        //点击分享图片  出现PopupWindow
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(print, Gravity.BOTTOM, 50, 50);
            }
        });
        //找到子布局的两个图片控件
        ImageView qq= (ImageView) view.findViewById(R.id.qq);
        ImageView qzone= (ImageView) view.findViewById(R.id.qzone);
        //分享
        mTencent = Tencent.createInstance(APP_ID,getApplicationContext());
        //分享到QQ好友
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "这是我男神的");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"今日头条");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://blog.csdn.net/u013451048");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
                mTencent.shareToQQ(WebActivity.this, params,new shareListener());
            }
        });
        //分享到空间
        qzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "这是我男神的");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,"今日头条");
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://blog.csdn.net/u013451048");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://avatar.csdn.net/C/3/D/1_u013451048.jpg");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "CSDN");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQQ(WebActivity.this, params,new shareListener());
            }
        });

    }
    public class shareListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            //分享成功后回调
            Toast.makeText(WebActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败后回调
        }

        @Override
        public void onCancel() {
            //取消分享后回调
        }
    };
}
