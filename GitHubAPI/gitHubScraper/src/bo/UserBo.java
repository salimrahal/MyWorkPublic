/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo;

import java.io.IOException;
import java.util.List;
import org.eclipse.egit.github.core.ItemUser;
import org.eclipse.egit.github.core.SearchUser;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

/**
 *
 * @author salim
 */
public class UserBo {

    /*
     it returns a dump of the user: username, id , without email and other details
     */
    public static void getAllUsers(GitHubClient client, int pageLimit) throws IOException {
        UserService serviceUser = new UserService(client);
        List<User> allusers = serviceUser.getAllUsers(pageLimit);
        System.out.println("getUsefulUsers= size:" + allusers.size());
        for (User user : allusers) {
            System.out.println("login:" + user.getLogin() + "/email:" + user.getEmail() + "/url:" + user.getUrl());
        }
    }

    /*
     not working need to implement the search api
     */
    public static void getUsefulUsers(GitHubClient client, int minRepos, int minFollowers) throws IOException {
        UserService serviceUser = new UserService(client);
        List<SearchUser> usefulusers = serviceUser.getUsefulUsers(minRepos, minFollowers);
        for (SearchUser searchUser : usefulusers) {
            List<ItemUser> iuL = searchUser.getItems();
            System.out.println("total count:" + searchUser.getTotalCount() + "/ ItemUser:total item user=" + iuL.size());
            for (ItemUser iu : iuL) {
                System.out.println(iu.toString());
            }
        }

    }

    /*
     not working need to implement the search api
     */
    public static void getSearchUser(GitHubClient client) throws IOException {
        UserService serviceUser = new UserService(client);
        SearchUser searchUser = serviceUser.getSearchUser();
        System.out.println("getSearchUser=total count:" + searchUser.getTotalCount());//getUsefulUsers=total count: 7 for 10000 follower it works!
    }
    /* Json -> Java
     it returns the full user object as java by calling https://api.github.com/users/$user
     */

    public static void getUserbyUserName(GitHubClient client, String username) throws IOException {
        UserService serviceUser = new UserService(client);
        User user = serviceUser.getUser(username);
        System.out.println("getUserObjectbyUserName: user =" + user.toString());
    }

    /*
     it extract the total number of followers, this function loop thru all the pages:
     for example defunkt username has about 12k followers, 141 page each page has 100 followers
     Link=[<https://api.github.com/user/2/followers?per_page=100&page=121>; rel="next", 
     <https://api.github.com/user/2/followers?per_page=100&page=141>; rel="last",
     <https://api.github.com/user/2/followers?per_page=100&page=1>; rel="first", 
     <https://api.github.com/user/2/followers?per_page=100&page=119>; rel="prev"]
    
     */
    public static void getFollowersbyUserName(GitHubClient client, String username) throws IOException {
        UserService serviceUser = new UserService(client);
        List<User> followers = serviceUser.getFollowers(username);
        System.out.println("getFollowersOfUser " + username + " = size:" + followers.size());
    }
}
