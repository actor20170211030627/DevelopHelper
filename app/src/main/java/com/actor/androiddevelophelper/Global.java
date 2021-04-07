package com.actor.androiddevelophelper;

/**
 * Description: 全局常量
 * Author     : 李大发
 * Date       : 2019-8-5 on 10:49
 *
 * @version 1.0
 */
public class Global {
    public static final String MARGIN_VIEW_TOP    = "MARGIN_VIEW_TOP";
    public static final String MARGIN_VIEW_BOTTOM = "MARGIN_VIEW_BOTTOM";

    public static final String CHECK_UPDATE = "https://gitee.com/actor20170211030627/" +
            "DevelopHelper" +
            "/raw/master/" +
            "app" +
            "/build/outputs/apk/debug/output.json";

    public static final String DOWNLOAD_URL = "https://github.com/actor20170211030627/" +
            "DevelopHelper" +
            "/raw/master/" +
            "app" +
            "/build/outputs/apk/debug/" +
            "app-debug" +
            ".apk";


    //下方是github的api
    public static final String GITHUB_API = "https://api.github.com";

    public static final String BAIDU_LOGO = "https://www.baidu.com/img/baidu_jgylogo3.gif";

    //issues列表, 填入user & 仓库名
    public static final String GITHUB_ISSUES = GITHUB_API + "/repos/%s/%s/issues";
    public static final String GITHUB_ISSUE_DETAIL = GITHUB_ISSUES + "/%d";


    ////////////////////////
    // 下方是常量
    ////////////////////////
    public static final String NUMBER = "NUMBER";
    public static final String HOST_ADDRESS = "C:\\Windows\\System32\\drivers\\etc\\hosts";
}
