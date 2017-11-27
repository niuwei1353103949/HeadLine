package niuwei.com.headlinedemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import niuwei.com.headlinedemo.bean.TabTypeBean;

/**
 * Created by One Dream on 2017/9/8.
 */

public class SqliteDao{
    List<TabTypeBean> uplist;
    List<TabTypeBean> downlist;
    private final MySqliteOpenHelper helper;

    public SqliteDao(Context context){
        helper = new MySqliteOpenHelper(context);
    }
    //添加上面的GridView
    public void addup(String name,String path){
        System.out.println(name +"aaaaaaaaaaaaaa"+path);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("path",path);
        db.insert("uppindao",null,values);
        db.close();
    }
    //添加下面的GridView
    public void adddown(String name,String path){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("path",path);
        db.insert("downpindao",null,values);
        db.close();
    }
    //查询上面的表s
    public List<TabTypeBean> selectup(){
        uplist=new ArrayList<TabTypeBean>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("uppindao", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String nameup=cursor.getString(cursor.getColumnIndex("name"));
            String pathup=cursor.getString(cursor.getColumnIndex("path"));
            System.out.println(nameup+"============"+pathup);
            uplist.add(new TabTypeBean(nameup,pathup));

        }
        return uplist;
    }
    //查询下面的表
    public List<TabTypeBean> selectdown(){
        downlist=new ArrayList<TabTypeBean>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("downpindao", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String namedown=cursor.getString(cursor.getColumnIndex("name"));
            String pathdown=cursor.getString(cursor.getColumnIndex("path"));
            downlist.add(new TabTypeBean(namedown,pathdown));
        }
        return downlist;
    }
    //删除上面的表
    public void deleteup(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from uppindao where name=?",new Object[]{name});
        db.close();
    }
    //删除下面的表
    public void deletedown(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from downpindao where name=?",new Object[]{name});
        db.close();
    }

}
