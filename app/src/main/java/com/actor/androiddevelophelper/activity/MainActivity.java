package com.actor.androiddevelophelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actor.androiddevelophelper.R;
import com.blankj.utilcode.util.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_ip_v4)
    TextView tvIpV4;
    @BindView(R.id.tv_ip_v6)
    TextView tvIpV6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvIpV4.setText("IP v4: " + NetworkUtils.getIPAddress(true));
        tvIpV6.setText("IP v6: " + NetworkUtils.getIPAddress(false));
    }

    @OnClick({R.id.btn_calculate_constraintlayout, R.id.btn_view_system_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate_constraintlayout://计算约束布局偏移量
                startActivity(new Intent(this, CalculateConstrailtLayoutActivity.class));
                break;
            case R.id.btn_view_system_icon://查看系统资源图标
                startActivity(new Intent(this, ViewSystemIconActivity.class));
                break;
            case R.id.btn_app_info://查看App信息(包括签名)
                break;
        }
    }
}
