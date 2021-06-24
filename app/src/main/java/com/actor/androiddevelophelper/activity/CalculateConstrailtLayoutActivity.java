package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 计算约束布局
 * Author     : ldf
 * Date       : 2019-8-28 on 9:48
 */
public class CalculateConstrailtLayoutActivity extends BaseActivity {

    @BindView(R.id.et_view_margin_top)
    EditText etViewMarginTop;
    @BindView(R.id.et_view_margin_bottom)
    EditText etViewMarginBottom;
    @BindView(R.id.tv_result)
    TextView tvResult;//计算结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_constrailt_layout);
        ButterKnife.bind(this);

        setTitle("计算约束布局偏移量");
        etViewMarginTop.setText(SPUtils.getString(Global.MARGIN_VIEW_TOP));
        etViewMarginBottom.setText(SPUtils.getString(Global.MARGIN_VIEW_BOTTOM));
    }

    //开始计算
    @OnClick(R.id.btn_calculate)
    public void onViewClicked() {
        calculate();
    }

    private void calculate() {
        if (isNoEmpty(etViewMarginTop, etViewMarginBottom)) {
            String marginTopS = getText(etViewMarginTop);//顶部边距
            String marginBottomS = getText(etViewMarginBottom);//底部边距
            //保存
            SPUtils.putString(Global.MARGIN_VIEW_TOP, marginTopS);
            SPUtils.putString(Global.MARGIN_VIEW_BOTTOM, marginBottomS);
            //计算
            double marginTop = Double.parseDouble(marginTopS);
            double marginBottom = Double.parseDouble(marginBottomS);
            double bias = marginTop / (marginTop + marginBottom);//偏移量
            String clip = getStringFormat("%s/(%s+%s)=%f", marginTopS, marginTopS, marginBottomS, bias);
            tvResult.setText("计算结果: ".concat(clip));
            ClipboardUtils.copy2Clipboard(clip);
            toast("已复制到剪贴板");
        }
    }
}
