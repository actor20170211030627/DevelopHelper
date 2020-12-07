package com.actor.androiddevelophelper.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.actor.androiddevelophelper.Global;
import com.actor.androiddevelophelper.R;
import com.actor.androiddevelophelper.activity.GithubIssueDetailActivity;
import com.actor.androiddevelophelper.info.GithubIssuesInfo;
import com.actor.myandroidframework.utils.ConfigUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;

/**
 * description: 描述
 *
 * @author : 李大发
 * date       : 2020/12/7 on 15
 * @version 1.0
 */
public class GithubIssuesAdapter extends BaseQuickAdapter<GithubIssuesInfo, BaseViewHolder> {

    private final int colorOpen, colorClosed;

    public GithubIssuesAdapter() {
        super(R.layout.item_github_issues);
        colorOpen = ConfigUtils.APPLICATION.getResources().getColor(R.color.green_2ea44f);
        colorClosed = ConfigUtils.APPLICATION.getResources().getColor(R.color.green_cb2431);

        setOnItemClickListener((adapter, view, position) -> {
            GithubIssuesInfo item = getItem(position);
            if (item == null) {
                return;
            }
            mContext.startActivity(new Intent(mContext, GithubIssueDetailActivity.class)
                    .putExtra(Global.NUMBER, item.number));
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GithubIssuesInfo item) {
        boolean isOpen = "open".equals(item.state);
        helper.setText(R.id.tv_is_open, isOpen ? "Open issue" : "Closed")
                .setTextColor(R.id.tv_is_open, isOpen ? colorOpen : colorClosed)
                .setText(R.id.tv_number, "Issue number: #" + item.number)
                .setText(R.id.tv_title, "Title: " + item.title)
                .setText(R.id.tv_time, ISO8601ToDate(item.created_at))
                .setText(R.id.tv_user, "User: " + item.user.login);
    }


    /**
     * ISO8601 转换成日期, 来自 api.github 的 created_at
     * @param iso8601 "2020-12-01T00:56:44Z"    //最后是Z...
     * @return 2020-12-01 08:56:44
     */
    private String ISO8601ToDate(String iso8601) {
        Date date = TimeUtils.string2Date(iso8601, "yyyy-MM-dd'T'HH:mm:ssX");
        return TimeUtils.date2String(date, "yyyy-MM-dd HH:mm:ss");
    }
}
