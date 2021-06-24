package com.actor.androiddevelophelper.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * description: GithubIssues
 *
 * @author : ldf
 * date       : 2020/12/7 on 14
 * @version 1.0
 */
public class GithubIssuesInfo {

    /**
     * url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/23
     * repository_url : https://api.github.com/repos/ButterAndButterfly/GithubHost
     * labels_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/23/labels{/name}
     * comments_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/23/comments
     * events_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/23/events
     * html_url : https://github.com/ButterAndButterfly/GithubHost/issues/23
     * id : 753915043
     * node_id : MDU6SXNzdWU3NTM5MTUwNDM=
     * number : 23
     * title : 2020-12-1 github hosts
     * user : {"login":"github-actions[bot]","id":41898282,"node_id":"MDM6Qm90NDE4OTgyODI=","avatar_url":"https://avatars2.githubusercontent.com/in/15368?v=4","gravatar_id":"","url":"https://api.github.com/users/github-actions%5Bbot%5D","html_url":"https://github.com/apps/github-actions","followers_url":"https://api.github.com/users/github-actions%5Bbot%5D/followers","following_url":"https://api.github.com/users/github-actions%5Bbot%5D/following{/other_user}","gists_url":"https://api.github.com/users/github-actions%5Bbot%5D/gists{/gist_id}","starred_url":"https://api.github.com/users/github-actions%5Bbot%5D/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/github-actions%5Bbot%5D/subscriptions","organizations_url":"https://api.github.com/users/github-actions%5Bbot%5D/orgs","repos_url":"https://api.github.com/users/github-actions%5Bbot%5D/repos","events_url":"https://api.github.com/users/github-actions%5Bbot%5D/events{/privacy}","received_events_url":"https://api.github.com/users/github-actions%5Bbot%5D/received_events","type":"Bot","site_admin":false}
     * labels : [{"id":2101164352,"node_id":"MDU6TGFiZWwyMTAxMTY0MzUy","url":"https://api.github.com/repos/ButterAndButterfly/GithubHost/labels/host","name":"host","color":"ededed","default":false,"description":null}]
     * state : open
     * locked : false
     * assignee : null
     * assignees : []
     * milestone : null
     * comments : 0
     * created_at : 2020-12-01T00:56:44Z
     * updated_at : 2020-12-01T00:56:44Z
     * closed_at : null
     * author_association : NONE
     * active_lock_reason : null
     * body : ```
     # GitHub Start
     140.82.112.4 github.com
     140.82.114.3 gist.github.com
     185.199.111.153 assets-cdn.github.com
     151.101.208.133 raw.githubusercontent.com
     151.101.248.133 gist.githubusercontent.com
     151.101.248.133 cloud.githubusercontent.com
     151.101.208.133 camo.githubusercontent.com
     151.101.248.133 avatars0.githubusercontent.com
     151.101.208.133 avatars1.githubusercontent.com
     151.101.208.133 avatars2.githubusercontent.com
     151.101.208.133 avatars3.githubusercontent.com
     151.101.208.133 avatars4.githubusercontent.com
     151.101.208.133 avatars5.githubusercontent.com
     151.101.208.133 avatars6.githubusercontent.com
     151.101.248.133 avatars7.githubusercontent.com
     151.101.208.133 avatars8.githubusercontent.com
     185.199.108.154 github.githubassets.com
     140.82.121.5 api.github.com
     # GitHub End
     ```
     * performed_via_github_app : null
     */

    public String url;
    public String           repository_url;
    public String           labels_url;
    public String           comments_url;
    public String           events_url;
    public String           html_url;
    public int              id;
    public String           node_id;
    public int              number;
    public String           title;
    public UserBean         user;
    public String           state;
    public boolean          locked;
    public Object           assignee;
    public Object           milestone;
    public int              comments;
    public String           created_at;
    public String           updated_at;
    public Object           closed_at;
    public String           author_association;
    public Object           active_lock_reason;
    public String           body;
    public Object           performed_via_github_app;
    public List<LabelsBean> labels;
    public List<?>          assignees;

    public static class UserBean {
        /**
         * login : github-actions[bot]
         * id : 41898282
         * node_id : MDM6Qm90NDE4OTgyODI=
         * avatar_url : https://avatars2.githubusercontent.com/in/15368?v=4
         * gravatar_id :
         * url : https://api.github.com/users/github-actions%5Bbot%5D
         * html_url : https://github.com/apps/github-actions
         * followers_url : https://api.github.com/users/github-actions%5Bbot%5D/followers
         * following_url : https://api.github.com/users/github-actions%5Bbot%5D/following{/other_user}
         * gists_url : https://api.github.com/users/github-actions%5Bbot%5D/gists{/gist_id}
         * starred_url : https://api.github.com/users/github-actions%5Bbot%5D/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/github-actions%5Bbot%5D/subscriptions
         * organizations_url : https://api.github.com/users/github-actions%5Bbot%5D/orgs
         * repos_url : https://api.github.com/users/github-actions%5Bbot%5D/repos
         * events_url : https://api.github.com/users/github-actions%5Bbot%5D/events{/privacy}
         * received_events_url : https://api.github.com/users/github-actions%5Bbot%5D/received_events
         * type : Bot
         * site_admin : false
         */

        public String login;
        public int     id;
        public String  node_id;
        public String  avatar_url;
        public String  gravatar_id;
        public String  url;
        public String  html_url;
        public String  followers_url;
        public String  following_url;
        public String  gists_url;
        public String  starred_url;
        public String  subscriptions_url;
        public String  organizations_url;
        public String  repos_url;
        public String  events_url;
        public String  received_events_url;
        public String  type;
        public boolean site_admin;
    }

    public static class LabelsBean {
        /**
         * id : 2101164352
         * node_id : MDU6TGFiZWwyMTAxMTY0MzUy
         * url : https://api.github.com/repos/ButterAndButterfly/GithubHost/labels/host
         * name : host
         * color : ededed
         * default : false
         * description : null
         */

        public int id;
        public String  node_id;
        public String  url;
        public String  name;
        public String  color;
        @SerializedName("default")
        public boolean defaultX;
        public Object  description;
    }
}
