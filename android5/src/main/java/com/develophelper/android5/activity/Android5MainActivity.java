package com.develophelper.android5.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.develophelper.android5.Constants;
import com.develophelper.android5.R;
import com.develophelper.android5.fragment.AnimationFragment;
import com.develophelper.android5.fragment.BaseFragment;
import com.develophelper.android5.fragment.DrawableFragment;
import com.develophelper.android5.fragment.DrawerFragment;
import com.develophelper.android5.fragment.ShadowFragment;
import com.develophelper.android5.fragment.StyleFragment;
import com.develophelper.android5.fragment.SupportFragment;
import com.develophelper.android5.fragment.WidgetFragment;


public class Android5MainActivity extends BaseActivity {

    DrawerLayout drawerLayout;//侧边栏

    private DrawerFragment        mDrawerFragment;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    //设置样式子类StyleFragment
    private BaseFragment          mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Constants.M_THEME != -1) {
            //点击修改主题后，会重新启动 Activity, 加载新的Material Design主题
            setTheme(Constants.M_THEME);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android5_main);
        setTitle("Android 5.0新特性");

        drawerLayout = findViewById(R.id.drawer_layout);


        //从左往右滑
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //初始化开关，并和drawer关联
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //该方法会自动和actionBar关联
        actionBarDrawerToggle.syncState();

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidateOptionsMenu();
            }
        });
//        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
//            /**
//             * Drawer的回调方法
//             * 打开drawer
//             * 需要在该方法中对Toggle做对应的操作
//             */
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                actionBarDrawerToggle.onDrawerOpened(drawerView);//需要把开关也变为打开
//                invalidateOptionsMenu();
//            }
//
//            /**
//             * 关闭drawer
//             */
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                actionBarDrawerToggle.onDrawerClosed(drawerView);//需要把开关也变为关闭
//                invalidateOptionsMenu();
//            }
//
//            /**
//             * drawer滑动的回调
//             */
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                actionBarDrawerToggle.onDrawerSlide(drawerView, slideOffset);
//            }
//
//            /**
//             * drawer状态改变的回调
//             */
//            @Override
//            public void onDrawerStateChanged(int newState) {
//                actionBarDrawerToggle.onDrawerStateChanged(newState);
//            }
//        });
        //获取fragment管理器
        final FragmentManager fragmentManager = getSupportFragmentManager();
        //侧边栏菜单
        mDrawerFragment = (DrawerFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);

        //设置点击菜单条目监听
        mDrawerFragment.setOnDrawerItemSelectedListener(new DrawerFragment.OnDrawerItemSelectedListener() {
            /**
             * 侧边栏 抽屉菜单 的回调方法
             * 需要在该方法中添加对应的Framgment
             */
            @Override
            public void onDrawerItemSelected(int position) {
                //点击条目后关闭抽屉菜单
                drawerLayout.closeDrawer(GravityCompat.START);


                //创建fragment
                BaseFragment fragment = createFragment(position);
                mCurrentFragment = fragment;

                FragmentUtils.replace(fragmentManager, fragment, R.id.container);
            }
        });
        //默认选中第一个
        mDrawerFragment.selectItem(0);



        //初始化toolbar
        ActionBar actionBar = getSupportActionBar();
        //设置显示左侧按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置左侧按钮可点
        actionBar.setHomeButtonEnabled(true);
        //设置显示标题
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        //设置标题
        actionBar.setTitle(getString(R.string.app_name));



//      Intent intent = new Intent();
//      intent.setAction("com.google.sample.fade");//Intent.ACTION_DIAL拨号
//		Intent intent = new Intent("com.google.sample.fade");
//      intent.setData(Uri.parse("callPhone://buzhidao"));
//      intent.setType("text/xxx");//和上面方法冲突
//      intent.setDataAndType();//data和type共存...

        //                          action, uri
//        Intent intent = new Intent("com.google.sample.fade", Uri.parse("callPhone://buzhidao"));
//        startActivity(intent);
    }

    /**
     * 设置菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            mCurrentFragment.onCreateOptionsMenu(menu, getMenuInflater());
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选中菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || mCurrentFragment.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //fragment不能装进List&Map中!
    public BaseFragment createFragment(int position) {
        switch (position) {
            case 0:  //样式主题
                return new StyleFragment();
            case 1:  //阴影
                return new ShadowFragment();
            case 2:
                return new DrawableFragment();
            case 3:
                return new AnimationFragment();
            case 4:
                return new WidgetFragment();
            case 5:
                return new SupportFragment();
            default:
                return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerLayout.removeDrawerListener(actionBarDrawerToggle);
    }
}
