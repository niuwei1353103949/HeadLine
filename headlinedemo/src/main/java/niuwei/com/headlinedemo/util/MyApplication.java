package niuwei.com.headlinedemo.util;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import niuwei.com.headlinedemo.R;

/**
 * Created by One Dream on 2017/9/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobSDK.init(this, "210bc97e33e86", "b27ba337e16c6d584346668860616e38");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(100)
                .threadPriority(5)
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .memoryCacheExtraOptions(400,800)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
    }
    public static DisplayImageOptions options(){

        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher_round)
                .showImageOnFail(R.mipmap.ic_launcher_round)
                .showImageOnLoading(R.mipmap.ic_launcher_round)
                .build();
        return options;
    }
}
