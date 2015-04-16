/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.egit.github.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salim this should implement the search user model
 * https://api.github.com/search/users?q=repos:%3E2+followers:%3E20
 *
 */
public class SearchUser implements Serializable {

    private int totalCount;
    private boolean incompleteResults;
    private static final long serialVersionUID = -661552596263255389L;
    //the user array should be build from JSON
    public List<ItemUser> items = new ArrayList<ItemUser>();

    public int getTotalCount() {
        return totalCount;
    }

    public SearchUser setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public List<ItemUser> getItems() {
        return items;
    }

    public void setItems(List<ItemUser> items) {
        this.items = items;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public SearchUser setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
        return this;
    }

}
/* 
 * {
 "total_count": 43178,
 "incomplete_results": false,
 "items": [
 {
 "login": "mojombo",
 "id": 1,
 "avatar_url": "https://avatars.githubusercontent.com/u/1?v=3",
 "gravatar_id": "",
 "url": "https://api.github.com/users/mojombo",
 "html_url": "https://github.com/mojombo",
 "followers_url": "https://api.github.com/users/mojombo/followers",
 "following_url": "https://api.github.com/users/mojombo/following{/other_user}",
 "gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
 "starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
 "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
 "organizations_url": "https://api.github.com/users/mojombo/orgs",
 "repos_url": "https://api.github.com/users/mojombo/repos",
 "events_url": "https://api.github.com/users/mojombo/events{/privacy}",
 "received_events_url": "https://api.github.com/users/mojombo/received_events",
 "type": "User",
 "site_admin": false,
 "score": 1.0,
 "text_matches": [

 ]
 },
 *  {
 "login": "paulirish",
 "id": 39191,
 "avatar_url": "https://avatars.githubusercontent.com/u/39191?v=3",
 "gravatar_id": "",
 "url": "https://api.github.com/users/paulirish",
 "html_url": "https://github.com/paulirish",
 "followers_url": "https://api.github.com/users/paulirish/followers",
 "following_url": "https://api.github.com/users/paulirish/following{/other_user}",
 "gists_url": "https://api.github.com/users/paulirish/gists{/gist_id}",
 "starred_url": "https://api.github.com/users/paulirish/starred{/owner}{/repo}",
 "subscriptions_url": "https://api.github.com/users/paulirish/subscriptions",
 "organizations_url": "https://api.github.com/users/paulirish/orgs",
 "repos_url": "https://api.github.com/users/paulirish/repos",
 "events_url": "https://api.github.com/users/paulirish/events{/privacy}",
 "received_events_url": "https://api.github.com/users/paulirish/received_events",
 "type": "User",
 "site_admin": false,
 "score": 1.0,
 "text_matches": [

 ]
 },
 */
