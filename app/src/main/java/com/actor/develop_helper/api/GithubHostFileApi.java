package com.actor.develop_helper.api;

import com.actor.develop_helper.Global;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestHost;

/**
 * description: 描述
 * company    :
 *
 * @author : ldf
 * date       : 2024/4/28 on 18
 * @version 1.0
 */
public class GithubHostFileApi implements IRequestApi, IRequestHost {
    @Override
    public String getApi() {
        return Global.HOSTS_FILE;
    }

    @Override
    public String getHost() {
        return "";
    }
}
