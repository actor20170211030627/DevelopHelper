package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.myandroidframework.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 计算约束布局
 * Author     : 李大发
 * Date       : 2019-8-28 on 9:48
 */
public class CalculateConstrailtLayoutActivity extends BaseActivity {

    @BindView(R.id.et_title_bar_height)
    EditText etTitleBarHeight;
    @BindView(R.id.et_total_height)
    EditText etTotalHeight;
    @BindView(R.id.et_view_height)
    EditText etViewHeight;
    @BindView(R.id.et_view_margin)
    EditText etViewMargin;
    @BindView(R.id.tv_result)
    TextView tvResult;//计算结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_constrailt_layout);
        ButterKnife.bind(this);

        setTitle("计算约束布局偏移量");
        etTitleBarHeight.setText(SPUtils.getString(Global.HEIGHT_TITLEBAR));
        etTotalHeight.setText(SPUtils.getString(Global.HEIGHT_TOTAL));
        etViewHeight.setText(SPUtils.getString(Global.HEIGHT_VIEW));
        etViewMargin.setText(SPUtils.getString(Global.MARGIN_VIEW));
    }

    private void calculate() {
        if (isNoEmpty(etTotalHeight, etViewHeight, etViewMargin)) {
            String text1 = getText(etTitleBarHeight);
            String text2 = getText(etTotalHeight);
            String text3 = getText(etViewHeight);
            String text4 = getText(etViewMargin);
            SPUtils.putString(Global.HEIGHT_TITLEBAR, text1);
            SPUtils.putString(Global.HEIGHT_TOTAL, text2);
            SPUtils.putString(Global.HEIGHT_VIEW, text3);
            SPUtils.putString(Global.MARGIN_VIEW, text4);

            if (isEmpty(text1)) text1 = "0";
            double titleBarHeight = Double.parseDouble(text1);
            double totalHeight = Double.parseDouble(text2);
            double viewHeight = Double.parseDouble(text3);
            double viewMargin = Double.parseDouble(text4);
            double leftHeight = totalHeight - titleBarHeight - viewHeight;//剩下宽/高度
            String result = "0";
            if (leftHeight <= 0) {
                tvResult.setText("0");
            } else result = String.valueOf(viewMargin / leftHeight);
            String clip = getStringFormat("%s/(%s-%s-%s)=%s", text4, text2, text1, text3, result);
            tvResult.setText(clip);
            copy2Clipboard(clip);
            toast("已复制到剪贴板");
        }
    }

    @OnClick(R.id.btn_calculate)
    public void onViewClicked() {
        calculate();
    }
}
