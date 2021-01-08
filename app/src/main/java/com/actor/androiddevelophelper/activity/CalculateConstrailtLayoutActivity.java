package com.actor.androiddevelophelper.activity;

import android.os.Bundle;
import android.text.TextUtils;
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
        etTitleBarHeight.setText(SPUtils.getString(Global.HEIGHT_TITLEBAR));
        etTotalHeight.setText(SPUtils.getString(Global.HEIGHT_TOTAL));
        etViewHeight.setText(SPUtils.getString(Global.HEIGHT_VIEW));
        etViewMarginTop.setText(SPUtils.getString(Global.MARGIN_VIEW_TOP));
        etViewMarginBottom.setText(SPUtils.getString(Global.MARGIN_VIEW_BOTTOM));
    }

    //开始计算
    private void calculate() {
        String marginTopS = getText(etViewMarginTop);//顶部边距
        String marginBottomS = getText(etViewMarginBottom);//底部边距

        //如果2个边距都不为空
        if (!TextUtils.isEmpty(marginTopS) && !TextUtils.isEmpty(marginBottomS)) {
            //保存
            SPUtils.putString(Global.MARGIN_VIEW_TOP, marginTopS);
            SPUtils.putString(Global.MARGIN_VIEW_BOTTOM, marginBottomS);
            //计算
            double marginTop = Double.parseDouble(marginTopS);
            double marginBottom = Double.parseDouble(marginBottomS);
            double bias = marginTop / (marginTop + marginBottom);//偏移量
            String clip = getStringFormat("第2种计算方式: %s/(%s+%s)=%f", marginTopS, marginTopS, marginBottomS, bias);
            tvResult.setText(clip);
            copy2Clipboard(clip);
            toast("第2种计算方式, 已复制到剪贴板");
        } else {
            String totalHeightS = getText(etTotalHeight);//总高度
            String viewHeightS = getText(etViewHeight);//控件高度
            if (!TextUtils.isEmpty(marginTopS) && !TextUtils.isEmpty(totalHeightS) && !TextUtils.isEmpty(viewHeightS)) {
                String titleBarHeightS = getText(etTitleBarHeight);//标题栏高度
                //保存
                SPUtils.putString(Global.HEIGHT_TITLEBAR, titleBarHeightS);
                SPUtils.putString(Global.HEIGHT_TOTAL, totalHeightS);
                SPUtils.putString(Global.HEIGHT_VIEW, viewHeightS);
                SPUtils.putString(Global.MARGIN_VIEW_TOP, marginTopS);
                if (isEmpty(titleBarHeightS)) titleBarHeightS = "0";
                double titleBarHeight = Double.parseDouble(titleBarHeightS);
                double totalHeight = Double.parseDouble(totalHeightS);
                double viewHeight = Double.parseDouble(viewHeightS);
                double viewMargin = Double.parseDouble(marginTopS);
                double leftHeight = totalHeight - titleBarHeight - viewHeight;//剩下宽/高度
                String result = "0";
                if (leftHeight == 0) {
                    tvResult.setText("0");
                } else {
                    result = String.valueOf(viewMargin / leftHeight);
                }
                String clip = getStringFormat("%s/(%s-%s-%s)=%s", marginTopS, totalHeightS, titleBarHeightS, viewHeightS, result);
                tvResult.setText(clip);
                copy2Clipboard(clip);
                toast("已复制到剪贴板");
            } else {
                toast("请输入对应高度, 推荐输入第2种计算方式的高度!");
            }
        }
    }

    @OnClick(R.id.btn_calculate)
    public void onViewClicked() {
        calculate();
    }
}
