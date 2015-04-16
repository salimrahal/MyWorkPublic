/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eclipse.egit.github.core;

import java.io.Serializable;

/**
 *
 * @author salim this is the model of the Item element i.e. the user element
 * returned by search Json example: { "total_count": 43178,
 * "incomplete_results": false, "items": [ { "login": "mojombo", "id": 1,
 * "avatar_url": "https://avatars.githubusercontent.com/u/1?v=3", ....
 *
 * ]
 * }, { "login": "paulirish", "id": 39191, .... },
 *
 */
public class ItemUser implements Serializable {

    private static final long serialVersionUID = 3855536833302129207L;
    private int id;
    private String login;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ItemUser{" + "id=" + id + ", login=" + login + ", url=" + url + '}';
    }

}
