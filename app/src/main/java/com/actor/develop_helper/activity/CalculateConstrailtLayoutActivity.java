package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.databinding.ActivityCalculateConstrailtLayoutBinding;
import com.actor.develop_helper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.utils.toaster.ToasterUtils;

/**
 * Description: 计算约束布局偏移率bias
 * Author     : ldf
 * Date       : 2019-8-28 on 9:48
 */
public class CalculateConstrailtLayoutActivity extends BaseActivity<ActivityCalculateConstrailtLayoutBinding> {

    private EditText etViewMarginTop;
    private EditText etViewMarginBottom;
    private TextView tvResult;//计算结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle("计算约束布局偏移率bias");

        etViewMarginTop = viewBinding.etViewMarginTop;
        etViewMarginBottom = viewBinding.etViewMarginBottom;
        tvResult = viewBinding.tvResult;
        etViewMarginTop.setText(SPUtils.getString(Global.MARGIN_VIEW_TOP));
        etViewMarginBottom.setText(SPUtils.getString(Global.MARGIN_VIEW_BOTTOM));
    }

    //开始计算
    public void onViewClicked(View view) {
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
            double bias = marginTop / (marginTop + marginBottom);//偏移率
            String clip = getStringFormat("%s/(%s+%s)=%f", marginTopS, marginTopS, marginBottomS, bias);
            tvResult.setText("计算结果: ".concat(clip));
            ClipboardUtils.copy2Clipboard(clip);
            ToasterUtils.success("已复制到剪贴板");
        }
    }
}
