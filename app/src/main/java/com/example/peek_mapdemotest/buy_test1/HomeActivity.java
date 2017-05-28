package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import operation.GeneralOperation;


public class HomeActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private String[] titles = {"查看地址", "查看信息", "退出"};
    private ListView drawList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // 初始化控件
        initView();

        // 初始化数据
        initData();
    }

    // 初始化Toolbar、DrawerLayout，生成相应的对象
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //抽屉部分的 ListView
        drawList = (ListView) findViewById(R.id.lv_title);
        MyAdapter myAdapter = new MyAdapter();
        drawList.setAdapter(myAdapter);

    }

    // 设置应用title
    private void initData() {
        // 设置Toolbar标题，需在setSupportActionBar之前，不然会失效
        mToolbar.setTitle("首页");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        // 设置toolbar支持actionbar
        setSupportActionBar(mToolbar);

        // 实现按钮开关的显示及打开关闭功能并同步动画
        initDrawerToggle();
    }

    private void initDrawerToggle() {
        // 参数：开启抽屉的activity、DrawerLayout的对象、toolbar按钮打开关闭的对象、描述open drawer、描述close drawer
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        // 添加抽屉按钮，通过点击按钮实现打开和关闭功能; 如果不想要抽屉按钮，只允许在侧边边界拉出侧边栏，可以不写此行代码
        mDrawerToggle.syncState();
        // 设置按钮的动画效果; 如果不想要打开关闭抽屉时的箭头动画效果，可以不写此行代码
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.drawer_list, null);
//            ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);

//            iv_photo.setBackgroundResource(imagesId[position]);
            tv_title.setText(titles[position]);
            return view;
        }
    }

}
