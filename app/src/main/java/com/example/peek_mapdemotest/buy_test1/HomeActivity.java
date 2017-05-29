package com.example.peek_mapdemotest.buy_test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fragment.ClassFragment;
import fragment.StoreFragment;
import fragment.MyFragmentPagerAdapter;
import object.User;
import operation.GeneralOperation;


public class HomeActivity extends AppCompatActivity {

    private User user = null;
    public ArrayList list = new ArrayList();


    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private String[] titles = {"我的地址", "我的信息", "退出"};
    private String[] tabTitles ={"商铺", "分类"};
    private ListView drawList;
    private RelativeLayout drawerList;

    private MyFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // 初始化控件
        initView();

        // 初始化数据
        initData();

        initLayoutView();

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
        mToolbar.setTitle("Tea Store");
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

            drawList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            Intent checkAddress = new Intent(HomeActivity.this, AddressList.class);
                            startActivity(checkAddress);
                            break;
                        case 1:
                            Intent checkInfo = new Intent(HomeActivity.this, AccountMessage.class);
                            startActivity(checkInfo);
                            break;
                        case 2:
                            try {
                                user = GeneralOperation.getUser();
                                list = GeneralOperation.logout(user);
                                if (Integer.parseInt((String) list.get(0)) == 204) {
                                    Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_LONG).show();
//                        Intent intent =new Intent(getApplicationContext(),MainActivity_buy.class);
//                        startActivity(intent);
                                    final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
//            String Authorization = data.getString("Authorization");
                                } else {
                                    JSONObject object = new JSONObject((String) list.get(1));
                                    String message = object.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        default:
                            break;

                    }
                }

            });
            return view;
        }
    }

    private void initLayoutView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("商铺"));
        tabLayout.addTab(tabLayout.newTab().setText("分类"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("TEST","onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(StoreFragment.newInstance(1));
        fragments.add(ClassFragment.newInstance(2));

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments, Arrays.asList(tabTitles));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TEST","select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
