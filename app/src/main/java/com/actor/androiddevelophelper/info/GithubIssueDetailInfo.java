package com.actor.androiddevelophelper.info;

import java.util.List;

/**
 * description: 描述
 *
 * @author : 李大发
 * date       : 2020/12/7 on 16
 * @version 1.0
 */
public class GithubIssueDetailInfo {

    /**
     * url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/21
     * repository_url : https://api.github.com/repos/ButterAndButterfly/GithubHost
     * labels_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/21/labels{/name}
     * comments_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/21/comments
     * events_url : https://api.github.com/repos/ButterAndButterfly/GithubHost/issues/21/events
     * html_url : https://github.com/ButterAndButterfly/GithubHost/issues/21
     * id : 753325802
     * node_id : MDU6SXNzdWU3NTMzMjU4MDI=
     * number : 21
     * title : question为啥要海外机器查询dns呀
     * user : {"login":"mafulong","id":24795000,"node_id":"MDQ6VXNlcjI0Nzk1MDAw","avatar_url":"https://avatars3.githubusercontent.com/u/24795000?v=4","gravatar_id":"","url":"https://api.github.com/users/mafulong","html_url":"https://github.com/mafulong","followers_url":"https://api.github.com/users/mafulong/followers","following_url":"https://api.github.com/users/mafulong/following{/other_user}","gists_url":"https://api.github.com/users/mafulong/gists{/gist_id}","starred_url":"https://api.github.com/users/mafulong/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/mafulong/subscriptions","organizations_url":"https://api.github.com/users/mafulong/orgs","repos_url":"https://api.github.com/users/mafulong/repos","events_url":"https://api.github.com/users/mafulong/events{/privacy}","received_events_url":"https://api.github.com/users/mafulong/received_events","type":"User","site_admin":false}
     * labels : []
     * state : open
     * locked : false
     * assignee : null
     * assignees : []
     * milestone : null
     * comments : 1
     * created_at : 2020-11-30T09:50:13Z
     * updated_at : 2020-11-30T09:50:47Z
     * closed_at : null
     * author_association : NONE
     * active_lock_reason : null
     * body : 为啥要海外机器查询dns呀
     * closed_by : null
     * performed_via_github_app : null
     */

    public String url;
    public String   repository_url;
    public String   labels_url;
    public String   comments_url;
    public String   events_url;
    public String   html_url;
    public int      id;
    public String   node_id;
    public int      number;
    public String   title;
    public UserBean user;
    public String   state;
    public boolean  locked;
    public Object   assignee;
    public Object   milestone;
    public int      comments;
    public String   created_at;
    public String   updated_at;
    public Object   closed_at;
    public String   author_association;
    public Object   active_lock_reason;
    public String   body;
    public Object   closed_by;
    public Object   performed_via_github_app;
    public List<?>  labels;
    public List<?>  assignees;

    public static class UserBean {
        /**
         * login : mafulong
         * id : 24795000
         * node_id : MDQ6VXNlcjI0Nzk1MDAw
         * avatar_url : https://avatars3.githubusercontent.com/u/24795000?v=4
         * gravatar_id :
         * url : https://api.github.com/users/mafulong
         * html_url : https://github.com/mafulong
         * followers_url : https://api.github.com/users/mafulong/followers
         * following_url : https://api.github.com/users/mafulong/following{/other_user}
         * gists_url : https://api.github.com/users/mafulong/gists{/gist_id}
         * starred_url : https://api.github.com/users/mafulong/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/mafulong/subscriptions
         * organizations_url : https://api.github.com/users/mafulong/orgs
         * repos_url : https://api.github.com/users/mafulong/repos
         * events_url : https://api.github.com/users/mafulong/events{/privacy}
         * received_events_url : https://api.github.com/users/mafulong/received_events
         * type : User
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
}
