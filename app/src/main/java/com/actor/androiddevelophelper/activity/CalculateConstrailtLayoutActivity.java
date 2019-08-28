package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 计算约束布局
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-8-28 on 9:48
 */
public class CalculateConstrailtLayoutActivity extends BaseActivity {

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
        etTotalHeight.setText(SPUtils.getInstance().getString(Global.HEIGHT_TOTAL));
        etViewHeight.setText(SPUtils.getInstance().getString(Global.HEIGHT_VIEW));
        etViewMargin.setText(SPUtils.getInstance().getString(Global.MARGIN_VIEW));
    }

    private void calculate() {
        if (isNoEmpty(etTotalHeight, etViewHeight, etViewMargin)) {
            String text = getText(etTotalHeight);
            String text1 = getText(etViewHeight);
            String text2 = getText(etViewMargin);
            SPUtils.getInstance().put(Global.HEIGHT_TOTAL, text);
            SPUtils.getInstance().put(Global.HEIGHT_VIEW, text1);
            SPUtils.getInstance().put(Global.MARGIN_VIEW, text2);

            double totalHeight = Double.parseDouble(text);
            double viewHeight = Double.parseDouble(text1);
            double viewMargin = Double.parseDouble(text2);
            double leftHeight = totalHeight - viewHeight;//剩下宽/高度
            String result = "0";
            if (leftHeight <= 0) {
                tvResult.setText("0");
            } else result = String.valueOf(viewMargin / leftHeight);
            String clip = String.format(Locale.getDefault(), "%s/(%s-%s)=%s", text2, text, text1,
                    result);
            tvResult.setText(clip);
            copy2Clipboard(clip);
            ToastUtils.showShort("已复制到剪贴板");
        }
    }

    @OnClick(R.id.btn_calculate)
    public void onViewClicked() {
        calculate();
    }
}
