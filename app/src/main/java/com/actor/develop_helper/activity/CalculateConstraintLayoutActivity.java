package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.actor.develop_helper.Global;
import com.actor.develop_helper.databinding.ActivityCalculateConstrailtLayoutBinding;
import com.actor.develop_helper.utils.ClipboardUtils;
import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.utils.toaster.ToasterUtils;
import com.blankj.utilcode.util.KeyboardUtils;

/**
 * Description: 计算约束布局偏移率bias
 * Author     : ldf
 * Date       : 2019-8-28 on 9:48
 */
public class CalculateConstraintLayoutActivity extends BaseActivity<ActivityCalculateConstrailtLayoutBinding> {

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
    public void onViewClicked(@NonNull View view) {
        calculate(view);
    }

    private void calculate(@NonNull View view) {
        if (isNoEmpty(etViewMarginTop, etViewMarginBottom)) {
            KeyboardUtils.hideSoftInput(view);
            String marginTopS = getText(etViewMarginTop);//顶部边距
            String marginBottomS = getText(etViewMarginBottom);//底部边距
            //保存
            SPUtils.putString(Global.MARGIN_VIEW_TOP, marginTopS);
            SPUtils.putString(Global.MARGIN_VIEW_BOTTOM, marginBottomS);
            //计算
            float marginTop = Float.parseFloat(marginTopS);
            float marginBottom = Float.parseFloat(marginBottomS);
            float bias = marginTop / (marginTop + marginBottom);//偏移率
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) viewBinding.ivView.getLayoutParams();
            lp.verticalBias = bias;
            viewBinding.ivView.setLayoutParams(lp);

            String clip = getStringFormat("%s/(%s+%s)=%f", marginTopS, marginTopS, marginBottomS, bias);
            tvResult.setText("计算结果: ".concat(clip));
            ClipboardUtils.copy2Clipboard(clip);
            ToasterUtils.success("已复制到剪贴板");
        }
    }
}
